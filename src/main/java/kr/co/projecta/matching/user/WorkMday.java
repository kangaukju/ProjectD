package kr.co.projecta.matching.user;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class WorkMday extends BitMatch {

	public WorkMday(int value) {
		super(value);
	}
	
	public int getMaxLength() {
		return MdayBit.values().length;
	}
	
	public static WorkMday valueOf(int value) {
		return new WorkMday(value);
	}
	
	public static WorkMday valueOf(String []mdays) {
		int value = 0;
		if (mdays == null)
			return null;
		for (String mday : mdays) {
			value |= MdayBit.valueOf(mday).intValue();
		}
		return new WorkMday(value);
	}
	
	public static WorkMday valueOf(String value) {
		return new WorkMday(Integer.valueOf(value));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (MdayBit mdayBit : MdayBit.values()) {
			if ((value & mdayBit.value) == mdayBit.value) {
				if (index != 0) {
					sb.append(",");
				}
				sb.append(mdayBit.name);
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
	
	public static void main(String [] args) {
		WorkMday m1 = new WorkMday(
				MdayBit.SUN.intValue() |
				MdayBit.SAT.intValue() |
				MdayBit.FRI.intValue() |
				MdayBit.THU.intValue() |
				MdayBit.WEB.intValue() |
				MdayBit.TUE.intValue() |
				MdayBit.MON.intValue());
		WorkMday m2 = new WorkMday(
				MdayBit.SUN.intValue() |
				MdayBit.SAT.intValue() |
				MdayBit.FRI.intValue() |
				MdayBit.WEB.intValue() |
				MdayBit.TUE.intValue());
		
		System.out.println(m1);
		System.out.println(m2);
		
		int matched = m1.howMatch(m2);
		System.out.println(matched);
		
		System.out.println(m1.howMatchPercent(m2));
	}
}
