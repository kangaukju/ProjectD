package kr.co.projecta.matching.user.types;

import java.util.ArrayList;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public class WorkAbility implements ContextSchemable {
	public static final int ASSIST = 2;
	public static final int KITCHEN = 1;
	public static final int SERVING = 0;
	
	int workAbility;

	public WorkAbility() {
		
	}
	
	public WorkAbility(int workAbility) {
		this.workAbility = workAbility;
	}

	public int getWorkAbility() {
		return workAbility;
	}

	public void setWorkAbility(int workAbility) {
		this.workAbility = workAbility;
	}

	public static WorkAbility valueOf(int workAbility) {
		switch (workAbility) {
		case ASSIST: return new WorkAbility(ASSIST);
		case KITCHEN: return new WorkAbility(KITCHEN);
		case SERVING: return new WorkAbility(SERVING);
		default: throw new AssertionError("Unknown value: "+workAbility);
		}
	}
	
	public String toString() {
		switch (workAbility) {
		case ASSIST: return "조무사";
		case KITCHEN: return "주방보조";
		case SERVING: return "홀서빙";
		default: throw new AssertionError("Unknown value: "+workAbility);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<Object>();
		list.add(new WorkAbility(ASSIST));
		list.add(new WorkAbility(KITCHEN));
		list.add(new WorkAbility(SERVING));
		return list;
	}
	
	public static WorkAbility valueOf(String value) {
		if ("조무사".equals(value)) {
			return new WorkAbility(ASSIST);
		}
		if ("주방보조".equals(value)) {
			return new WorkAbility(KITCHEN);
		}
		if ("홀서빙".equals(value)) {
			return new WorkAbility(SERVING);
		}
		throw new AssertionError("Unknown value: "+value);
	}
}