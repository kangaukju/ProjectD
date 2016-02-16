package kr.co.projecta.matching.match;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.json.simple.JSONObject;

import kr.co.projecta.matching.dao.DAO;
import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.match.MatchResult.MatchResultComparable;
import kr.co.projecta.matching.match.MatchResult.RankResultComparable;
import kr.co.projecta.matching.user.Matcher;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.util.Times;

public class MatchService2 {
	Plogger log = Plogger.getLogger(this.getClass());
	
	DAO<? extends Matcher> seekerDAO;
	DAO<? extends Matcher> requirementDAO;
	int topLimit = 5;
	
	// 양방향 매칭 시 가중치를 부여할 매칭타켓
	static enum MATCHER_PRIORITY { 
		NONE, 
		SEEKER, 
		REQUIREMENT
	};
	MATCHER_PRIORITY matcherPriority = MATCHER_PRIORITY.NONE;
	
	String resultExportPath;
	
	public MatchService2(
			DAO<? extends Matcher> seekerDAO, 
			DAO<? extends Matcher> requirementDAO, 
			int topLimit) {
		this(seekerDAO, requirementDAO, topLimit, MATCHER_PRIORITY.NONE);
	}
	
	public MatchService2(
			DAO<? extends Matcher> seekerDAO, 
			DAO<? extends Matcher> requirementDAO, 
			int topLimit,
			MATCHER_PRIORITY matcherPriority) 
	{
		this.seekerDAO = seekerDAO;
		this.requirementDAO = requirementDAO;
		this.topLimit = topLimit;
		this.matcherPriority = matcherPriority;
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
	public void setSeekerDAO(DAO<Matcher> DAO) {
		this.seekerDAO = DAO;
	}
	public void setRequirementDAO(DAO<Matcher> DAO) {
		this.requirementDAO = DAO;
	}	
	public static long matrixScore(long max, long x, long y) {
		return max - (x + y);
	}
	
	// 자동 매칭 서비스
	synchronized public void autoMatchService() {
		// TODO: DAO의 모든 데이터를 불러온다. 많은 리소스가 요구됨.
		List<? extends Matcher> seekerAll = seekerDAO.selectList();
		List<? extends Matcher> requirementAll = requirementDAO.selectList(null);
		
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
			recommandSeekers = topMatch(requirement, seekerAll);
			
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
			
			// 해당 채용정보에 가장 (양방향)매칭률이 좋은 구직자 N명 선출
			int topN = topLimit;
			List<RankResultComparable<Long, MatchResultComparable<Matcher>>> bestRank = 
					new ArrayList<>();
			while (!rank.isEmpty() && (topN-- > 0)) {
				
				RankResultComparable<Long, MatchResultComparable<Matcher>> r = rank.poll();
				
				seekerAll.remove(r.getData().getData());
				bestRank.add(r);
			}
 
//			recommandAction((Requirement) requirement, bestRank);
			
			if (resultExportPath != null) {
				Requirement require = (Requirement) requirement;
				JSONObject output = recommandActionJSON(require, bestRank);
				exportJSON(output, require.getOffererId(), resultExportPath);
			}
		}
	}
	
	private String exportJSON(JSONObject result, String offererId, String path) {
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
			JSONObject root = new JSONObject();
			root.put("results", result);
			//root.writeJSONString(output);
			output.write(root.toJSONString());
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
			List<RankResultComparable<Long, MatchResultComparable<Matcher>>> recommandSeeker) 
	{
		log.d("<<< ["+requirement+"] recommand seekers >>>");
		
		for (RankResultComparable<Long, MatchResultComparable<Matcher>> rank : recommandSeeker) {
			MatchResultComparable<Matcher> match = rank.getData();
			log.d("\t rank: "+rank.getRank());
			MatchResult matchResult = match.getMatchResult();
			Matcher matcher = match.getData();
			Map<String, Double> matchScore = matchResult.getMatchScore();
			log.d("\t\t ["+matcher+"]");
			for (String key : matchScore.keySet()) {
				log.d("\t\t "+key+": "+matchScore.get(key));
			}
		}
	}
	
	private JSONObject recommandActionJSON(
			Requirement requirement, 
			List<RankResultComparable<Long, MatchResultComparable<Matcher>>> recommandSeeker) 
	{
		JSONObject requirementJSON = new JSONObject();
		
		List<JSONObject> seekerListJSON = new ArrayList<>();
		for (RankResultComparable<Long, MatchResultComparable<Matcher>> rank : recommandSeeker) {
			JSONObject o = new JSONObject();
			
			long rankN = rank.getRank();
			MatchResultComparable<Matcher> match = rank.getData();
			Matcher matcher = match.getData();
			MatchResult matchResult = match.getMatchResult();
			Map<String, Double> matchScore = matchResult.getMatchScore();
			
			o.put("score", matchScore);
			o.put("matcher", matcher.toJSON());
			o.put("rank", rankN);
			
			seekerListJSON.add(o);
		}
		
		requirementJSON.put("seeker", seekerListJSON);
		requirementJSON.put("total", recommandSeeker.size());
		requirementJSON.put("requirement", requirement.toJSON());
		
		return requirementJSON;
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
			List<? extends Matcher> matcherAll) {
		int topN = topLimit;
		
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
}
