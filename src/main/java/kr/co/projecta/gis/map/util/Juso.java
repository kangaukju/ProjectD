package kr.co.projecta.gis.map.util;

import java.sql.PreparedStatement;

public interface Juso {
	public String toExcelHeader(char delim);
	public String toExcel(char delim);
	public String insertSQL(String table);
	public void insertPreparedStatement(PreparedStatement ps) throws Exception;
	public String [] parseCode(String code);
	public String getSidoName();
}
