package kr.co.projecta.matching.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.ibatis.parsing.ParsingException;

import kr.co.projecta.matching.log.Plogger;

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
	 * 월 더하기
	 */
	public static Date addMonth(String yyyy_mm_dd, int month) {
		Calendar c = Calendar.getInstance(seoul);
		c.setTime(getDateYYYYMMDD(yyyy_mm_dd));
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}
	public static Date addMonth(Date date, int month) {
		Calendar c = Calendar.getInstance(seoul);
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}
	/**
	 * 시간 더하기
	 */
	public static Date addHour(Date date, int hour) {
		Calendar c = Calendar.getInstance(seoul);
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}
	
	/**
	 * 날짜 변환 yyyy-mm-dd hh:mm:ss
	 */
	public static Date getDateYYYYMMDDHHMMSS(String yyyy_mm_dd_hh_mm_ss) {
		try {
			return yyyy_mm_dd_hh_mm_ss_format.parse(yyyy_mm_dd_hh_mm_ss);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}	
	public static Date getDate(String yyyy_mm_dd_hh_mm_ss) {
		return getDateYYYYMMDDHHMMSS(yyyy_mm_dd_hh_mm_ss);
	}
	/**
	 * 날짜 변환 yyyy-mm-dd hh:mm
	 */
	public static Date getDateYYYYMMDDHHMM(String yyyy_mm_dd_hh_mm) {
		try {
			return yyyy_mm_dd_hh_mm_format.parse(yyyy_mm_dd_hh_mm);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	/**
	 * 날짜 변환 yyyy-mm-dd hh
	 */
	public static Date getDateYYYYMMDDHH(String yyyy_mm_dd_hh) {
		try {
			return yyyy_mm_dd_hh_format.parse(yyyy_mm_dd_hh);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	/**
	 * 날짜 변환 yyyy-mm-dd
	 */
	public static Date getDateYYYYMMDD(String yyyy_mm_dd) {
		try {
			return yyyy_mm_dd_format.parse(yyyy_mm_dd);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	/**
	 * 날짜 변환 yyyy-mm
	 */
	public static Date getDateYYYYMM(String yyyy_mm) {
		try {
			return yyyy_mm_format.parse(yyyy_mm);
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	/**
	 * 날짜 변환 yyyy
	 */
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
	
	/**
	 * 문자열 날짜를 가져온다.
	 * @return
	 */
	public static String formatYYYY(Date date) {
		return yyyy_format.format(date);
	}
	public static String formatYYYYMM(Date date) {
		return yyyy_mm_format.format(date);
	}
	public static String formatYYYYMMDD(Date date) {
		return yyyy_mm_dd_format.format(date);
	}
	public static String formatYYYYMMDDHH(Date date) {
		return yyyy_mm_dd_hh_format.format(date);
	}
	public static String formatYYYYMMDDHHMM(Date date) {
		return yyyy_mm_dd_hh_mm_format.format(date);
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
	public static int nowYear() {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}
	public static int nowMonth() {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(new Date());
		return calendar.get(Calendar.MONTH);
	}
	public static int nowDay() {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(new Date());
		return calendar.get(Calendar.DATE);
	}
	public static int nowHour() {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(new Date());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	public static int nowMinute() {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(new Date());
		return calendar.get(Calendar.MINUTE);
	}
	public static int nowSecond() {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(new Date());
		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 날짜/시간 정보 얻는다.
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}
	
	public static void main(String [] args) {
		System.out.println(getHour(new Date()));
	}
}
