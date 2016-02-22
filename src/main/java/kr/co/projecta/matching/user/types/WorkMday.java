package kr.co.projecta.matching.user.types;

public class WorkMday extends BitMatch {

	public WorkMday() {
	}
	
	public WorkMday(int value) {
		super(value);
	}
	
	public int getMaxLength() {
		return MdayBit.SIZE;
	}
	
	public int getWorkMday() {
		return getValue();
	}
	
	public void setWorkMday(int workMday) {
		setValue(workMday);
	}
	
	public static WorkMday valueOf(int value) {
		return new WorkMday(value);
	}
	
	public static WorkMday valueOf(String []mdayBitNames) {
		int value = 0;
		if (mdayBitNames == null) {
			return null;
		}
		for (String name : mdayBitNames) {
			value |= MdayBit.valueOf(name).getMdayBit();
		}
		return new WorkMday(value);
	}
	
	public static WorkMday valueOf(String value) {
		return new WorkMday(Integer.valueOf(value));
	}

	public String toString() {
		StringBuffer sb = null;
		for (int i=0, b=1; i<MdayBit.SIZE; i++, b <<= 1) {
			if ((b & value) == b) {
				if (sb == null) {
					sb = new StringBuffer();
				} else {
					sb.append(",");
				}
				sb.append(MdayBit.valueOf(b));
			}
		}
		return sb.toString();
	}
	
	public static void main(String [] args) {
		WorkMday m1 = new WorkMday(
				MdayBit.SUN |
				MdayBit.SAT |
				MdayBit.FRI |
				MdayBit.THU |
				MdayBit.WEB |
				MdayBit.TUE |
				MdayBit.MON);
		WorkMday m2 = new WorkMday(
				MdayBit.SUN |
				MdayBit.SAT |
				MdayBit.FRI |
				MdayBit.WEB |
				MdayBit.TUE);
		
		System.out.println(m1);
		System.out.println(m2);
		
		System.out.println(m1.howMatch(m2));		
		System.out.println(m1.howMatchPercent(m2));
	}
}
