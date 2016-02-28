package kr.co.projecta.matching.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.json.simple.JSONObject;
import org.mozilla.universalchardet.UniversalDetector;

public class Strings {

	public static int digitStringCompare(String s1, String s2) {
		return digitCompare(digitBinary(s1), digitBinary(s2));
	}
	
	public static int digitCompare(byte[] b1, byte[] b2) {
		byte[] shortByte = (b1.length > b2.length) ? b2 : b1;
		
		for (int i=0; i<shortByte.length; i++) {
			int compare = (int)b1[i] - (int)b2[i];
			if (compare != 0) {
				if (b1.length > b2.length) {
					return 1;
				} else if (b2.length > b1.length) {
					return -1;
				} else {
					return (compare > 0) ? 1 : -1;
				}
			}
		}
		if (b1.length == b2.length) {
			return 0;
		}
		return (shortByte == b1) ? -1 : 1;
	}
	
	public static byte[] digitBinary(String s) {
		if ("".equals(s)) {
			return null;
		}
		
		byte [] sb = new byte[s.length()];
		for (int i=0; i<sb.length; i++) {
			sb[i] = 0;
		}
		for (int i=s.length()-1, k=s.length()-1; i>=0; i--) {
			if (Character.isDigit(s.charAt(i))) {
				sb[k--] = (byte) Character.digit(s.charAt(i), 16);
			}else if (Character.isAlphabetic(s.charAt(i))) {
				sb[k--] = (byte) s.charAt(i);
			}
		}
		/*
		for (byte b : sb) {
			System.out.print(b);
		}
		System.out.println();
		*/
		return sb;
	}
	
	/*
	public static void setJSON(JSONObject json, String key, Object value) {		
		if (value == null) {
			return;
		}
		// Date
		if (Date.class.isAssignableFrom(value.getClass())) {
			json.put(key, Times.formatYYYYMMDDHHMMSS((Date) value));
		}
		// Enum
		else if (Enum.class.isAssignableFrom(value.getClass())) {
			Enum e = (Enum) value;
			json.put(key, e.toString());
		}
		// List
		else if (List.class.isAssignableFrom(value.getClass())) {
			json.put(key, value);
		}
		else {
			json.put(key, value);
		}
	}
	*/
	
	public static void setJSONDateYYYYMMDD(JSONObject json, String key, Date value) {
		if (value != null) {
			json.put(key, Times.formatYYYYMMDD(value));
		}
	}
	
	public static void setJSONDateYYYYMMDDHH(JSONObject json, String key, Date value) {
		if (value != null) {
			json.put(key, Times.formatYYYYMMDDHH(value));
		}
	}
	
	public static String findFileEncoding(File file) throws IOException {
		FileInputStream fis  = null;
		String encoding = null;
		
		try {
			fis = new FileInputStream(file);
			
			UniversalDetector detector = new UniversalDetector(null);
			int bytes;
			byte [] buf = new byte [4096];
			while ((bytes = fis.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, bytes);
			}
			detector.dataEnd();
			
			encoding = detector.getDetectedCharset();
			detector.reset();
			fis.close();
			if (encoding != null) {
				return encoding;
			}
			throw new IOException("No encoding detected.");
		} catch (IOException e) {	
		} finally {
			if (fis != null) try { fis.close(); } catch (IOException e) { }
		}
		return encoding;
	}
	
	public static String systemDefaultEncoding() {
		return new java.io.OutputStreamWriter(System.out).getEncoding();
	}
	
	/**
	 * byte를 hex 문자열로 변환
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();
	    for(byte b: bytes) {
	        sb.append(String.format("%02x", b & 0xff));
	    }
	    return sb.toString();
	}
	
	/**
	 * hex 문자열을 byte로 변환
	 * @param hex
	 * @return
	 */
	public static byte [] toHexByte(String hex) {
		int len = hex.length();
		byte[] bytes = new byte[len / 2];
		for (int i=0; i<len; i+=2) {
			bytes[i/2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + 
					Character.digit(hex.charAt(i+1), 16));
		}
		return bytes;
	}
	
	/**
	 * 문자열 str이 len길이 보다 작은 경우 fillChar문자로 앞(또는 뒤)서 부터 채운 문자열을 생성한다.
	 * @param str: 입력문자
	 * @param len: 문자열 최대길이
	 * @param fillChar: 패딩할 문자
	 * @param frontDirection: 패딩 순서(앞/뒤)
	 * @return
	 */
	public static String fillSpace(String str, int len, char fillChar, boolean frontDirection) {
		int spaceCount = len - str.length();
		if (spaceCount <= 0) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		if (frontDirection) {
			for (int i=0; i<spaceCount; i++) {
				sb.append(fillChar);
			}
		}
		sb.append(str);
		if (!frontDirection) {
			for (int i=0; i<spaceCount; i++) {
				sb.append(fillChar);
			}
		}
		return sb.toString();
	}
	
	public static String toBitString(long bit) {
		return Long.toString(bit, 2);
	}
	
	/**
	 * 바이너리 스트링을 10진수 값으로 변환한다. (ex> 1100 = 12)
	 * @param bitString: 바이너리 스트링
	 * @return
	 */
	public static long bitStringToInt(String bitString) {
		return bitStringToInt(bitString, bitString.length());
	}
	
	/**
	 * 바이너리 스트링을 10진수 값으로 변환한다. (ex> 1100 = 12)
	 * @param bitString: 바이너리 스트링
	 * @param maxBitLength: 바이너리 스트링의 최대길이 (길이가 부족하면 뒤로 패딩이 붙는다. ex> maxBitLength=5, bitString=1100, 11000 = 24
	 * @return
	 */
	public static long bitStringToInt(String bitString, int maxBitLength) {
		long bitVal = 0;
		if (bitString == null) {
			return bitVal;
		}		
		int restLength = maxBitLength - bitString.length();
		if (restLength > 0) {
			bitString = fillSpace(bitString, maxBitLength, '0', false);
		} else if (restLength < 0) {
			bitString = bitString.substring(0, maxBitLength);
		}
		for (int i=0; i<bitString.length(); i++) {
			if (bitString.charAt(bitString.length()-i-1) == '1') {
				bitVal |= 1<< i;
			}
		}
		return bitVal;
	}
	
	public static void main(String [] ars) throws Exception {
		/*
		JSONObject j = new JSONObject();
		Gender gender = new Gender(Gender.MALE);
		MatchStatus matchStatus = new MatchStatus(MatchStatus.COMPLETION);
		MdayBit mdayBit = new MdayBit(MdayBit.MON);
		Nation nation = new Nation(Nation.CHA);
		QtimeBit qtimeBit = new QtimeBit(QtimeBit.Q2);
		Region region = new Region();
		region.setId(1);
		region.setSidoId(11);
		region.setSidoName("서울시");
		region.setSigunguId(22);
		region.setSigunguName("은평구");
		WorkAbility workAbility = new WorkAbility(WorkAbility.KITCHEN);
		WorkMday workMday = new WorkMday(MdayBit.FRI | MdayBit.SAT);
		WorkQtime workQtime = new WorkQtime(QtimeBit.Q2 | QtimeBit.Q3);
		
		
		ObjectMapper mapper = new ObjectMapper();
		JSONObject o = new JSONObject();
		
		HashMap<String, String> map = new HashMap<>();
		map.put("gender", gender.toString());
		
		System.out.println(mapper.writeValueAsString(map));
		*/
		String s1 = "2016-02-27-23.bin.10";
		String s2 = "2016-02-27-23.bin.8";
		String s3 = "2016-02-27-23.bin.3";
		
		System.out.println(digitStringCompare(s1, s2));
		System.out.println(digitStringCompare(s1, s1));
		System.out.println(digitStringCompare(s3, s2));
	}
}
