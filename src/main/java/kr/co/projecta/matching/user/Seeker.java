package kr.co.projecta.matching.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import kr.co.projecta.matching.util.Times;

public class Seeker  
	extends Matcher 
	implements Identity
{	
	String id;
	String name;
	String password;
	String licenseFile;
	Date payDate;
	Date eosDate;	
	
	public Seeker() {
		
	}
	
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
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	public Map<String, Object> buildJSON() {
		Map<String, Object> map = new HashMap<>();
		if (id != null)
			map.put("id", id);
		if (name != null)
			map.put("name", name);
		if (payDate != null)
			map.put("payDate", Times.formatYYYYMMDD(payDate));
		if (eosDate != null)
			map.put("eosDate", Times.formatYYYYMMDD(eosDate));
		return map;
	}

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
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
