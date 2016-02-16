package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public enum Nation implements ContextSchemable {
	ETC("그외", 2),
	CHA("한국", 1),
	KOR("중국", 0);
	
	final int value;
	final String name;
		
	Nation(String name, int value) {
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
	
	public static Nation valueOf(int value) {
		switch (value) {
		case 0: return CHA;
		case 1: return KOR;
		case 2: return ETC;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}

	public List<Object> getContextSchemaData() {
		List<Object> list
			= new ArrayList<Object>(Arrays.asList(Nation.values()));
		Collections.reverse(list);
		return list;
	}
	
	public String toString() {
		return this.name;
	}
	
	public String getOriginalName() {
		return name();
	}
};
