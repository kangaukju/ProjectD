package kr.co.projecta.matching.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.Admin;

@Component("AdminDAO")
public class AdminDAO extends LoggingDAO implements DAO<Admin> {
	Plogger log = Plogger.getLogger(this.getClass());
	
	// 관리자 목록 조회
	public Map selectMap(Map<String, Object> params) {
		return super.selectMap("admin.select", params, "id");
	}
	
	// 관리자 목록 조회
	public List<Admin> selectList(Map<String, Object> params) {
		return super.selectList("admin.select", params);
	}
	
	// 관리자 단일 조회
	public Admin selectOne(String key, String value) {
		Map<String, Object> params = new HashMap<>();
		params.put(key, value);
		
		Admin admin = (Admin) super.selectOne("admin.selectOne", params);
		return admin;
	}
	
	// 관리자 목록 조회
	public long selectCount(Map<String, Object> params) {
		return super.selectCount("admin.selectCount", params);
	}
	
	// 관리자 등록
	public void join(Admin admin) {
		super.insert("admin.join", admin);
	}
}
