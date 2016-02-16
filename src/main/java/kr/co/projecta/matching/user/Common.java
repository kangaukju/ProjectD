package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.projecta.matching.context.ContextSchemable;

public class Common implements ContextSchemable {

	Map<String, Object> utilMap = new HashMap<>();	
	static int [] months = intFill(1, 12);
	static int [] days = intFill(1, 31);	
	static int [] years = intFromTo(1940, 2010, false);

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
	
	public static int[] intFill(int start, int size) {
		int [] arr = new int[size];
		for (int i=0; i<arr.length; i++) {
			arr[i] = start++;
		}
		return arr;
	}
	
	public Common() {
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
		return "Utils";
	}
	
	public String getOriginalName() {
		return getName();
	}
}
