package kr.co.projecta.matching.user.types;

import java.util.ArrayList;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

/**
 * Quater Time Bit (하루를 6시간 단위로 4등분한 시간)
 * @author root
 *
 */
public class QtimeBit implements ContextSchemable {	
	/*
	Q4("야간(18:00 ~ 24:00)", 1<<3), // 18 ~ 24
	Q3("오후(12:00 ~ 18:00)", 1<<2), // 12 ~ 18
	Q2("오전(06:00 ~ 12:00)", 1<<1), // 6  ~ 12
	Q1("새벽(00:00 ~ 06:00)", 1<<0); // 0  ~ 6
	*/
	public static final int Q4 = 1<<3; // 18 ~ 24
	public static final int Q3 = 1<<2; // 12 ~ 18
	public static final int Q2 = 1<<1; // 6  ~ 12
	public static final int Q1 = 1<<0; // 0  ~ 6
	public static final int SIZE = 4;
	
	int qtimeBit;
	

	public QtimeBit() {
		
	}

	public QtimeBit(int qtimeBit) {
		this.qtimeBit = qtimeBit;
	}
	
	public int getQtimeBit() {
		return qtimeBit;
	}

	public void setQtimeBit(int qtimeBit) {
		this.qtimeBit = qtimeBit;
	}


	public static QtimeBit valueOf(int qtimeBit) {
		switch (qtimeBit) {
		case Q4: return new QtimeBit(Q4);
		case Q3: return new QtimeBit(Q3);
		case Q2: return new QtimeBit(Q2);
		case Q1: return new QtimeBit(Q1);
		default: throw new AssertionError("Unknown value: "+qtimeBit);
		}
	}
	
	public String toString() {
		switch (qtimeBit) {
		case Q4: return "야간";
		case Q3: return "오후";
		case Q2: return "오전";
		case Q1: return "새벽";
		default: throw new AssertionError("Unknown value: "+qtimeBit);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<Object>();
		list.add(new QtimeBit(Q4));
		list.add(new QtimeBit(Q3));
		list.add(new QtimeBit(Q2));
		list.add(new QtimeBit(Q1));
		return list;
	}
	
	public static QtimeBit valueOf(String value) {
		if ("야간".equals(value)) {
			return new QtimeBit(Q4);
		}
		if ("오후".equals(value)) {
			return new QtimeBit(Q3);
		}
		if ("오전".equals(value)) {
			return new QtimeBit(Q2);
		}
		if ("새벽".equals(value)) {
			return new QtimeBit(Q1);
		}
		throw new AssertionError("Unknown value: "+value);
	}
}
