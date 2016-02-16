package kr.co.projecta.matching.user;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import kr.co.projecta.matching.util.Times;

public class WorkQtime extends BitMatch {
	
	public WorkQtime(int value) {
		super(value);
	}
	public void append(QtimeBit qtimeBit) {
		this.value |= qtimeBit.intValue();
	}
	
	@Override
	public int getMaxLength() {
		return QtimeBit.values().length;
	}
	
	public static WorkQtime valueOf(String []qtimes) {
		int value = 0;
		if (qtimes == null)
			return null;
		for (String qtime : qtimes) {
			value |= QtimeBit.valueOf(qtime).intValue();
		}
		return new WorkQtime(value);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (QtimeBit qtimeBit : QtimeBit.values()) {
			if ((value & qtimeBit.value) == qtimeBit.value) {
				if (index != 0) {
					sb.append(",");
				}
				sb.append(qtimeBit.name);
				index++;
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static WorkQtime valueOf(int value) {
		return new WorkQtime(value);
	}
	
	public static WorkQtime valueOf(String value) {
		return valueOf(Integer.valueOf(value));
	}
	
	public static WorkQtime valueOf(Date date, int workTime) {
		int startQtime = Times.getHH(date) / 6;
		int endQtime = Times.getHH(Times.addHour(date, workTime)) / 6;
		WorkQtime workQtime = new WorkQtime(0);
		
//		System.out.println(Times.getHH(date)+":00 ~ "+Times.getHH(Times.addHour(date, workTime))+":00");		
		if (startQtime > endQtime) {
			for (int i=startQtime; i<QtimeBit.values().length; i++) {
				workQtime.append(QtimeBit.valueOf(1 << i));
			}
			for (int i=0; i<=startQtime; i++) {
				workQtime.append(QtimeBit.valueOf(1 << i));
			}
		} else {
			for (int i=startQtime; i<=endQtime; i++) {
				workQtime.append(QtimeBit.valueOf(1 << i));
			}
		}
		
		return workQtime;
	}
	
	public static void main(String [] args) {
		WorkQtime q1 = new WorkQtime(
				QtimeBit.Q2.value | 
				QtimeBit.Q4.value);
		WorkQtime q2 = new WorkQtime(
				QtimeBit.Q2.value | 
				QtimeBit.Q3.value | 
				QtimeBit.Q4.value);
		
		System.out.println(q1);
		System.out.println(q2);

		int matched = q1.howMatch(q2);
		System.out.println(matched);
		
		System.out.println(q1.howMatchPercent(q2));
		
		System.out.println(WorkQtime.valueOf(new Date(), 12));
	}


}
