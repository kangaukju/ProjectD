package kr.co.projecta.matching.util;

import java.util.HashMap;
import java.util.Map;

import kr.co.projecta.matching.exception.ParametersException;

public class Parameters {
	/**
	 * parameter map을 생성해주는 유틸
	 * @param args
	 * @return
	 * @throws ParametersException
	 */
	public static Map<String, Object> makeMap(Object ...args) {
		Map<String, Object> map = new HashMap<>();
		if (args.length % 2 != 0) {
			throw new RuntimeException("mismatch args count");
		}
		String key;
		Object value;
		for (int i=0; i<args.length; i=i+2) {
			key = (String) args[i];
			value = args[i+1];
			map.put(key, value);
		}
		return map;
	}
}
