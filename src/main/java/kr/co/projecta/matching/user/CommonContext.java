package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.projecta.matching.context.ContextSchemable;
import kr.co.projecta.matching.util.Times;

/**
 * web jstl에서 숫자, 문자, 집합 범위 데이터를 사용하기 위한 저장 디비로 사용
 */
public class CommonContext 
	implements ContextSchemable
{

	Map<String, Object> utilMap = new HashMap<>();
	// 1월 ~ 12월
	static int [] months = intFill(1, 12);
	// 1일 ~ 31일
	static int [] days = intFill(1, 31);
	// 1940년 ~ 2010년
	static int LAST_YEAR = Times.nowYear() - 10;
	static int YEAR_SIZE = 50;
	static int [] years = intFromTo(LAST_YEAR-50, LAST_YEAR, false);

	/**
	 * 숫자배열 생성
	 * @param start
	 * @param end
	 * @param asc: 오름차순?
	 * @return
	 */
	public static int[] intFromTo(int start, int end, boolean asc) {
		int [] arr = new int[end-start+1];
		for (int i=0; i<arr.length; i++) {
			if (asc) {
				arr[i] = start++;
			} else {
				arr[i] = end--;
			}
		}
		return arr;
	}
	
	/**
	 * 숫자배열 생성
	 * @param start
	 * @param size
	 * @return
	 */
	public static int[] intFill(int start, int size) {
		int [] arr = new int[size];
		for (int i=0; i<arr.length; i++) {
			arr[i] = start++;
		}
		return arr;
	}
	
	public CommonContext() {
		utilMap.put("months", months);
		utilMap.put("days", days);
		utilMap.put("years", years);
	}
	
	public List<Object> getContextSchemaData() {
		List<Object> list = new ArrayList<>();
		list.add(utilMap);
		return list;
	}

	public String getName() {
		return "commonContext";
	}
}
