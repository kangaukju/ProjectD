package kr.co.projecta.matching.user;

public abstract class BitMatch {
	int maxlen;
	int value;
	int matchableLen;

	abstract public int getMaxLength();
	
	public int intValue() {
		return value;
	}
	
	public String stringValue() {
		return String.valueOf(value);
	}
	
	public BitMatch(int value) {
		this.value = value;		
		maxlen = getMaxLength();		
		matchableLen = 0;
		for (int i=0; i<maxlen; i++) {
			if (((value >> i) & 1) == 1) {
				matchableLen++;
			}
		}
	}
	
	public int howMatch(BitMatch bitmatch) {
		int matched = 0;
		int mix = value & bitmatch.value;
		for (int i=0; i<maxlen; i++) {
			if (((mix >> i) & 1) == 1) {
				matched++;
			}
		}
		return matched;
	}
	
	public double howMatchPercent(BitMatch bitmatch) {
		int matched = howMatch(bitmatch);
		return ((double)matched / (double)matchableLen) * 100;
	}
}
