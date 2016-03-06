package kr.co.projecta.matching.user;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.map.ObjectMapper;

import kr.co.projecta.matching.util.JSONUtils;

public class Requirement extends Matcher {
	String id;
	String offererId;
	String offererName;
	int ageRange;
	int person;
	
	public Requirement() {
	}
	
	public String toJSON() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = JSONUtils.getJSONMap(this.getClass(), this);
		return mapper.writeValueAsString(map);
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
