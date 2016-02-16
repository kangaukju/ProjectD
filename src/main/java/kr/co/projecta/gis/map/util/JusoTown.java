package kr.co.projecta.gis.map.util;

import java.sql.PreparedStatement;
import java.util.StringTokenizer;

public class JusoTown extends JusoBase {
	public static final int TOWN_CODE_LENGTH = 2;
	protected String townId; // length: 2
	protected String townName;
	
	
	@Override
	public String toExcelHeader(char delim) {
		return new StringBuffer()
				.append(super.toExcelHeader(delim)).append(delim)
				.append("townId").append(delim)
				.append("townName").toString();
	}
	@Override
	public String toExcel(char delim) {
		return new StringBuffer()
				.append(super.toExcel(delim)).append(delim)
				.append(townId).append(delim)
				.append(townName).toString();
	}
	public static String createTableSQL(String table) {
		return new StringBuffer()
				.append("CREATE TABLE " + table + " (")
				.append("id int unsigned NOT NULL AUTO_INCREMENT, ")
				.append("sidoId int(11) NOT NULL, ")
				.append("sidoName varchar(45) NOT NULL, ")
				.append("sigunguId int(11) NOT NULL, ")
				.append("sigunguName varchar(45), ")
				.append("townId int(11) NOT NULL, ")
				.append("townName varchar(45), ")
				.append("PRIMARY KEY (sidoId,sigunguId,townId), ")
				.append("UNIQUE KEY id_UNIQUE (id)")
				.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8").toString();
	}
	@Override
	public String insertSQL(String table) {
		return new StringBuffer()
				.append("INSERT INTO " + table)
				.append("(sidoId,sidoName,sigunguId,sigunguName,townId,townName) ")
				.append("VALUES ")
				.append("(?,?,?,?,?,?)").toString();
	}
	@Override
	public void insertPreparedStatement(PreparedStatement ps) throws Exception {
		ps.setInt(1, Integer.valueOf(sidoId));
		ps.setString(2, sidoName);
		ps.setInt(3, Integer.valueOf(sigunguId));
		ps.setString(4, sigunguName);
		ps.setInt(5, Integer.valueOf(townId));
		ps.setString(6, townName);
	}
	
	public static Juso jusoFactory(String line, char delim) {
		String code;
		int index = 0;
		JusoTown juso = new JusoTown();
		
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
				juso.setTownId(
						code.substring(
								SIDO_CODE_LENGTH+SIDOGUNGU_CODE_LENGTH,
								SIDO_CODE_LENGTH+SIDOGUNGU_CODE_LENGTH+TOWN_CODE_LENGTH));
				break;
			case 1: // sido
				juso.setSidoName(toker.nextToken());
				break;
			case 2: // sigungu
				juso.setSigunguName(toker.nextToken());
				break;
			case 3: // town
				juso.setTownName(toker.nextToken());
				break;
			default: // ignore
			}
			index++;
		}
		return juso;
	}
	
	
	public String getTownId() {
		return townId;
	}
	public void setTownId(String townId) {
		this.townId = townId;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}	
}
