package kr.co.projecta.matching.user;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class Region {
	private int id;
	
	private int sidoId;
	private String sidoName;
	
	private int sigunguId;
	private String sigunguName;
	
	public Region() { }
	public Region(Region r) {
		this.id = r.id;
		this.sidoId = r.sidoId;
		this.sidoName = r.sidoName;
		this.sigunguId = r.sigunguId;
		this.sigunguName = r.sigunguName;
	}
	public Region(int id) {
		this.id = id;
		/*
		if (id == 0) {
			this.sidoName = this.sigunguName = "전지역";
		}
		*/
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		/*
		if (id == 0) {
			this.sidoName = this.sigunguName = "전지역";
		}
		*/
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
