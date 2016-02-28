package kr.co.projecta.matching.user;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Admin 
	implements Identity, Serializable 
{
	private static final long serialVersionUID = 7369278996015677890L;
	
	String id; // 관리자 아이디
	String name; // 관리자 이름
	String password; // 관리자 비밀번호
	
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
	public String toString() {
		return ToStringBuilder.reflectionToString(
			this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
}
