package kr.co.projecta.matching.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.Offerer;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.util.Parameters;

@Component("RequirementDAO")
public class RequirementDAO 
	extends LoggingDAO 
	implements DAO<Requirement>, Serializable
{
	private static final long serialVersionUID = 2930895453767351336L;
	
	transient Plogger log = Plogger.getLogger(this.getClass());

	// 매칭 상태를 갱신한다
	public boolean updateMatchStatus(String requirementId, int matchStatus) {
		return (int)super.update("requirement.updateMatchStatus", 
				Parameters.makeMap(
						"id", requirementId,
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
	public Requirement selectOne(String requirementId) {
		return this.selectOne("id", requirementId);
	}
	
	//배정번호로 고용주 정보 조회
	public Offerer selectOfferer(String requirementId) {
		return (Offerer) super.selectOne("requirement.selectOfferer",
				Parameters.makeMap("id", requirementId));
	}
	
	// 배정요청 단일 조회
	public Requirement selectOne(String key, String value) {
		Requirement requirement = 
				(Requirement) super.selectOne("requirement.selectOne", 
						Parameters.makeMap(key, value));
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
