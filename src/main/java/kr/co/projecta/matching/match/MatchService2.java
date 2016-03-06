package kr.co.projecta.matching.match;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;

import kr.co.projecta.matching.dao.AssignmentDAO;
import kr.co.projecta.matching.dao.HistoryDAO;
import kr.co.projecta.matching.dao.RequirementDAO;
import kr.co.projecta.matching.dao.SeekerDAO;
import kr.co.projecta.matching.history.SeekerNoticeHistory;
import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.match.MatchResult.MatchResultComparable;
import kr.co.projecta.matching.match.MatchResult.RankResultComparable;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;
import kr.co.projecta.matching.user.types.MatchStatus;
import kr.co.projecta.matching.util.Times;

public class MatchService2 {
	transient Plogger log = Plogger.getLogger(this.getClass());
	
	SeekerDAO seekerDAO;
	RequirementDAO requirementDAO;
	AssignmentDAO assignmentCandidateDAO;
	HistoryDAO historyDAO;
	int topLimit = 5;
	
	// 양방향 매칭 시 가중치를 부여할 매칭타켓
	static enum MATCHER_PRIORITY { 
		NONE, 
		SEEKER, 
		REQUIREMENT
	};
	MATCHER_PRIORITY matcherPriority = MATCHER_PRIORITY.NONE;
	
	// 매칭결과 저장 경로
	String resultExportPath;	
	// 최종 매칭된 배정요청 수
	long matchedRequirementCount;
	// 최종 매칭된 구직자 수
	long matchedSeekerCount;

	public MatchService2(
			SeekerDAO seekerDAO, 
			RequirementDAO requirementDAO,
			AssignmentDAO assignmentCandidateDAO,
			HistoryDAO historyDAO,
			int topLimit) {
		this(seekerDAO, requirementDAO, assignmentCandidateDAO, historyDAO,
				topLimit, MATCHER_PRIORITY.NONE);
	}
	
	public MatchService2(
			SeekerDAO seekerDAO, 
			RequirementDAO requirementDAO,
			AssignmentDAO assignmentCandidateDAO,
			HistoryDAO historyDAO,
			int topLimit,
			MATCHER_PRIORITY matcherPriority) 
	{
		this.seekerDAO = seekerDAO;
		this.requirementDAO = requirementDAO;
		this.assignmentCandidateDAO = assignmentCandidateDAO;
		this.historyDAO = historyDAO;
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
	
	// 자동 매칭 서비스
	synchronized public void autoMatchService() {
		log.i("Start autoMatchService");
		// TODO: DAO의 모든 데이터를 불러온다. 많은 리소스가 요구됨.
		List<Seeker> seekerAll = seekerDAO.selectNotAssignSeeker();
		List<Requirement> requirementAll = requirementDAO.selectNotAssignComplete();
		
		// 양방향 매칭
		twoWayMatching(seekerAll, requirementAll);
	}
	
	/**
	 * twoWayMatching: 양방향 매칭
	 * @param seekerAll
	 * @param requirementAll
	 */
	private void twoWayMatching(
			List<Seeker> seekerAll, 
			List<Requirement> requirementAll)
	{
		long requirementRank;
		List<MatchResultComparable<Seeker>> topMatchedSeekerList;
		
		// 모든 채용정보 검색
		for (Requirement requirement : requirementAll) {
			// 채용정보와 매칭률이 높은 구직자 N명 선출
			// 1. topN
			topMatchedSeekerList = topMatchSeekerList(requirement, seekerAll, topLimit);
			
			PriorityQueue<RankResultComparable<MatchResultComparable<Seeker>>> rankMatchedSeekerQueue
				= new PriorityQueue<>(requirementAll.size());
			
			long seekerRank = 0;
			long matcherScore;			
			
			// 선출된 구직자 입장에서
			for (MatchResultComparable<Seeker> topMatchedSeeker : topMatchedSeekerList) {
				// 구직자 입장에서 해당 채용정보의 매칭순위
				// 2. rank
				Seeker seeker = topMatchedSeeker.getData();
				requirementRank = rankMatchRequirement(seeker, requirementAll, requirement);
				
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
				
				rankMatchedSeekerQueue.add(new RankResultComparable<MatchResultComparable<Seeker>>(matcherScore, topMatchedSeeker));
				
				seekerRank++;
			}
			
			// 배정요청에 현재 배정된 구직자 수
			// TODO: DAO 호출로 검색 부하 예상
			int assignedSeekerCount = (int)assignmentCandidateDAO.selectCount(requirement.getId());
			
			// 해당 채용정보에 가장 (양방향)매칭률이 좋은 구직자 N명 선출
			int person = ((Requirement)requirement).getPerson();
			
			// 배정요청에 부족한 인원 만큼 선출한다.
			int needN = person - assignedSeekerCount;
			
			List<RankResultComparable<MatchResultComparable<Seeker>>> recommandSeekerList = new ArrayList<>();
			
			while (!rankMatchedSeekerQueue.isEmpty() && (needN-- > 0)) {				
				RankResultComparable<MatchResultComparable<Seeker>> recommandSeeker = rankMatchedSeekerQueue.poll();
				
				// 다음 매칭률 계산에서는 지금 선정된 구직자를 제외한다.
				seekerAll.remove(recommandSeeker.getData().getData());
				recommandSeekerList.add(recommandSeeker);
			}
 
			if (recommandSeekerList.size() > 0) {				
				recommandAction(requirement, recommandSeekerList);
				
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
			Requirement requirement,
			List<RankResultComparable<MatchResultComparable<Seeker>>> recommandSeekerList) 
	{
		this.matchedRequirementCount++;
		this.matchedSeekerCount += recommandSeekerList.size();
		
		String requirementId = requirement.getId();
		
		// 디버깅
		log.i("<<< ["+requirement+"] recommand seekers >>>");
		for (RankResultComparable<MatchResultComparable<Seeker>> rank : recommandSeekerList) {
			MatchResultComparable<Seeker> match = rank.getData();
			log.i("\t rank: "+rank.getRank());
			MatchResult matchResult = match.getMatchResult();
			Seeker matcher = match.getData();
			Map<String, Double> matchScore = matchResult.getMatchScore();
			log.i("\t\t ["+matcher+"]");
			for (String key : matchScore.keySet()) {
				log.i("\t\t "+key+": "+matchScore.get(key));
			}
		}
		
		// 현재 배정 현황(assignment) 기록
		for (RankResultComparable<MatchResultComparable<Seeker>> rank : recommandSeekerList) {
			MatchResultComparable<Seeker> match = rank.getData();
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
		
		// 
		for (RankResultComparable<MatchResultComparable<Seeker>> rank : recommandSeekerList) {
			MatchResultComparable<Seeker> match = rank.getData();
			Seeker seeker = (Seeker) match.getData();
			// 구직자에게 배정 알림 통보
			if (noticeSeeker(seeker)) {
				historyDAO.insertSeekerNoticeHistory(
						new SeekerNoticeHistory(
							requirement.getId(), 
							requirement.getOffererId(), 
							seeker.getId()));
			}
			///////////////////////////////////////////////
			// 구직자에게 배정 알림 통보 실패 시
			///////////////////////////////////////////////
			else {
				
			}
		}
	}
	
	private boolean noticeSeeker(Seeker seeker) {
		
		return true;
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
	 * 구직자 입장에서 배정요청의 매칭률 순위를 구한다.
	 * @param seeker
	 * @param requirementAll
	 * @param rankMatcher
	 * @return
	 */
	private long rankMatchRequirement(
			Seeker seeker, 
			List<Requirement> requirementAll, 
			Requirement rankMatcher)
	{
		long ranking = 0;
		
		if (requirementAll.isEmpty()) {
			throw new IllegalArgumentException("requirement list is empty");
		}
		
		// 구직자와 배정요청 매칭률이 높은 우선순위 큐
		PriorityQueue<MatchResultComparable<Requirement>> topRequirementQueue
			= new PriorityQueue<>(requirementAll.size());
		
		// 모든 배정요청 검색
		for (Requirement requirement : requirementAll) {
			// 매칭함수 결과	
			MatchResult result = seeker.matchResult(requirement);
			
			topRequirementQueue.add(
					new MatchResultComparable<Requirement>(result, requirement));			
		}
		while (!topRequirementQueue.isEmpty()) {
			// FIXME: equals? or == ?
			if (rankMatcher.equals(topRequirementQueue.poll().getData())) {
				return ranking;
			}
			ranking++;
		}
		// 해당 배정요청의 순위
		return ranking;
	}
		
	/**
	 * 기준과 매칭률이 높은 매칭 N개 선출
	 * @param requirement 기준이 되는 매칭
	 * @param seekerAll 기준과 비교할 매칭
	 * @param topN 선출 갯수
	 * @return 기준과 매칭률이 높은 매칭 N개 리스트 
	 */
	private List<MatchResultComparable<Seeker>> topMatchSeekerList(
			Requirement requirement,
			List<Seeker> seekerAll,
			int topN) {
		
		// 구직자 매칭 점수 우선순위 큐
		PriorityQueue<MatchResultComparable<Seeker>> topSeekerQueue
			= new PriorityQueue<>(topN);
		
		// 비교할 매칭들
		for (Seeker seeker : seekerAll) {
			// 배정요청과 구직자와 매칭률을 계산
			MatchResult result = requirement.matchResult(seeker);
			// 매칭점수가 높은 구직자 비교 연산
			MatchResultComparable<Seeker> seekerCompare 
				= new MatchResultComparable<Seeker>(result, seeker);
			
			topSeekerQueue.add(seekerCompare);
		}
		
		List<MatchResultComparable<Seeker>> matchedSeekerList = new ArrayList<>();
		// 매칭률이 높은 구직자 매칭 N개 선출
		while (!topSeekerQueue.isEmpty() && (topN-- > 0)) {
			matchedSeekerList.add(topSeekerQueue.poll());
		}
		// 우선순위 큐 메모리 정리
		topSeekerQueue.clear();
		topSeekerQueue = null;
		
		return matchedSeekerList;
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
