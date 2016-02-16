package kr.co.projecta.matching.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.ibatis.parsing.ParsingException;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.MdayBit;
import kr.co.projecta.matching.user.WorkQtime;

public class Times {
	
	static Plogger log = Plogger.getLogger(Times.class);
	
	static SimpleDateFormat yyyy_format = new SimpleDateFormat("yyyy");
	static SimpleDateFormat yyyy_mm_format = new SimpleDateFormat("yyyy-MM");
	static SimpleDateFormat yyyy_mm_dd_format = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat yyyy_mm_dd_hh_format = new SimpleDateFormat("yyyy-MM-dd HH");
	static SimpleDateFormat yyyy_mm_dd_hh_mm_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static SimpleDateFormat yyyy_mm_dd_hh_mm_ss_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
	
	/**
	 * 현재 날짜의 요일을 가져온다.
	 * @return
	 */
	public static MdayBit getMday() {
		return getMday(new Date());
	}	
	public static MdayBit getMday(String date) {
		try {
			return getMday(yyyy_mm_dd_format.parse(date));
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	public static MdayBit getMday(Date date) {		
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY: return MdayBit.SUN; 
		case Calendar.MONDAY: return MdayBit.MON;
		case Calendar.TUESDAY: return MdayBit.TUE;
		case Calendar.WEDNESDAY: return MdayBit.WEB;
		case Calendar.THURSDAY: return MdayBit.THU;
		case Calendar.FRIDAY: return MdayBit.FRI;
		case Calendar.SATURDAY: return MdayBit.SAT;
		default: return MdayBit.SUN;
		}
	}
	
	/**
	 * 날짜 더하기
	 * @param yyyy_mm_dd
	 * @param month
	 * @return
	 */
	public static Date addMonth(String yyyy_mm_dd, int month) {
		Calendar c = Calendar.getInstance(seoul);
		c.setTime(getDateYYYYMMDD(yyyy_mm_dd));
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}
	public static Date addHour(Date date, int hour) {
		Calendar c = Calendar.getInstance(seoul);
		c.setTime(date);
		c.add(Calendar.HOUR, hour);
		return c.getTime();
	}
	
	/**
	 * 날짜 변환
	 */
	public static Date getDateYYYYMMDDHHMMSS(String yyyy_mm_dd_hh_mm_ss) {
		try {
			return yyyy_mm_dd_hh_mm_ss_format.parse(yyyy_mm_dd_hh_mm_ss);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	public static Date getDateYYYYMMDDHHMM(String yyyy_mm_dd_hh_mm) {
		try {
			return yyyy_mm_dd_hh_mm_format.parse(yyyy_mm_dd_hh_mm);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	public static Date getDateYYYYMMDDHH(String yyyy_mm_dd_hh) {
		try {
			return yyyy_mm_dd_hh_format.parse(yyyy_mm_dd_hh);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	public static Date getDateYYYYMMDD(String yyyy_mm_dd) {
		try {
			return yyyy_mm_dd_format.parse(yyyy_mm_dd);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}	
	public static Date getDateYYYYMM(String yyyy_mm) {
		try {
			return yyyy_mm_format.parse(yyyy_mm);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}	
	public static Date getDateYYYY(String yyyy) {
		try {
			return yyyy_format.parse(yyyy);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	public static Date getDate(String year, String month, String date) {
		Calendar c = Calendar.getInstance(seoul);
		c.set(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(date));
		return c.getTime();
	}
	public static Date getDate(String yyyy_mm_dd_hh_mm_ss) {
		try {
			return yyyy_mm_dd_hh_mm_ss_format.parse(yyyy_mm_dd_hh_mm_ss);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static String formatYYYYMMDD(Date date) {
		return yyyy_mm_dd_format.format(date);
	}
	public static String formatYYYYMMDDHH(Date date) {
		return yyyy_mm_dd_hh_format.format(date);
	}
	public static String formatYYYYMMDDHHMMSS(Date date) {
		return yyyy_mm_dd_hh_mm_ss_format.format(date);
	}
	
	/**
	 * 오늘 날짜
	 * @return
	 */
	public static Date now() {		
		Calendar c = Calendar.getInstance(seoul);
		c.setTime(new Date());
		return c.getTime();
	}
	
	public static int getHH(Date date) {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public static void main(String [] args) {
		System.out.println(getHH(new Date()));
	}
}
