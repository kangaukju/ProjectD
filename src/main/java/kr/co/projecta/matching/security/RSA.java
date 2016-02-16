package kr.co.projecta.matching.security;

import java.io.FileNotFoundException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import kr.co.projecta.matching.exception.RSAException;
import kr.co.projecta.matching.util.Numbers;
import kr.co.projecta.matching.util.Strings;

public class RSA {
	
	PublicKey publicKey;
	PrivateKey privateKey;
	String publicKeyModulus;
	String publicKeyExponent;
	
	public RSA() {
		KeyPairGenerator keyPairGenerator = null;
		KeyPair keyPair = null;
		KeyFactory keyFactory = null;
		RSAPublicKeySpec rsaPublicKeySpec = null;
		
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			try {
				keyPairGenerator.initialize(1024,  new SecureRandom(Numbers.getLinuxUrandom(1024)));
			} catch (FileNotFoundException e) {
				keyPairGenerator.initialize(1024);
			}
			keyPair = keyPairGenerator.generateKeyPair();
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}		
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
		
		try {
			rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			this.publicKeyModulus = rsaPublicKeySpec.getModulus().toString(16);
			this.publicKeyExponent = rsaPublicKeySpec.getPublicExponent().toString(16);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public String getPublicKeyModulus() {
		return publicKeyModulus;
	}

	public String getPublicKeyExponent() {
		return publicKeyExponent;
	}
	
	public static String decrypt(PrivateKey privateKey, String dirty) 
			throws RSAException 
	{
		String clean = "";
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			byte[] dirtyBytes = Strings.toHexByte(dirty);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] cleanBytes = cipher.doFinal(dirtyBytes);
			clean = new String(cleanBytes, "utf-8");
		} catch (Exception e) {
			throw new RSAException("Failed to RSA decrypt - "+e);
		}
		return clean;
	}

	public static void main(String [] args) {
		RSA rsa = new RSA();
		System.out.println(rsa.getPublicKeyExponent());
		System.out.println(rsa.getPublicKeyModulus());
		
	}

}