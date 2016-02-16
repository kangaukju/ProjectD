package kr.co.projecta.matching.types;

import java.io.Serializable;

public final class BitString implements Serializable {
	
	private long value;
	private int maxLength;
	
	public BitString() {
		this.value = 0;
		this.maxLength = 0;
	}
	
	public BitString(String bitString) {
		this(bitString, 0);
	}
	
	public BitString(String bitString, int max) {
		this.maxLength = max;
		
		if (bitString == null) {
			value = 0;
			return;
		}
		if (maxLength > 0) {
			int restLength = this.maxLength - bitString.length();
			if (restLength > 0) {
				// padding by '0'
				StringBuffer sb = new StringBuffer();
				for (int i=0; i<restLength; i++)
					sb.append('0');
				sb.append(bitString);
				bitString = sb.toString();
			} else if (restLength < 0) {
				// truncate bitString
				bitString = bitString.substring(0, this.maxLength);
			}
		}
		char bit;
		for (int i=0; i<bitString.length(); i++) {
			bit = bitString.charAt(bitString.length()-i-1);			
			if (bit == '1') {				
				this.value |= 1 << i;
			} else if (bit != 0) {
				throw new IllegalArgumentException("'"+bitString+"' is not bit string.");
			}
		} 
	}
	
	public BitString add(long bitVal) {
		this.value |= bitVal;
		return this;
	}
	
	public BitString clear(long value) {
		this.value &= ~value;
		return this;
	}

	public String toString() {
		return Long.toBinaryString(this.value);
	}
	
	public static BitString valueOf(String value) {
		return new BitString(value);
	}
	
	public long longValue() {
		return this.value;
	}
	
	public static void main(String [] args) {
		BitString bs = new BitString("10011012");
		System.out.println(bs.longValue());
		System.out.println(bs);
	}
}
