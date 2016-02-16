package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public enum Gender implements ContextSchemable {	
	FEMALE("여자", 0),
	MALE("남자", 1);
	
	final int value;
	final String name;
	
	Gender(String name, int value) {
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
	
	public static Gender valueOf(int value) {
		switch (value) {
		case 0: return FEMALE;
		case 1: return MALE;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list
			= new ArrayList<Object>(Arrays.asList(Gender.values()));
		return list;
	}
	
	public String toString() {
		return this.name;
	}
	
	public String getOriginalName() {
		return name();
	}
	
	public static void main(String [] args) {
		Gender g = Gender.valueOf("MALE");
		System.out.println(g);
	}
}
