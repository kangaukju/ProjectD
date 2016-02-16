package kr.co.projecta.matching.match;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import kr.co.projecta.matching.dao.DAO;
import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.match.MatchResult.MatchResultComparable;
import kr.co.projecta.matching.match.MatchResult.RankResultComparable;
import kr.co.projecta.matching.user.Matcher;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;

public class MatchService {
	Plogger log = Plogger.getLogger(this.getClass());
	
	boolean doubleMatching = true;
	DAO<Matcher> seekerDAO;
	DAO<Matcher> requirementDAO;
	int topLimit = 5;
	
	// 양방향 매칭 시 가중치를 부여할 매칭타켓
	static enum MATCHER_PRIORITY { 
		NONE, 
		SEEKER, 
		REQUIREMENT
	};
	MATCHER_PRIORITY matcherPriority = MATCHER_PRIORITY.NONE;
	
	boolean multiMatching = false;
	
	
	public int getTopLimit() {
		return topLimit;
	}
	public void setTopLimit(int topLimit) {
		this.topLimit = topLimit;
	}
	public boolean isMultiMatching() {
		return multiMatching;
	}
	public void setMultiMatching(boolean multiMatching) {
		this.multiMatching = multiMatching;
	}
	public void setSeekerPriority() {
		matcherPriority = MATCHER_PRIORITY.SEEKER;
	}
	public void setRequirementPriority() {
		matcherPriority = MATCHER_PRIORITY.REQUIREMENT;
	}	
	public boolean isDoubleMatching() {
		return doubleMatching;
	}
	public void setDoubleMatching(boolean doubleMatching) {
		this.doubleMatching = doubleMatching;
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
		List<Matcher> seekerAll = seekerDAO.selectList();
		List<Matcher> requirementAll = requirementDAO.selectList(null);
		
		if (doubleMatching) {
			// 양방향 매칭
			twoWayMatching(seekerAll, requirementAll);
		} else {
			// 단방향 매칭: 채용정보(requirement) 기준
			oneWayMatching(seekerAll, requirementAll);
		}
	}
	
	/**
	 * twoWayMatching: 양방향 매칭
	 * @param seekerAll
	 * @param requirementAll
	 */
	private void twoWayMatching(List<Matcher> seekerAll, List<Matcher> requirementAll) {
		long requirementRank;
		List<Matcher> recommandSeekers;
		
		// 모든 채용정보 검색
		for (Matcher requirement : requirementAll) {
			// 채용정보와 매칭률이 높은 구직자 N명 선출
			recommandSeekers = topMatch(requirement, seekerAll);
			/*
			if (log.isDebug()) {
				log.d("\t["+requirement+"'s top "+topLimit+"]");
				int i=0;
				for (Matcher m : recommandSeekers) {
					log.d("\t["+i+"] "+m);
				}
			}
			*/
			
			PriorityQueue<RankResultComparable<Long, Matcher>> rank
				= new PriorityQueue<>(requirementAll.size());
			
			long seekerRank = 0;
			long matcherScore;
			
			// 선출된 구직자 입장에서
			for (Matcher seeker : recommandSeekers) {
				// 구직자 입장에서 해당 채용정보의 매칭순위
				requirementRank = rankMatch(seeker, requirementAll, requirement);
				
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
									
//				log.d("["+requirement+"] "+seeker+	" = "+requirementRank+"+"+ seekerRank+" = " +(requirementRank + seekerRank));
				
				rank.add(
						new RankResultComparable<Long, Matcher>(matcherScore, seeker));
				
				seekerRank++;
			}
			
			// 해당 채용정보에 가장 (양방향)매칭률이 좋은 구직자 N명 선출
			int topN = topLimit;
			List<Seeker> bestSeekers = new ArrayList<Seeker>();
			while (!rank.isEmpty() && (topN-- > 0)) {
				Seeker bestSeeker = (Seeker) rank.poll().getData();
				if (!multiMatching) {
					seekerAll.remove(bestSeeker);
				}
				bestSeekers.add(bestSeeker);
			}
 
			recommandAction((Requirement) requirement, bestSeekers);
		}
	}
	
	/**
	 * oneWayMatching
	 * @param seekerAll
	 * @param requirementAll
	 */
	private void oneWayMatching(List<Matcher> seekerAll, List<Matcher> requirementAll) {
		List<? extends Matcher> recommandSeekers;
		
		// 모든 배정요청 정보 검색
		for (Matcher requirement : requirementAll) {
			// 매칭률 높은 순위로 구직자 정보가 담겨져있다.
			recommandSeekers = topMatch(requirement, seekerAll);
			 
			recommandAction((Requirement) requirement, (List<Seeker>) recommandSeekers);
		}
	}
	
	/**
	 * 매칭률이 높은 구직자 선출 이후에 해야할 비즈니스 로직 구현
	 * @param requirement: 채용정보
	 * @param recommandSeeker: 추천된 구직자
	 */
	private void recommandAction(Requirement requirement, List<Seeker> recommandSeeker) {
		long rank = 0;
		log.d("<<< ["+requirement+"] recommand seekers >>>");
		for (Seeker seeker : recommandSeeker) {
			log.d("\t[rank: "+(rank++)+"] => "+seeker);
			
			// TODO: 추천된 채용정보에 구직자 알림 서비스 구현
		}
	}
	
	
	// @return rank 순위(0부터 시작)
	private long rankMatch(Matcher compareMatcher, List<Matcher> matcherAll, Matcher rankMatcher) {
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
	private List<Matcher> topMatch(Matcher compareMatcher, List<Matcher> matcherAll) {
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
		
		List<Matcher> matchers = new ArrayList<>();
		// 매칭률이 높은 매칭 N개 선출
		while (!topQueue.isEmpty() && (topN-- > 0)) {
			matchers.add(topQueue.poll().getData());
		}
		// 우선순위 큐 메모리 정리
		topQueue.clear();
		topQueue = null;
		
		return matchers;
	}
}
