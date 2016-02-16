package kr.co.projecta.matching.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kr.co.projecta.matching.util.Strings;

public class Password {
	public static String hash(String password) {
		MessageDigest md;
		byte []bytes = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			bytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Strings.toHexString(bytes);
	}	

	public static void main(String []args) {
		System.out.println(Password.hash("qwe123"));
		System.out.println("1 2.	3-4 5 ".replaceAll("[ .\t-]", ""));
	}
}
