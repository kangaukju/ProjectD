package kr.co.projecta.matching.user.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;


public class Gender 
	implements ContextSchemable, Serializable 
{
	private static final long serialVersionUID = -7108734017581481233L;
	
	public static final int FEMALE = 0;
	public static final int MALE = 1;
	
	int gender;
	
	public Gender() {
		
	}
	
	public Gender(int gender) {
		this.gender = gender;
	}
	
	public int getGender() {
		return gender;
	}
	
	public void setGender(int gender) {
		this.gender = gender;
	}

	public static Gender valueOf(int gender) {
		switch (gender) {
		case FEMALE: return new Gender(FEMALE);
		case MALE: return new Gender(MALE);
		default: throw new AssertionError("Unknown value: "+gender);
		}
	}
	
	public String toString() {
		switch (gender) {
		case FEMALE: return new String("여자");
		case MALE: return new String("남자");
		default: throw new AssertionError("Unknown value: "+gender);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<Object>();
		list.add(new Gender(FEMALE));
		list.add(new Gender(MALE));
		return list;
	}
	
	public static Gender valueOf(String value) {
		if ("남자".equals(value)) {
			return new Gender(MALE);
		}
		if ("여자".equals(value)) {
			return new Gender(FEMALE);
		}
		throw new AssertionError("Unknown value: "+value);
	}
}