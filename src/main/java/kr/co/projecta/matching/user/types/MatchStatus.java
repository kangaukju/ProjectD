package kr.co.projecta.matching.user.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public class MatchStatus 
	implements ContextSchemable, Serializable 
{
	private static final long serialVersionUID = 7173989424214014325L;
	
	public static final int INCOMPLETION = 0;	// 배정중
	public static final int COMPLETION = 1; // 배정완료
	
	public static final int DETERMINE = 2; // 배정확정 (구직자 모두가 확정함)
	
	int matchStatus;
	
	public MatchStatus() {
		
	}

	public MatchStatus(int matchStatus) {
		this.matchStatus = matchStatus;
	}

	public int getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(int matchStatus) {
		this.matchStatus = matchStatus;
	}
	
	public static MatchStatus valueOf(int matchStatus) {
		switch (matchStatus) {
		case INCOMPLETION: return new MatchStatus(INCOMPLETION);
		case COMPLETION: return new MatchStatus(COMPLETION);
		default: throw new AssertionError("Unknown value: "+matchStatus);
		}
	}
	
	public String toString() {
		switch (matchStatus) {
		case INCOMPLETION: return "매칭중";
		case COMPLETION: return "매칭완료";
		default: throw new AssertionError("Unknown value: "+matchStatus);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<Object>();
		list.add(new MatchStatus(INCOMPLETION));
		list.add(new MatchStatus(COMPLETION));
		return list;
	}
	
	public static MatchStatus valueOf(String value) {
		if ("매칭중".equals(value)) {
			return new MatchStatus(INCOMPLETION);
		}
		if ("매칭완료".equals(value)) {
			return new MatchStatus(COMPLETION);
		}
		throw new AssertionError("Unknown value: "+value);
	}
}
