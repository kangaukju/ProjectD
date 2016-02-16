package kr.co.projecta.matching.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public enum PayType implements ContextSchemable {
	PAY_3_MON("3개월", 3),
	PAY_1_MON("1개월", 1);
	
	final int value;
	final String name;
	
	PayType(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public int intValue() {
		return value;
	}
	
	public static PayType valueOf(int value) {
		switch (value) {
		case 1: return PAY_1_MON;
		case 3: return PAY_3_MON;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<Object>(Arrays.asList(PayType.values()));
		Collections.reverse(list);
		return list;
	}

	public String getName() {
		return name;
	}

	public String getOriginalName() {
		return name();
	}
}
