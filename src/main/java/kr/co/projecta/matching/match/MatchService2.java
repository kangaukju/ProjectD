package kr.co.projecta.matching.match;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;

import kr.co.projecta.matching.dao.AssignmentDAO;
import kr.co.projecta.matching.dao.RequirementDAO;
import kr.co.projecta.matching.dao.SeekerDAO;
import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.match.MatchResult.MatchResultComparable;
import kr.co.projecta.matching.match.MatchResult.RankResultComparable;
import kr.co.projecta.matching.user.Matcher;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;
import kr.co.projecta.matching.user.types.MatchStatus;
import kr.co.projecta.matching.util.Times;

public class MatchService2 {
	transient Plogger log = Plogger.getLogger(this.getClass());
	
	SeekerDAO seekerDAO;
	RequirementDAO requirementDAO;
	AssignmentDAO assignmentCandidateDAO;
	int topLimit = 5;
	
	// 양방향 매칭 시 가중치를 부여할 매칭타켓
	static enum MATCHER_PRIORITY { 
		NONE, 
		SEEKER, 
		REQUIREMENT
	};
	MATCHER_PRIORITY matcherPriority = MATCHER_PRIORITY.NONE;
	
	String resultExportPath;
	
	// 최종 매칭된 배정요청 수
	long matchedRequirementCount;
	// 최종 매칭된 구직자 수
	long matchedSeekerCount;
	
	public static final String assignResultListPath = "assign";
	public static final String assignResultOffererPath = "offerer";
	List<AssignResult> assignResultList = new ArrayList<>();

	public MatchService2(
			SeekerDAO seekerDAO, 
			RequirementDAO requirementDAO,
			AssignmentDAO assignmentCandidateDAO,
			int topLimit) {
		this(seekerDAO, requirementDAO, assignmentCandidateDAO, topLimit, MATCHER_PRIORITY.NONE);
	}
	
	public MatchService2(
			SeekerDAO seekerDAO, 
			RequirementDAO requirementDAO,
			AssignmentDAO assignmentCandidateDAO,
			int topLimit,
			MATCHER_PRIORITY matcherPriority) 
	{
		this.seekerDAO = seekerDAO;
		this.requirementDAO = requirementDAO;
		this.assignmentCandidateDAO = assignmentCandidateDAO;
		this.topLimit = topLimit;
		this.matcherPriority = matcherPriority;
	}	
	public long getMatchedRequirementCount() {
		return matchedRequirementCount;
	}
	public long getMatchedSeekerCount() {
		return matchedSeekerCount;
	}	
	public String getResultExportPath() {
		return resultExportPath;
	}
	public void setResultExportPath(String resultExportPath) {
		this.resultExportPath = resultExportPath;
	}
	public int getTopLimit() {
		return topLimit;
	}
	public void setTopLimit(int topLimit) {
		this.topLimit = topLimit;
	}
	public void setSeekerPriority() {
		matcherPriority = MATCHER_PRIORITY.SEEKER;
	}
	public void setRequirementPriority() {
		matcherPriority = MATCHER_PRIORITY.REQUIREMENT;
	}
	public void setSeekerDAO(SeekerDAO seekerDAO) {
		this.seekerDAO = seekerDAO;
	}
	public void setRequirementDAO(RequirementDAO requirementDAO) {
		this.requirementDAO = requirementDAO;
	}
	public List<AssignResult> getAssignResultList() {
		return this.assignResultList;
	}
	
	// 자동 매칭 서비스
	synchronized public void autoMatchService() {
		log.i("Start autoMatchService");
		// TODO: DAO의 모든 데이터를 불러온다. 많은 리소스가 요구됨.
		List<? extends Matcher> seekerAll = seekerDAO.selectNotAssignSeeker();
		List<? extends Matcher> requirementAll = requirementDAO.selectNotAssignComplete();
		
		// 양방향 매칭
		twoWayMatching(seekerAll, requirementAll);
	}
	
	/**
	 * twoWayMatching: 양방향 매칭
	 * @param seekerAll
	 * @param requirementAll
	 */
	private void twoWayMatching(
			List<? extends Matcher> seekerAll, 
			List<? extends Matcher> requirementAll)
	{
		long requirementRank;
		List<MatchResultComparable<Matcher>> recommandSeekers;
		
		// 모든 채용정보 검색
		for (Matcher requirement : requirementAll) {
			// 채용정보와 매칭률이 높은 구직자 N명 선출
			// 1. topN
			recommandSeekers = topMatch(requirement, seekerAll, topLimit);
			
			PriorityQueue<RankResultComparable<Long, MatchResultComparable<Matcher>>> rank
				= new PriorityQueue<>(requirementAll.size());
			
			long seekerRank = 0;
			long matcherScore;
			
			// 선출된 구직자 입장에서
			for (MatchResultComparable<Matcher> matchResult : recommandSeekers) {
				// 구직자 입장에서 해당 채용정보의 매칭순위
				// 2. rank
				requirementRank = rankMatch(matchResult.getData(), requirementAll, requirement);
				
				switch (matcherPriority) {
				case SEEKER:
					matcherScore = seekerRank*2 + requirementRank;
					break;
				case REQUIREMENT:
					matcherScore = requirementRank*2 + seekerRank;
					break;
				default:
					matcherScore = requirementRank + seekerRank;
				}
				
				rank.add(new RankResultComparable<Long, MatchResultComparable<Matcher>>(matcherScore, matchResult));
				
				seekerRank++;
			}
			
			// 배정요청에 현재 배정된 구직자 수
			int assignedSeekerCount = (int)assignmentCandidateDAO.selectCount(requirement.getId());
			
			// 해당 채용정보에 가장 (양방향)매칭률이 좋은 구직자 N명 선출
			int person = ((Requirement)requirement).getPerson();
			
			// 배정요청에 부족한 인원 만큼 선출한다.
			int needN = person - assignedSeekerCount;
			
			List<String> seekers = new ArrayList<>();
			
			List<RankResultComparable<Long, MatchResultComparable<Matcher>>> bestRank = 
					new ArrayList<>();
			while (!rank.isEmpty() && (needN-- > 0)) {
				
				RankResultComparable<Long, MatchResultComparable<Matcher>> r = rank.poll();
				
				seekerAll.remove(r.getData().getData());
				bestRank.add(r);
				// 구직자 ID 리스트에 추가
				seekers.add(r.getData().getData().getId());
			}
 
			if (bestRank.size() > 0) {
				
				recommandAction(requirement, bestRank);
				
				// XXX: 배정이력 저장 방식-2
				// 배정결과를 JSON 파일로 기록한다.
				/*
				if (resultExportPath != null) {
					Requirement require = (Requirement) requirement;
					Map<String, Object> resultMap = recommandResultMap(require, bestRank);
					exportJSON(resultMap, require.getOffererId(), resultExportPath);
				}
				*/
				
				// XXX: 배정이력 저장 방식-1
				// 배정된 결과 저장
				String requirementId = ((Requirement)requirement).getId();
				AssignResult assignResult = newAssignResult(requirementId, seekers);
				this.assignResultList.add(assignResult);
				

				// XXX: 배정이력 저장 방식-3
				
			}
		}
		// XXX: 배정이력 저장 방식-1
		// 배정결과가 존재하는 경우에만 파일로 직렬화하여 저장한다.
		if (this.resultExportPath != null) {
			if (this.assignResultList.size() > 0) {
				String saveAssignResultFilename = saveAssignResult(
						this.resultExportPath, this.assignResultList);
//				System.out.println("saveAssignResultFilename: "+saveAssignResultFilename);
//				debugAssignResult(this.assignResultList);
			}
		}
	}
	
	private String exportJSON(Map<String, Object> resultMap, String offererId, String path) {
		String directoryPath = path+"/"+offererId;
		File dir = new File(directoryPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String dateFilename = format.format(Times.now())+".json";
		BufferedWriter output = null;
		try {
			output 
				= new BufferedWriter(
					new OutputStreamWriter(
						new FileOutputStream(
							directoryPath+"/"+dateFilename), "UTF8"));
			ObjectMapper mapper = new ObjectMapper();
			output.write(mapper.writeValueAsString(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
			dateFilename = null;
		} finally {
			if (output != null) try { output.close(); } catch (IOException e) { }
		}
		return dateFilename;
	}
	
	/**
	 * 매칭률이 높은 구직자 선출 이후에 해야할 비즈니스 로직 구현
	 * @param requirement: 채용정보
	 * @param recommandSeeker: 추천된 구직자
	 */
	private void recommandAction(
			Matcher requirement,
			List<RankResultComparable<Long, MatchResultComparable<Matcher>>> recommandSeeker) 
	{
		this.matchedRequirementCount++;
		this.matchedSeekerCount += recommandSeeker.size();
		
		String requirementId = requirement.getId();
		
		log.i("<<< ["+requirement+"] recommand seekers >>>");
		for (RankResultComparable<Long, MatchResultComparable<Matcher>> rank : recommandSeeker) {
			MatchResultComparable<Matcher> match = rank.getData();
			log.i("\t rank: "+rank.getRank());
			MatchResult matchResult = match.getMatchResult();
			Matcher matcher = match.getData();
			Map<String, Double> matchScore = matchResult.getMatchScore();
			log.i("\t\t ["+matcher+"]");
			for (String key : matchScore.keySet()) {
				log.i("\t\t "+key+": "+matchScore.get(key));
			}
		}
		
		// 현재 배정 현황(assignment) 기록
		for (RankResultComparable<Long, MatchResultComparable<Matcher>> rank : recommandSeeker) {
			MatchResultComparable<Matcher> match = rank.getData();
			Seeker seeker = (Seeker) match.getData();
			String seekerId = seeker.getId();
			assignmentCandidateDAO.insert(requirementId, seekerId);
		}
		
		// 매칭 상태를 업데이트한다
		Requirement r = (Requirement)requirement;
		long person = r.getPerson();
		long assignedCount = assignmentCandidateDAO.selectCount(requirementId);
		System.out.println("person:"+person+", assignedCount:"+assignedCount);
		if (person == assignedCount) {
			if (r.getMatchStatus().getMatchStatus() != MatchStatus.COMPLETION) {
				requirementDAO.updateMatchStatus(requirementId, MatchStatus.COMPLETION);
			}
		} else {
			if (r.getMatchStatus().getMatchStatus() != MatchStatus.INCOMPLETION) {
				requirementDAO.updateMatchStatus(requirementId, MatchStatus.INCOMPLETION);
			}
		}
	}
	
	/**
	 * 배정정보와 배정의 구직자정보를 리스트에 저장한다. 
	 * @param requirement
	 * @param seekers
	 */
	private AssignResult newAssignResult(String requirementId, List<String> seekers) {
		if (seekers.size() == 0) {
			return null;
		}		
		// 배정받은 구직자 정보
		List<Seeker> seekerList = new ArrayList<>();
		for (String seeker : seekers) {
			seekerList.add(seekerDAO.selectOne(seeker));
		}
		Requirement requirement = requirementDAO.selectOne(requirementId);
		
		return new AssignResult(requirement, seekerList);
	}
	
	/**
	 * 파일 중복을 검사하고 중복된 파일이 존재하면 파일명 뒤에 ".n" 형식으로 증가하는 파일명을 반환한다.
	 * @param filename
	 * @return
	 */
	private static String checkDuplicateFilename(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			return filename;
		}
		String pattern = String.format("%s.*", filename);
		File [] dir = file.getParentFile().listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return Pattern.matches(pattern, dir.getAbsolutePath()+"/"+name);
			}
		});
		return String.format("%s.%d", filename, dir.length);
	}
	
	/**
	 * 배정결과를 파일로 직렬화하여 배정이력으로 관리한다.
	 * @return
	 */
	public static String saveAssignResult(
			String resource,
			List<AssignResult> list)
	{
		// 배정내역 리스트 디렉토리 존재여부 검사
		File file = new File(String.format(
				"%s/%s",
				resource,
				MatchService2.assignResultListPath));
		if (!file.exists()) {
			file.mkdirs();
		}
		
		String filename = String.format(
				"%s/%s/%s-%d.bin",
				resource,
				MatchService2.assignResultListPath,
				Times.formatYYYYMMDD(Times.now()),
				Times.getHour(Times.now()));
		// 중복파일 검사
		filename = checkDuplicateFilename(filename);
		
		// 모든 배정내역을 기록한다.
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(
					new FileOutputStream(filename));
			out.writeObject(list);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) try {out.close();} catch (IOException e) { }
		}
		
		// 고용주의 배정내역 디렉토리 존재여부 검사
		file = new File(String.format(
				"%s/%s",
				resource,
				MatchService2.assignResultOffererPath));
		if (!file.exists()) {
			file.mkdirs();
		}
		// 고용주 개별적으로 배정내역을 기록한다.
		out = null;
		for (AssignResult as : list) {
//			System.out.println("save result: "+as.getRequirement().getOffererName()+", seekers:"+as.getSeekers());
			File offererFile = new File(String.format(
					"%s/%s/%s",
					resource,
					MatchService2.assignResultOffererPath,
					as.getRequirement().getOffererId()));
			if (!offererFile.exists()) {
				offererFile.mkdirs();
			}
			
			filename = String.format(
					"%s/%s/%s/%s-%d.bin",
					resource,
					MatchService2.assignResultOffererPath,
					as.getRequirement().getOffererId(),
					Times.formatYYYYMMDD(Times.now()),
					Times.getHour(Times.now()));
			// 중복파일 검사
			filename = checkDuplicateFilename(filename);
			try {
				out = new ObjectOutputStream(
						new FileOutputStream(filename));
				out.writeObject(list);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) try {out.close();} catch (IOException e) { }
			}
		}
		return filename;
	}
	
	/**
	 * 파일로 저장된 배정이력을 역직렬화하여 오브젝트로 생성한다.
	 * @param filename
	 * @return
	 */
	public static List<AssignResult> loadAssignResult(String filename) {
		ObjectInputStream in = null;
		List<AssignResult> list = null;
		try {
			in = new ObjectInputStream(
					new FileInputStream(filename));
			list = (List<AssignResult>) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) try {in.close();} catch (IOException e) { }
		}
		return list;
	}
	
	private void debugAssignResult(List<AssignResult> assignResultList) {
		for (AssignResult assignment : assignResultList) {
			Requirement requirement = assignment.getRequirement();
			System.out.println(requirement);
			
			for (Seeker seeker : assignment.getSeekers()) {
				System.out.println("\t"+seeker);
			}
		}
	}
	
	private Map<String, Object> recommandResultMap(
			Requirement requirement, 
			List<RankResultComparable<Long, MatchResultComparable<Matcher>>> recommandSeeker) 
	{
		Map<String, Object> requirementMap = new HashMap<String, Object>();
		
		List<Object> seekerListJSON = new ArrayList<Object>();
		for (RankResultComparable<Long, MatchResultComparable<Matcher>> rank : recommandSeeker) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			long rankN = rank.getRank();
			MatchResultComparable<Matcher> match = rank.getData();
			Matcher matcher = match.getData();
			MatchResult matchResult = match.getMatchResult();
			Map<String, Double> matchScore = matchResult.getMatchScore();
			
			map.put("score", matchScore);
			map.put("matcher", matcher.getBuildJSON());
			map.put("rank", rankN);
			
//			System.out.println(map);
			seekerListJSON.add(map);
		}
		
		requirementMap.put("seeker", seekerListJSON);
		requirementMap.put("total", recommandSeeker.size());
		requirementMap.put("requirement", requirement.getBuildJSON());
		
		return requirementMap;
	}
	
	// @return rank 순위(0부터 시작)
	private long rankMatch(
			Matcher compareMatcher, 
			List<? extends Matcher> matcherAll, 
			Matcher rankMatcher)
	{
		long ranking = 0;
		
		if (matcherAll.isEmpty()) {
			throw new IllegalArgumentException("matcher list is empty");
		}
		
		// MatchResult 정렬을 위한 우선순위 큐
		PriorityQueue<MatchResultComparable<Matcher>> topQueue
			= new PriorityQueue<>(matcherAll.size());
		
		// 모든 매 정보 검색
		for (Matcher matcher : matcherAll) {
			// 매칭함수 결과	
			MatchResult result = compareMatcher.matchResult(matcher);
			
			topQueue.add(
					new MatchResultComparable<Matcher>(result, matcher));			
		}
		while (!topQueue.isEmpty()) {
			// FIXME: equals? or ==?
			if (rankMatcher.equals(topQueue.poll().getData())) {
				return ranking;
			}
			ranking++;
		}
		return ranking;
	}
		
	/**
	 * 기준과 매칭률이 높은 매칭 N개 선출
	 * @param compareMatcher 기준이 되는 매칭
	 * @param matcherAll 기준과 비교할 매칭
	 * @param topN 선출 갯수
	 * @return 기준과 매칭률이 높은 매칭 N개 리스트 
	 */
	private List<MatchResultComparable<Matcher>> topMatch(
			Matcher compareMatcher, 
			List<? extends Matcher> matcherAll,
			int topN) {
		
		// 매칭 점수 우선순위 큐
		PriorityQueue<MatchResultComparable<Matcher>> topQueue
			= new PriorityQueue<>(topN);
		
		// 비교할 매칭들
		for (Matcher matcher : matcherAll) {
			// 매칭함수 수행 결과로 매칭점수 획득
			MatchResult result = compareMatcher.matchResult(matcher);
			// 매칭점수가 높은 우선순위 큐
			MatchResultComparable<Matcher> compare 
				= new MatchResultComparable<Matcher>(result, matcher);
			
			topQueue.add(compare);
		}
		
		List<MatchResultComparable<Matcher>> matchers = new ArrayList<>();
		// 매칭률이 높은 매칭 N개 선출
		while (!topQueue.isEmpty() && (topN-- > 0)) {
			matchers.add(topQueue.poll());
		}
		// 우선순위 큐 메모리 정리
		topQueue.clear();
		topQueue = null;
		
		return matchers;
	}
	
	/*
	public static void main(String [] args) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> m1 = new HashMap<>();
		Map<String, Object> m2 = new HashMap<>();
		Map<String, Object> m3 = new HashMap<>();
		
		m1.put("m1", "m1");
		m2.put("m2", "m2");
		List list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		m3.put("m3", list);
		
		ObjectMapper m = new ObjectMapper();
		String out = m.writeValueAsString(m3);
		System.out.println(out);
	}
	*/
}
