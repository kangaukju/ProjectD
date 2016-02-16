package kr.co.projecta.matching.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;
import kr.co.projecta.matching.user.WorkAbility;

public enum SeekerLevel implements ContextSchemable {
	CHARGED("유료", 1, null),
	FREE("무료", 0, CHARGED);
	
	final int value;
	final String name;
	final SeekerLevel next;
	
	SeekerLevel(String name, int value, SeekerLevel next) {
		this.name = name;
		this.value = value;
		this.next = next;
	}
	
	public SeekerLevel nextLevel() {
		return next;
	}
	
	public int intValue() {
		return value;
	}
	
	public static SeekerLevel valueOf(int value) {
/*
		SeekerLevel [] sls = SeekerLevel.values();
		if (value < 0 || value >= sls.length) {
			throw new AssertionError("Unknown value: "+value);
		}
		return sls[value];
*/
		switch (value) {
		case 0: return FREE;
		case 1: return CHARGED;
		default: throw new AssertionError("Unknown value: "+value);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list
			= new ArrayList<Object>(Arrays.asList(WorkAbility.values()));
		Collections.reverse(list);
		return list;
	}
	
	public String getName() {
		return name;
	}
};
