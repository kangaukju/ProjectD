package kr.co.projecta.matching.user.types;

import kr.co.projecta.matching.util.Strings;

public abstract class BitMatch {
	int value; // bit 값
	int maxStringlen; // bit 값이 string으로 변환할 때 최대 길이
	int matchableLen; // bit 값의 1(on) 갯수 (셋팅된 bit 갯수)

	abstract public int getMaxLength();
	
	public BitMatch() {
		
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMatchableLen() {
		return matchableLen;
	}

	public BitMatch(int value) {
		this.value = value;		
		maxStringlen = getMaxLength();		
		matchableLen = 0;
		for (int i=0; i<maxStringlen; i++) {
			if (((value >> i) & 1) == 1) {
				matchableLen++;
			}
		}
	}
	
	/**
	 * 두 bit의 1(on)값이 일치하는 갯수
	 */
	public int howMatch(BitMatch bitmatch) {
		int matched = 0;
		int mix = value & bitmatch.value;
		for (int i=0; i<maxStringlen; i++) {
			if (((mix >> i) & 1) == 1) {
				matched++;
			}
		}
		return matched;
	}
	
	/**
	 * 두 bit의 1(on)값의 매칭률을 계산
	 */
	public double howMatchPercent(BitMatch bitmatch) {
		int matched = howMatch(bitmatch);
		// 셋팅된 bit와 얼마만큼 매칭이되는지 계산
		return ((double)matched / (double)matchableLen) * 100;
	}
	
	public static void main(String [] args) {
		BitMatch b1 = new BitMatch(30) {
			public int getMaxLength() {
				return 8;
			}
		};
		BitMatch b2 = new BitMatch(8) {
			public int getMaxLength() {
				return 8;
			}
		};
		System.out.println(b1.matchableLen);
		System.out.println(b2.matchableLen);
		System.out.println(b1.howMatch(b2));
		System.out.println(b1.howMatchPercent(b2));
		System.out.println(b2.howMatchPercent(b1));
		System.out.println(Strings.toBitString(b1.value));
		System.out.println(Strings.toBitString(b2.value));
	}
}
