package kr.co.projecta.matching.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.simple.JSONObject;

import kr.co.projecta.matching.util.Strings;

public class Requirement extends Matcher {
	
	public Requirement() {}
	
	public boolean equals(Object obj) {
		Requirement requirement = (Requirement) obj;
		return new EqualsBuilder()
				.append(id, requirement.id)
				.isEquals();
	}
	
	public int hashCode() {
		return Integer.valueOf(id);
	}
	
	public String toString() {
		/*
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.NO_CLASS_NAME_STYLE);
		*/
		return "id="+id+
				",업주="+offererName+"("+offererId+")"+
				",연령="+ageRange+
				",국적="+nation.name+
				",성별="+gender.name+
				",파트="+workAbility.name;
				//+",주소="+location.getSigunguName();
	}
	
	protected JSONObject makeJSON() {
		JSONObject o = new JSONObject();
		Strings.setJSON(o, "id", id);
		Strings.setJSON(o, "offererId", offererId);
		Strings.setJSON(o, "offererName", offererName);
		Strings.setJSON(o, "ageRange", ageRange);
		return o;
	}
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		o.putAll(super.toJSON());
		o.putAll(makeJSON());
		return o;
	}

	String id;
	String offererId;
	String offererName;
	int ageRange;
	
	public String getOffererName() {
		return offererName;
	}
	public void setOffererName(String offererName) {
		this.offererName = offererName;
	}	
	public int getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(int ageRange) {
		this.ageRange = ageRange;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOffererId() {
		return offererId;
	}
	public void setOffererId(String offererId) {
		this.offererId = offererId;
	}
}
