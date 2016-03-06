package kr.co.projecta.matching.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.co.projecta.matching.history.OffererCancelHistory;
import kr.co.projecta.matching.history.SeekerCancelHistory;
import kr.co.projecta.matching.history.SeekerConfirmAfterCancelHistory;
import kr.co.projecta.matching.history.SeekerConfirmHistory;
import kr.co.projecta.matching.history.SeekerNoticeHistory;

@Component("HistoryDAO")
public class HistoryDAO extends LoggingDAO {
	/**
	 * 
	 */
	public void insertOffererCancelHistory(OffererCancelHistory offererCancelHistory) {
		super.insert("history.insertOffererCancelHistory", offererCancelHistory);
	}
	public List<OffererCancelHistory> selectOffererCancelHistory(Map<String, Object> params) {
		return super.selectList("history.selectOffererCancelHistory", params);
	}
	
	/**
	 * 
	 */
	public void insertSeekerCancelHistory(SeekerCancelHistory seekerCancelHistory) {
		super.insert("history.insertSeekerCancelHistory", seekerCancelHistory);
	}
	public List<SeekerCancelHistory> selectSeekerCancelHistory(Map<String, Object> params) {
		return super.selectList("history.selectSeekerCancelHistory", params);
	}
	
	/**
	 * 
	 */
	public void insertSeekerConfirmAfterCancelHistory(
			SeekerConfirmAfterCancelHistory seekerConfirmAfterCancelHistory) {
		super.insert("history.insertSeekerConfirmAfterCancelHistory", 
				seekerConfirmAfterCancelHistory);
	}
	public List<SeekerConfirmAfterCancelHistory> selectSeekerConfirmAfterCancelHistory(
			Map<String, Object> params) {
		return super.selectList("history.selectSeekerConfirmAfterCancelHistory", params);
	}
	
	/**
	 * 
	 */	
	public void insertSeekerConfirmHistory(SeekerConfirmHistory seekerConfirmHistory) {
		super.insert("history.insertSeekerConfirmHistory", seekerConfirmHistory);
	}
	public List<SeekerConfirmHistory> selectSeekerConfirmHistory(Map<String, Object> params) {
		return super.selectList("history.selectSeekerConfirmHistory", params);
	}
	
	/**
	 * 
	 */
	public void insertSeekerNoticeHistory(SeekerNoticeHistory seekerNoticeHistory) {
		super.insert("history.insertSeekerNoticeHistory", seekerNoticeHistory);
	}
	public List<SeekerNoticeHistory> selectSeekerNoticeHistory(Map<String, Object> params) {
		return super.selectList("history.selectSeekerNoticeHistory", params);
	}
}
