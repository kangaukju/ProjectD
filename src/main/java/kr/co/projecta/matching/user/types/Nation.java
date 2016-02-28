package kr.co.projecta.matching.user.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kr.co.projecta.matching.context.ContextSchemable;

public class Nation 
	implements ContextSchemable, Serializable
{
	private static final long serialVersionUID = -8412074443345824754L;
	
	public static final int ETC = 2;
	public static final int CHA = 1;
	public static final int KOR = 0;
	
	int nation;

	public Nation() {
		
	}
	
	public Nation(int nation) {
		this.nation = nation;
	}
	
	public int getNation() {
		return nation;
	}

	public void setNation(int nation) {
		this.nation = nation;
	}

	public static Nation valueOf(int nation) {
		switch (nation) {
		case ETC: return new Nation(ETC);
		case CHA: return new Nation(CHA);
		case KOR: return new Nation(KOR);
		default: throw new AssertionError("Unknown value: "+nation);
		}
	}
	
	public String toString() {
		switch (nation) {
		case ETC: return "그외";
		case CHA: return "중국";
		case KOR: return "한국";
		default: throw new AssertionError("Unknown value: "+nation);
		}
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<Object>();
		list.add(new Nation(ETC));
		list.add(new Nation(CHA));
		list.add(new Nation(KOR));
		return list;
	}
	
	public static Nation valueOf(String value) {
		if ("그외".equals(value)) {
			return new Nation(ETC);
		}
		if ("중국".equals(value)) {
			return new Nation(CHA);
		}
		if ("한국".equals(value)) {
			return new Nation(KOR);
		}
		throw new AssertionError("Unknown value: "+value);
	}
}