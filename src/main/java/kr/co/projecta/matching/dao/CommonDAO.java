package kr.co.projecta.matching.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.types.Region;

@Component("CommonDAO")
public class CommonDAO extends LoggingDao implements DAO<Region> {
	Plogger log = Plogger.getLogger(this.getClass());
	
	// 주소 조회
	public Map selectMap(Map<String, Object> params) {
		return super.selectMap("juso.select", params, "id");
	}
	
	// 주소 조회
	public List<Region> selectList(Map<String, Object> params) {
		return super.selectList("juso.select", params);
	}

	// 주소 단일 조회
	public Region selectOne(String key, String value) {
		Map<String, Object> params = new HashMap<>();
		params.put(key, value);
		
		Region region = (Region) super.selectOne("juso.selectOne", params);
		return region;
	}
	
	// 주소 갯수
	public long selectCount(Map<String, Object> params) {
		return super.selectCount("juso.selectCount", params);
	}
	
	// 서울시 주소 리스트
	public List<Region> selectListSeoul() {
		Map<String, Object> params = new HashMap<>();
		params.put("sidoId", 11);
		return this.selectList(params);
	}
	
	// 서울시 주소 맵
	public Map selectMapSeoul() {
		Map<String, Object> params = new HashMap<>();
		params.put("sidoId", 11);
		return this.selectMap(params);
	}
}
