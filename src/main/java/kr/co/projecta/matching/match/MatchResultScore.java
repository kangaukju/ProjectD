package kr.co.projecta.matching.match;

import java.util.HashMap;
import java.util.Map;

public class MatchResultScore implements MatchResult {
	double score;
	double max;
	long addcount;
	Map<String, Double> matchScore = new HashMap<>();
	
	public MatchResultScore(double max) {
		this.score = 0;
		this.max = max;
		this.addcount = 0;
	}
	
	public void addScore(Double score) {
		addScore(null, score, null);
	}
	
	public void addScore(String name, Double score) {
		addScore(name, score, null);
	}
	
	public void addScore(String name, Double score, String desc) {
		if (name != null) {
			matchScore.put(name, score);
		}	
		this.score += score;
		addcount++;
	}
	
	public double getPercent() {
		return ((double)(score / (max * addcount))) * 100;
	}
	
	public Map<String, Double> getMatchScore() {
		return matchScore;
	}
}
