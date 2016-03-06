package kr.co.projecta.matching.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.Assignment;
import kr.co.projecta.matching.util.Parameters;

@Component("AssignmentDAO")
public class AssignmentDAO 
	extends LoggingDAO 
	implements DAO<Assignment>, Serializable 
{	
	transient Plogger log = Plogger.getLogger(this.getClass());
	
	// 배정가능목록 조회
	public List<Assignment> selectList(String requirementId) {
		return super.selectList("assignment.select", 
				Parameters.makeMap("requirementId", requirementId));
	}
	public List<Assignment> selectList(Map<String, Object> params) {
		return super.selectList("assignment.select", params);
	}
	
	// 임시배정하기
	public void insert(String requirementId, String seekerId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("requirementId", requirementId);
		params.put("seekerId", seekerId);
		super.insert("assignment.insert",
				Parameters.makeMap(
						"requirementId", requirementId, 
						"seekerId", seekerId));
	}
	
	// 배정확정 업데이트
	public boolean setAssignConfirm(String requirementId, String seekerId) {
		return this.setAssign(requirementId, seekerId, true);
	}
	
	// 배정취소 (배정에서 삭제한다)
	public boolean setAssignCancel(String requirementId, String seekerId) {
		return (int)super.delete("assignment.deleteAssign", 
				Parameters.makeMap(
						"requirementId", requirementId,
						"seekerId", seekerId)) == 1;
	}
	
	public boolean setAssignCancel(String requirementId) {
		return (int)super.delete("assignment.deleteAssign", 
				Parameters.makeMap(
						"requirementId", requirementId)) > 0;
	}
	
	private boolean setAssign(String requirementId, String seekerId, boolean confirm) {
		return (int)super.update("assignment.updateConfirm", 
				Parameters.makeMap(
						"requirementId", requirementId,
						"seekerId", seekerId,
						"confirm", confirm ? 1:0)) == 1;
	}
	
	// 배정확정 구직자 수
	public long getAssignConfirm(String requirementId) {
		return this.getAssign(requirementId, true);
	}
	
	// 임시배정 구직자 수
	public long setAssignCandidate(String requirementId) {
		return this.getAssign(requirementId, false);
	}
	
	private long getAssign(String requirementId, boolean confirm) {
		return this.selectCount(
				Parameters.makeMap(
						"requirementId", requirementId,
						"confirm", confirm ? 1:0));
	}
	
	public long selectCount(Map<String, Object> params) {
		return super.selectCount("assignment.selectCount", params);
	}
	
	// 배정에 할당된 구직자 수
	public long selectCount(String requirementId) {
		return super.selectCount("assignment.selectCount", 
				Parameters.makeMap(
						"requirementId", requirementId));
	}

	@Deprecated
	public Map selectMap(Map<String, Object> params) {
		return null;
	}

	@Deprecated
	public Assignment selectOne(String key, String value) {
		return null;
	}
}
