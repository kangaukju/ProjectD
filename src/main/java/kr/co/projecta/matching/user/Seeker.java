package kr.co.projecta.matching.user;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.map.ObjectMapper;

import kr.co.projecta.matching.util.JSONUtils;

public class Seeker extends Matcher implements Identity {
	private String id;
	private String name;
	private String password;
	private String licenseFile;
	private Date payDate;
	private Date eosDate;
	private long cancelCount; // 구직자 배정취소 횟수
	private byte canceled; // 고용주에 의해 (억울하게) 배정취소 당한 경우
	
	public Seeker() {
	}
	
	public String toJSON() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = JSONUtils.getJSONMap(this.getClass(), this);
		return mapper.writeValueAsString(map);
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
