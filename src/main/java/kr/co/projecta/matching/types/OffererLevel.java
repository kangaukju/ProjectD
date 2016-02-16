package kr.co.projecta.matching.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public enum OffererLevel implements ContextSchemable {
	PREMIUM("프리미엄", 1, null),
	BASIC("기본", 0, PREMIUM);
	
	final int value;
	final String name;
	final OffererLevel next;
	
	OffererLevel(String name, int value, OffererLevel next) {
		this.name = name;
		this.value = value;
		this.next = next;
	}
	
	public String getName() {
		return name;
	}
	
	public OffererLevel nextLevel() {
		return next;
	}
	
	public int intValue() {
		return value;
	}
	
	public int getValue() {
		return intValue();
	}
	
	public static OffererLevel valueOf(int value) {
		switch (value) {
		case 0: return BASIC;
		case 1: return PREMIUM;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}

	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<Object>(Arrays.asList(OffererLevel.values()));
		Collections.reverse(list);
		return list;
	}

	public String getOriginalName() {
		return name();
	}
};
