package kr.co.projecta.matching.security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BaseSessionInterceptor {
	
	List<String> excludePattern = new ArrayList<String>();

	public List<String> getExcludePattern() {
		return excludePattern;
	}

	public void setExcludePattern(List<String> excludePattern) {
		this.excludePattern = excludePattern;
	}
	
	/**
	 * interceptor 처리를 제외할 URI 패턴 검사
	 * @param uri
	 * @return
	 */
	protected boolean excludeURI(String uri) {
		for (String ex : excludePattern) {
			if (Pattern.matches(ex, uri)) {
				return true;
			}
		}
		return false;
	}
}
