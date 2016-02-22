package kr.co.projecta.matching.match;

import java.util.Map;

public interface MatchResult {
	
	public Map<String, Double> getMatchScore();
	
	double getPercent();
	
	// Matching 결과 우선 순위 큐 compare 함
	static class MatchResultComparable<T> 
		implements Comparable<MatchResultComparable<T>> 
	{
		MatchResult result;
		T data;
		MatchResultComparable(MatchResult result, T data) {
			this.result = result;
			this.data = data;
		}
		
		public MatchResult getMatchResult() {
			return result;
		}
		
		public T getData() {
			return data;
		}
		
		public int compareTo(MatchResultComparable<T> o) {
			return (result.getPercent() >= o.result.getPercent()) ? 1 : -1;
		}
	};
	
	/**
	 * Ranking 우선 순위 큐 compare 함
	 * @param <Long> rank 숫자
	 * @param <T>
	 */
	static class RankResultComparable<Long, T>
		implements Comparable<RankResultComparable<Long, T>>
	{
		long rank;
		T data;
		RankResultComparable(long result, T data) {
			this.rank = result;
			this.data = data;
		}
		
		public T getData() {
			return data;
		}
		
		public long getRank() {
			return rank;
		}
		
		public int compareTo(RankResultComparable<Long, T> o) {
			return (rank >= o.rank) ? 1 : -1;
		}
	}
}
