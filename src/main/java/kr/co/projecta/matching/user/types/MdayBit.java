package kr.co.projecta.matching.user.types;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.ibatis.parsing.ParsingException;

import kr.co.projecta.matching.context.ContextSchemable;


public class MdayBit implements ContextSchemable {
	public static final int SUN = 1<<0;
	public static final int MON = 1<<1;
	public static final int TUE = 1<<2;
	public static final int WEB = 1<<3;
	public static final int THU = 1<<4;
	public static final int FRI = 1<<5;
	public static final int SAT = 1<<6;
	public static final int SIZE = 7; 
	
	int mdayBit;

	public MdayBit() {
		
	}
	
	public MdayBit(int mdayBit) {
		this.mdayBit = mdayBit;
	}
	
	public int getMdayBit() {
		return mdayBit;
	}

	public void setMdayBit(int mdayBit) {
		this.mdayBit = mdayBit;
	}

	public static MdayBit valueOf(int mdayBit) {
		switch (mdayBit) {
		case SUN: return new MdayBit(SUN);		
		case MON: return new MdayBit(MON);
		case TUE: return new MdayBit(TUE);		
		case WEB: return new MdayBit(WEB);
		case THU: return new MdayBit(THU);
		case FRI: return new MdayBit(FRI);
		case SAT: return new MdayBit(SAT);		
		default: throw new AssertionError("Unknown value: "+mdayBit);
		}
	}
	
	public String toString() {
		switch (mdayBit) {
		case SUN: return "일";
		case MON: return "월";
		case TUE: return "화";
		case WEB: return "수";
		case THU: return "목";
		case FRI: return "금";
		case SAT: return "토";
		default: throw new AssertionError("Unknown value: "+mdayBit);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<Object>();
		list.add(new MdayBit(SUN));
		list.add(new MdayBit(MON));
		list.add(new MdayBit(TUE));
		list.add(new MdayBit(WEB));
		list.add(new MdayBit(THU));
		list.add(new MdayBit(FRI));
		list.add(new MdayBit(SAT));
		return list;
	}
	
	public static MdayBit valueOf(String value) {
		if ("일".equals(value)) {
			return new MdayBit(SUN);
		}
		if ("월".equals(value)) {
			return new MdayBit(MON);
		}
		if ("화".equals(value)) {
			return new MdayBit(TUE);
		}
		if ("수".equals(value)) {
			return new MdayBit(WEB);
		}
		if ("목".equals(value)) {
			return new MdayBit(THU);
		}
		if ("금".equals(value)) {
			return new MdayBit(FRI);
		}
		if ("토".equals(value)) {
			return new MdayBit(SAT);
		}
		throw new AssertionError("Unknown value: "+value);
	}
	
	/**
	 * Date(날짜)의 요일을 가져온다. (Date를 MdayBit로 변환)
	 * @param date
	 * @return
	 */
	public static MdayBit getMday(Date date) {
		TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY: return new MdayBit(SUN); 
		case Calendar.MONDAY: return new MdayBit(MON);
		case Calendar.TUESDAY: return new MdayBit(TUE);
		case Calendar.WEDNESDAY: return new MdayBit(WEB);
		case Calendar.THURSDAY: return new MdayBit(THU);
		case Calendar.FRIDAY: return new MdayBit(FRI);
		case Calendar.SATURDAY: return new MdayBit(SAT);
		default: throw new AssertionError("Unknown date: "+date);
		}
	}
	
	public static MdayBit getMday(String date) {
		try {
			SimpleDateFormat yyyy_mm_dd_format = new SimpleDateFormat("yyyy-MM-dd");
			return getMday(yyyy_mm_dd_format.parse(date));
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	
	/**
	 * 오늘 요일을 가져온다.
	 * @return
	 */
	public static MdayBit getMday() {
		return MdayBit.getMday(new Date());
	}
}