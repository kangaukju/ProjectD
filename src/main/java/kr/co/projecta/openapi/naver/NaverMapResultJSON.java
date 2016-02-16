package kr.co.projecta.openapi.naver;

import java.util.List;

public class NaverMapResultJSON {
	int total;
	String userquery;
	List<Item> items;
	
	public static class Item {
		String address;
		Addrdetail addrdetail;
		String isRoadAddress;
		Point point;
		
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public Addrdetail getAddrdetail() {
			return addrdetail;
		}
		public void setAddrdetail(Addrdetail addrdetail) {
			this.addrdetail = addrdetail;
		}
		public String getIsRoadAddress() {
			return isRoadAddress;
		}
		public void setIsRoadAddress(String isRoadAddress) {
			this.isRoadAddress = isRoadAddress;
		}
		public Point getPoint() {
			return point;
		}
		public void setPoint(Point point) {
			this.point = point;
		}
	}
	
	public static class Addrdetail {
		String country;
		String sido;
		String sigugun;
		String dongmyun;
		String rest;
		
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getSido() {
			return sido;
		}
		public void setSido(String sido) {
			this.sido = sido;
		}
		public String getSigugun() {
			return sigugun;
		}
		public void setSigugun(String sigugun) {
			this.sigugun = sigugun;
		}
		public String getDongmyun() {
			return dongmyun;
		}
		public void setDongmyun(String dongmyun) {
			this.dongmyun = dongmyun;
		}
		public String getRest() {
			return rest;
		}
		public void setRest(String rest) {
			this.rest = rest;
		}
	}
	
	public static class Point {
		double x;
		double y;
		
		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getUserquery() {
		return userquery;
	}
	public void setUserquery(String userquery) {
		this.userquery = userquery;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
