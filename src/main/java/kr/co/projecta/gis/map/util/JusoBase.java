package kr.co.projecta.gis.map.util;

import java.sql.PreparedStatement;
import java.util.StringTokenizer;

public class JusoBase implements Juso {
	public static final int SIDO_CODE_LENGTH = 2;
	public static final int SIDOGUNGU_CODE_LENGTH = 3;
	String sidoId; // length:2
	String sidoName;
	String sigunguId; // length:3
	String sigunguName;
	
	
	@Override
	public String toExcelHeader(char delim) {
		return new StringBuffer()
				.append("sidoId").append(delim)
				.append("sidoName").append(delim)
				.append("sigunguId").append(delim)
				.append("sigunguName").toString();
	}
	@Override
	public String toExcel(char delim) {
		return new StringBuffer()
				.append(sidoId).append(delim)
				.append(sidoName).append(delim)
				.append(sigunguId).append(delim)
				.append(sigunguName).toString();
	}
	public static String createTableSQL(String table) {
		return new StringBuffer()
				.append("CREATE TABLE " + table + " (")
				.append("id int unsigned NOT NULL AUTO_INCREMENT, ")
				.append("sidoId int(11) NOT NULL, ")
				.append("sidoName varchar(45) NOT NULL, ")
				.append("sigunguId int(11) NOT NULL, ")
				.append("sigunguName varchar(45), ")
				.append("PRIMARY KEY (sidoId,sigunguId), ")
				.append("UNIQUE KEY id_UNIQUE (id)")
				.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8").toString();
	}
	@Override
	public String insertSQL(String table) {
		return new StringBuffer()
				.append("INSERT INTO "+table)
				.append("(sidoId,sidoName,sigunguId,sigunguName) ")
				.append("VALUES ")
				.append("(?,?,?,?)").toString();
	}
	@Override
	public void insertPreparedStatement(PreparedStatement ps) throws Exception {
		ps.setInt(1, Integer.valueOf(sidoId));
		ps.setString(2, sidoName);
		ps.setInt(3, Integer.valueOf(sigunguId));
		ps.setString(4, sigunguName);
	}
	@Override
	public String[] parseCode(String code) {
		String [] sep = {
			code.substring(
					0, SIDO_CODE_LENGTH),
			code.substring(
					SIDO_CODE_LENGTH, SIDO_CODE_LENGTH+SIDOGUNGU_CODE_LENGTH)};
		return sep;
	}
	public static Juso jusoFactory(String line, char delim) {
		String code;
		int index = 0;
		JusoBase juso = new JusoBase();
		
		StringTokenizer toker = new StringTokenizer(line, ",");
		while (toker.hasMoreElements()) {
			switch (index) {
			case 0: // code
				code = toker.nextToken();
				juso.setSidoId(
						code.substring(
								0, 
								SIDO_CODE_LENGTH));
				juso.setSigunguId(
						code.substring(
								SIDO_CODE_LENGTH, 
								SIDO_CODE_LENGTH+SIDOGUNGU_CODE_LENGTH));
				break;
			case 1: // sido
				juso.setSidoName(toker.nextToken());
				break;
			case 2: // sigungu
				juso.setSigunguName(toker.nextToken());
				break;
			default: // ignore
			}
			index++;
		}
		return juso;
	}
	
	
	
	
	public String getSidoId() {
		return sidoId;
	}
	public void setSidoId(String sidoId) {
		this.sidoId = sidoId;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getSigunguId() {
		return sigunguId;
	}
	public void setSigunguId(String sigunguId) {
		this.sigunguId = sigunguId;
	}
	public String getSigunguName() {
		return sigunguName;
	}
	public void setSigunguName(String sigunguName) {
		this.sigunguName = sigunguName;
	}	
}
