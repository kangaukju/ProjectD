package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public enum MatchStatus implements ContextSchemable {	
	COMPLETION("매칭완료", 1),
	INCOMPLETION("매칭중", 0);
	
	final int value;
	final String name;
	
	MatchStatus(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public int intValue() {
		return value;
	}
	
	public int getValue() {
		return intValue();
	}
	
	public String toString() {
		return this.name;
	}
	
	public static MatchStatus valueOf(int value) {
		switch (value) {
		case 0: return COMPLETION;
		case 1: return INCOMPLETION;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}

	public List<Object> getContextSchemaData() {
		List<Object> list
			= new ArrayList<Object>(Arrays.asList(MatchStatus.values()));
		Collections.reverse(list);
		return list;
	}
	
	public static void main(String [] args) {
		MatchStatus s = INCOMPLETION;
		System.out.println(s.toString());
	}
	
	public String getOriginalName() {
		return name();
	}
}
