package kr.co.projecta.matching.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Requirement extends Matcher {
	
	String id;
	String offererId;
	String offererName;
	int ageRange;
	int person;
	
	public Requirement() {
		
	}
	
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
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	public Map<String, Object> buildJSON() {
		Map<String, Object> map = new HashMap<>();		
		if (id != null)
			map.put("id", id);
		if (offererId != null)
			map.put("offererId", offererId);
		if (offererName != null)
			map.put("offererName", offererName);
		map.put("ageRange", ageRange);
		map.put("person", person);
		return map;
	}
	
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
	public int getPerson() {
		return person;
	}
	public void setPerson(int person) {
		this.person = person;
	}
}
