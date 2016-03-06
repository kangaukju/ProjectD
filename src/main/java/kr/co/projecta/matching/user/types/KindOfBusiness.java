package kr.co.projecta.matching.user.types;

import java.util.Arrays;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public class KindOfBusiness implements ContextSchemable {
	int kindOfBusiness;
	
	public static String [] kinds 
		= new String[] {"한식", "양식", "중식", "일식", "주점", "뷔페", "기타"};

	public KindOfBusiness() {		
	}
	
	public int getKindOfBusiness() {
		return kindOfBusiness;
	}

	public void setKindOfBusiness(int kindOfBusiness) {
		this.kindOfBusiness = kindOfBusiness;
	}
	
	public static int valueOf(String kind) {
		int index = 0;
		for (String k : kinds) {
			if (kind.equals(k)) {
				return index;
			}
			index++;
		}
		throw new AssertionError("Unknown value: "+kind);
	}
	
	public static String valueOf(int value) {
		try {
			return kinds[value];
		} catch (Exception e) {
			throw new AssertionError("Unknown value: "+value);	
		}		
	}

	public List<Object> getContextSchemaData() {
		return Arrays.asList(kinds);
	}
}
