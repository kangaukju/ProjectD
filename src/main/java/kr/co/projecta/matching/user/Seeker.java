package kr.co.projecta.matching.user;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.simple.JSONObject;

import kr.co.projecta.matching.util.Strings;

public class Seeker  
	extends Matcher 
	implements Identity {
	
	public boolean equals(Object obj) {
		Seeker seeker = (Seeker) obj;
		return new EqualsBuilder()
				.append(id, seeker.id)
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
		String s1 = "id="+id+
				",이름="+name+
				",성별="+gender.name+
				",시간="+workQtime+
				",요일="+workMday+
				",국적="+nation.name+
				",파트="+workAbility.name;
		String s2 = ",지역=";
		for (Region r : regions) {
			s2 += r.getSigunguName() +" ";
		}
		return s1+s2;
	}
	
	public JSONObject makeJSON() {
		JSONObject o = new JSONObject();
		Strings.setJSON(o, "id", id);
		Strings.setJSON(o, "name", name);
		return o;
	}
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		o.putAll(super.toJSON());
		o.putAll(makeJSON());
		return o;
	}
	
	String id;
	String name;
	String password;
	String licenseFile;
	Date payDate;
	Date eosDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLicenseFile() {
		return licenseFile;
	}
	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getEosDate() {
		return eosDate;
	}
	public void setEosDate(Date eosDate) {
		this.eosDate = eosDate;
	}
	public String getPassword() {
		return null;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
