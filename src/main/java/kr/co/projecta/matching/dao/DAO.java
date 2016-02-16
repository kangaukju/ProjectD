package kr.co.projecta.matching.dao;

import java.util.List;
import java.util.Map;

public interface DAO<T> {
	
	// 조회 결과를 Map으로 반환
	public Map selectMap(Map<String, Object> params);
	
	// 조회 결과를 List로 반환
	public List<T> selectList(Map<String, Object> params);	
	
	// 단일 조회(Primary Key)
	public T selectOne(String key, String value);
	
	// 리스트 조회 갯수
	public long selectCount(Map<String, Object> params);
	
	// 모든 리스트 갯수
	default public long selectCount() { return selectCount(null); }
	
	// 모든 리스트 조회
	default public List<T> selectList() { return selectList(null); }
	
	// 모든 리스트 조회
	default public Map selectMap() { return selectMap(null); }
}
