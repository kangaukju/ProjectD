package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public enum MdayBit implements ContextSchemable {
	SUN("일", 1<<6), 
	MON("월", 1<<5), 
	TUE("화", 1<<4), 
	WEB("수", 1<<3), 
	THU("목", 1<<2), 
	FRI("금", 1<<1), 
	SAT("토", 1<<0);
	
	final int value;
	final String name;
	
	MdayBit(String name, int value) {
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
	
	public static MdayBit valueOf(int value) {
		switch (value) {
		case (1<<0): return SAT;
		case (1<<1): return FRI;
		case (1<<2): return THU;
		case (1<<3): return WEB;
		case (1<<4): return TUE;
		case (1<<5): return MON;
		case (1<<6): return SUN;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}

	public List<Object> getContextSchemaData() {
		List<Object> list
		= new ArrayList<Object>(Arrays.asList(MdayBit.values()));
	return list;
	}
	
	public String toString() {
		return this.name;
	}
	
	public String getOriginalName() {
		return name();
	}
}
