package kr.co.projecta.matching.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.util.Parameters;

@Component("RequirementDAO")
public class RequirementDAO extends LoggingDao implements DAO<Requirement> {
	Plogger log = Plogger.getLogger(this.getClass());

	// 매칭 상태를 갱신한다
	public boolean updateMatchStatus(String id, int matchStatus) {
		return (int)super.update("requirement.updateMatchStatus", 
				Parameters.makeMap(
						"id", id,
						"matchStatus",matchStatus)) == 1;
	}
	
	// 배정요청목록 조회
	public Map selectMap(Map<String, Object> params) {
		return super.selectMap("requirement.select", params, "id");
	}

	// 배정요청목록 조회
	public List<Requirement> selectList(Map<String, Object> params) {
		return super.selectList("requirement.select", params);
	}

	// 미 배정요청목록 조회
	public List<Requirement> selectNotAssignComplete() {
		return super.selectList("requirement.selectNotAssignComplete");
	}

	// 배정요청목록 수 조회
	public long selectCount(Map<String, Object> params) {
		return super.selectCount("requirement.selectCount", params);
	}

	// 배정요청 단일 조회
	public Requirement selectOne(String key, String value) {
		Map<String, Object> params = new HashMap<>();
		params.put(key, value);

		Requirement requirement = (Requirement) super.selectOne("requirement.selectOne", params);
		return requirement;
	}

	// 배정요청하기
	public void join(Requirement requirement) {
		super.insert("requirement.join", requirement);
	}

	// 배정요청하기
	public void join(Map<String, Object> params) {
		super.insert("requirement.join", params);
	}
}
