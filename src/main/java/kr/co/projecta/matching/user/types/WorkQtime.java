package kr.co.projecta.matching.user.types;

import java.io.Serializable;
import java.util.Date;

import kr.co.projecta.matching.util.Times;

public class WorkQtime 
	extends BitMatch
	implements Serializable
{
	private static final long serialVersionUID = 4799916769930767954L;

	public WorkQtime() {
		
	}
	
	public WorkQtime(int value) {
		super(value);
	}
	
	public int getMaxLength() {
		return QtimeBit.SIZE;
	}
	
	public int getWorkQtime() {
		return getValue();
	}
	
	public void setWorkQtime(int workQtime) {
		setValue(workQtime);
	}
	
	public static WorkQtime valueOf(String []qtimeBitNames) {
		int value = 0;
		if (qtimeBitNames == null) {
			return null;
		}
		for (String name : qtimeBitNames) {
			value |= QtimeBit.valueOf(name).getQtimeBit();
		}
		return new WorkQtime(value);
	}
	
	public String toString() {
		StringBuffer sb = null;
		for (int i=0, b=1; i<QtimeBit.SIZE; i++, b <<= 1) {
			if ((b & value) == b) {
				if (sb == null) {
					sb = new StringBuffer();
				} else {
					sb.append(",");
				}
				sb.append(QtimeBit.valueOf(b));
			}
		}
		if (sb == null) {
			return "";
		}
		return sb.toString();
	}
	
	public static WorkQtime valueOf(int value) {
		return new WorkQtime(value);
	}
	
	public static WorkQtime valueOf(String value) {
		return valueOf(Integer.valueOf(value));
	}
	
	private void append(QtimeBit qtimeBit) {
		this.value |= qtimeBit.getQtimeBit();
	}
	
	/**
	 * 출근시간 부터 근무시간을 계산하여 WorkQtime 객체 값을 생성한다.
	 * 예시1>
	 * 출근시간: 08:00
	 * 근무시간: 8
	 * 근무시간: 08:00 ~ 16:00(오후 04:00)
	 * QtimeBit 집합: Q1(오전), Q2(오후)
	 * 
	 * 예시2>
	 * 출근시간: 08:00
	 * 근무시간: 10
	 * 근무시간: 08:00 ~ 18:00(오후 06:00)
	 * QtimeBit 집합: Q1(오전), Q2(오후), Q3(야간)
	 *  
	 * @param date
	 * @param workTime
	 * @return
	 */
	public static WorkQtime valueOf(Date date, int workTime) {
		int startQtime = Times.getHour(date) / 6;
		int endQtime = Times.getHour(Times.addHour(date, workTime)) / 6;
		WorkQtime workQtime = new WorkQtime(0);
		
		if (startQtime > endQtime) {
			for (int i=startQtime; i<QtimeBit.SIZE; i++) {
				workQtime.append(QtimeBit.valueOf(1 << i));
			}
			for (int i=0; i<=startQtime; i++) {
				workQtime.append(QtimeBit.valueOf(1 << i));
			}
		}
		else {
			for (int i=startQtime; i<=endQtime; i++) {
				workQtime.append(QtimeBit.valueOf(1 << i));
			}
		}		
		return workQtime;
	}
	
	public static void main(String [] args) {
		WorkQtime q1 = new WorkQtime(
				QtimeBit.Q2| 
				QtimeBit.Q4);
		WorkQtime q2 = new WorkQtime(
				QtimeBit.Q2| 
				QtimeBit.Q3| 
				QtimeBit.Q4);
		
		System.out.println(q1);
		System.out.println(q2);

		System.out.println(q1.howMatch(q2));		
		System.out.println(q1.howMatchPercent(q2));
		
		Date workTime = Times.getDateYYYYMMDDHHMM("2016-04-21 08:00");
		int howlong = 9;
		// 9 10 11 12 13 14 15 16 17 18
		System.out.println(Times.getHour(workTime)+":00 ~ "+Times.getHour(Times.addHour(workTime, howlong))+":00");
		System.out.println(WorkQtime.valueOf(workTime, howlong));
	}


}
