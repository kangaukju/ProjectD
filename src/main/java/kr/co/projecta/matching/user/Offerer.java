package kr.co.projecta.matching.user;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.map.ObjectMapper;

import kr.co.projecta.matching.util.JSONUtils;

public class Offerer implements Identity {
	
	private String id;
	private String name;
	private String password;
	private String offererName;
	private String offererNumber;
	private String phone;
	private String cellPhone;
	private String businessType;
	private String offererBrief;
	private Date payDate;
	private Date eosDate;
	private Date registerDate;
	private int sidoId;
	private int sigunguId;
	private String postcode;
	private String address1;
	private String address2;
	private String mapFilename;
	private int kindOfBusiness;
	private long cancelCount;

	public Offerer() {		
	}
	
	public String toJSON() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = JSONUtils.getJSONMap(this.getClass(), this);
		return mapper.writeValueAsString(map);
	}
	
	public boolean equals(Object obj) {
		Offerer offerer = (Offerer) obj;
		return new EqualsBuilder()
				.append(id, offerer.id)
				.isEquals();
	}
	
	public int hashCode() {
		return Integer.valueOf(id);
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.NO_CLASS_NAME_STYLE);
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
	public String getOffererBrief() {
		return offererBrief;
	}
	public void setOffererBrief(String offererBrief) {
		this.offererBrief = offererBrief;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getOffererNumber() {
		return offererNumber;
	}
	public void setOffererNumber(String offererNumber) {
		this.offererNumber = offererNumber;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getOffererName() {
		return offererName;
	}
	public void setOffererName(String offererName) {
		this.offererName = offererName;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public int getSidoId() {
		return sidoId;
	}
	public void setSidoId(int sidoId) {
		this.sidoId = sidoId;
	}
	public int getSigunguId() {
		return sigunguId;
	}
	public void setSigunguId(int sigunguId) {
		this.sigunguId = sigunguId;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getMapFilename() {
		return mapFilename;
	}
	public void setMapFilename(String mapFilename) {
		this.mapFilename = mapFilename;
	}
	public int getKindOfBusiness() {
		return kindOfBusiness;
	}
	public void setKindOfBusiness(int kindOfBusiness) {
		this.kindOfBusiness = kindOfBusiness;
	}
}
