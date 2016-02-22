package kr.co.projecta.matching.user;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Offerer 
	implements Identity
{
	String id;
	String name;
	String password;
	String offererName;
	String offererNumber;
	String phone;
	String cellPhone;
	String businessType;
	String offererBrief;
	Date payDate;
	Date eosDate;
	Date registerDate;
	int sidoId;
	int sigunguId;
	String postcode;
	String address1;
	String address2;
	String mapFilename;

	public Offerer() {
		
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
}
