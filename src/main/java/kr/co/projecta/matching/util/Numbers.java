package kr.co.projecta.matching.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.parsing.ParsingException;

import kr.co.projecta.matching.exception.InvalidPhoneNumberException;

public class Numbers {

	/**
	 * 시간 형식 정의
	 */
	static SimpleDateFormat yyyy_format = new SimpleDateFormat("yyyy");
	static SimpleDateFormat yyyy_mm_format = new SimpleDateFormat("yyyy-MM");
	static SimpleDateFormat yyyy_mm_dd_format = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat yyyy_mm_dd_HH_format = new SimpleDateFormat("yyyy-MM-dd HH");
	static SimpleDateFormat yyyy_mm_dd_HH_MM_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static SimpleDateFormat yyyy_mm_dd_HH_MM_SS_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
	
	public static Integer bitOR(String [] values) {
		int value = 0;
		for (String v : values) {
			value |= Integer.valueOf(v);
		}
		return value;
	}
	
	/**
	 * 연령 나이대를 알아낸다. (10대, 20대, 30대) 
	 * @param birthDay
	 * @return
	 */
	public static int ageGeneration(String birthDay) {
		try {
			return ageGeneration(yyyy_mm_dd_format.parse(birthDay));
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
	}
	public static int ageGeneration(Date birthDate) {
		Calendar cur = Calendar.getInstance(seoul);
		Calendar cal = Calendar.getInstance(seoul);
		
		cur.setTime(new Date());
		cal.setTime(birthDate);
		
		cur.add(Calendar.YEAR, cal.get(Calendar.YEAR) * -1);
		// 만 나이로 계산한다.
		return (cur.get(Calendar.YEAR) / 10) * 10;
	}	
		
	static Pattern cellPhoneNumberPattern = Pattern.compile("^(01\\d{1})-?(\\d{3,4})-?(\\d{4})");
	/**
	 * 휴대폰번호 변화 (ex> 010-123-4567 = 0101234567, 010.1234.5678 = 01012345678 
	 * @param number
	 * @return
	 * @throws InvalidPhoneNumberException
	 */
	public static String getCellPhoneNumber(String number) 
			throws InvalidPhoneNumberException 
	{
		number = number.trim().replaceAll("[-. ]", "");		
		Matcher matcher = cellPhoneNumberPattern.matcher(number);
		if (!matcher.matches()) {
			throw new InvalidPhoneNumberException("Invalud phone number - "+number);
		}
		int len = number.length();
		String p1 = number.substring(0, 3);
		String p2 = number.substring(3, 3+len-7);
		String p3 = number.substring(len-4, len);
		
		return p1+p2+p3;
	}
	
	static Pattern phoneNumberPattern = Pattern.compile("^(01\\d{1}|02|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");
	/**
	 * 일반전화번호 변환 (ex> 02-123-4567 = 021234567
	 * @param number
	 * @return
	 * @throws InvalidPhoneNumberException
	 */
	public static String getPhoneNumber(String number) 
			throws InvalidPhoneNumberException 
	{
		number = number.trim().replaceAll("[-. ]", "");		
		Matcher matcher = phoneNumberPattern.matcher(number);
		if (!matcher.matches()) {
			throw new InvalidPhoneNumberException("Invalud phone number - "+number);
		}
		int len = number.length();
		String p1 = number.substring(0, 3);
		String p2 = number.substring(3, 3+len-7);
		String p3 = number.substring(len-4, len);
		
		return p1+p2+p3;
	}
	
	/**
	 * Linux의 /dev/urandom을 이용한 랜덤 byte 생성
	 * @param len: 랜덤 byte 길이
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static byte[] getLinuxUrandom(int len) throws FileNotFoundException {
		byte[] bytes = new byte[len];
		File urandomFile = new File("/dev/urandom");
		if (!urandomFile.exists()) {
			throw new FileNotFoundException("File not found - "+urandomFile.getAbsolutePath());
		}
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(urandomFile);
			fs.read(bytes, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fs != null)
				try { fs.close(); } catch (IOException e) { }
		}
		return bytes;
	}
}
