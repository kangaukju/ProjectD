package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public enum WorkAbility implements ContextSchemable {
	ASSIST("조무사", 2),
	KITCHEN("주방보조", 1),
	SERVING("홀서빙", 0);
		
	final int value;
	final String name;
	
	WorkAbility(String name, int value) {
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
	
	public static WorkAbility valueOf(int value) {
		switch (value) {
		case 0: return SERVING;
		case 1: return KITCHEN;
		case 2: return ASSIST;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}

	public List<Object> getContextSchemaData() {
		List<Object> list
			= new ArrayList<Object>(Arrays.asList(WorkAbility.values()));
		Collections.reverse(list);
		return list;
	}
	
	public String toString() {
		return this.name;
	}
	
	public String getOriginalName() {
		return name();
	}
}
