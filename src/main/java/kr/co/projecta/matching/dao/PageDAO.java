package kr.co.projecta.matching.dao;

import java.util.List;
import java.util.Map;

public interface PageDAO<T> {
	public long getCount(Map<String, Object> params);
	public List<T> getList(Map<String, Object> params);
}
