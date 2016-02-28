package kr.co.projecta.matching.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.projecta.matching.log.Plogger;

public class LoggingDao {
	
	transient Plogger log = Plogger.getLogger(this.getClass());
	
	@Autowired
	SqlSessionTemplate sqlSession;

	protected void printQueryId(String queryId) {
		if (log.isDebug()) {
			log.d("\t QueryId  \t:  " + queryId);
		}
	}

	protected Object insert(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}

	protected Object update(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}

	protected Object delete(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}

	protected Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}

	protected long selectCount(String queryId, Object params) {
		printQueryId(queryId);
		Long count = sqlSession.selectOne(queryId, params);
		return (count == null) ? 0 : count;
	}
	
	protected Object selectOne(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}

	protected Map selectMap(String queryId, String key) {
		printQueryId(queryId);
		return sqlSession.selectMap(queryId, key);
	}
	
	protected Map selectMap(String queryId, Object params, String key) {
		printQueryId(queryId);
		return sqlSession.selectMap(queryId, params, key);
	}
	
	protected List selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}

	protected List selectList(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
}
