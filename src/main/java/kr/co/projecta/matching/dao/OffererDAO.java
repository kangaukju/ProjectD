package kr.co.projecta.matching.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.Offerer;

@Component("OffererDAO")
public class OffererDAO extends LoggingDao implements DAO<Offerer> {
	Plogger log = Plogger.getLogger(this.getClass());

	// 업주 조회
	public Map selectMap(Map<String, Object> params) {
		return super.selectMap("offerer.select", params, "id");
	}
	
	// 업주 조회
	public List<Offerer> selectList(Map<String, Object> params) {
		return super.selectList("offerer.select", params);
	}

	// 업주 회원 수 조회
	public long selectCount(Map<String, Object> params) {
		return super.selectCount("offerer.selectCount", params);
	}

	// 업주 단일 조회
	public Offerer selectOne(String key, String value) {
		Map<String, Object> params = new HashMap<>();
		params.put(key, value);

		Offerer offerer = (Offerer) super.selectOne("offerer.selectOne", params);
		return offerer;
	}

	// 업체 위치 지도 파일 업데이트
	public boolean updateMapFilename(Map<String, Object> params) {
		Integer result = (Integer) super.update("offerer.updateMapFilename", params);
		return (result == 1) ? true : false;
	}
	
	// 업주 ID, NAME 조회
	public List<Offerer> selectName(Map<String, Object> params) {
		return super.selectList("offerer.selectName", params);
	}

	// 업주 회원가입
	public void join(Map<String, Object> params) {
		super.insert("offerer.join", params);
	}

	// 업주 회원가입
	public void join(Offerer offerer) {
		super.insert("offerer.join", offerer);
	}
}
