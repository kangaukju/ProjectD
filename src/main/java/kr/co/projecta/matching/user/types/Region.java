package kr.co.projecta.matching.user.types;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.map.ObjectMapper;

public class Region 
	implements Serializable
{
	private static final long serialVersionUID = 2131826259603189167L;

	private int id;
	
	private int sidoId; // 시/도 번호
	private String sidoName;	 // 시/도 이름
	private int sigunguId; // 시/군/구 번호
	private String sigunguName; // 시/군/구 이름
	
	public Region() {
		
	}
	
	public Region(Region r) {
		this.id = r.id;
		this.sidoId = r.sidoId;
		this.sidoName = r.sidoName;
		this.sigunguId = r.sigunguId;
		this.sigunguName = r.sigunguName;
	}
	
	public Region(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSidoId() {
		return sidoId;
	}
	
	public void setSidoId(int sidoId) {
		this.sidoId = sidoId;
	}
	
	public String getSidoName() {
		return sidoName;
	}
	
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	
	public int getSigunguId() {
		return sigunguId;
	}
	
	public void setSigunguId(int sigunguId) {
		this.sigunguId = sigunguId;
	}
	
	public String getSigunguName() {
		return sigunguName;
	}
	
	public void setSigunguName(String sigunguName) {
		this.sigunguName = sigunguName;
	}
	
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
