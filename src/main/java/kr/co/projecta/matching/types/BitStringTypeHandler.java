package kr.co.projecta.matching.types;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BitStringTypeHandler extends BaseTypeHandler<BitString> {

	@Override
	public BitString getNullableResult(ResultSet rs, String s) throws SQLException {
		return BitString.valueOf(rs.getString(s));
	}

	@Override
	public BitString getNullableResult(ResultSet rs, int i) throws SQLException {
		return BitString.valueOf(rs.getString(i));
	}

	@Override
	public BitString getNullableResult(CallableStatement cs, int i) throws SQLException {
		return BitString.valueOf(cs.getString(i));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, BitString bitString, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, bitString.toString());
	}

}
