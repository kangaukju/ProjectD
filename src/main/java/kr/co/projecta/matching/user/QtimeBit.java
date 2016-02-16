package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;


public	enum QtimeBit implements ContextSchemable {
	/*
	Q4("야간(18:00 ~ 24:00)", 1<<3), // 18 ~ 24
	Q3("오후(12:00 ~ 18:00)", 1<<2), // 12 ~ 18
	Q2("오전(06:00 ~ 12:00)", 1<<1), // 6  ~ 12
	Q1("새벽(00:00 ~ 06:00)", 1<<0); // 0  ~ 6
	*/
	Q4("야간", 1<<3), // 18 ~ 24
	Q3("오후", 1<<2), // 12 ~ 18
	Q2("오전", 1<<1), // 6  ~ 12
	Q1("새벽", 1<<0); // 0  ~ 6
	
	final int value;
	final String name;
	
	QtimeBit(String name, int value) {
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
	
	public static QtimeBit valueOf(int value) {
		switch (value) {
		case (1<<0): return Q1;
		case (1<<1): return Q2;
		case (1<<2): return Q3;
		case (1<<3): return Q4;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}

	public List<Object> getContextSchemaData() {
		List<Object> list
			= new ArrayList<Object>(Arrays.asList(QtimeBit.values()));
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