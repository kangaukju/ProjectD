package kr.co.projecta.matching.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class URLUtils {
	URL urlCtx;
	
	public URLUtils(String url) throws MalformedURLException {
		urlCtx = new URL(url);
	}
	
	public String getHost() {
		return urlCtx.getHost();
	}
	
	public String getPath() {
		return urlCtx.getPath();
	}
	
	public Map<String, Object> getParameters() throws MalformedURLException {		
		Map<String, Object> map = new HashMap<>();
		String key, val;
		String tok;
		int idx;
		String params = urlCtx.getQuery();
		StringTokenizer st = new StringTokenizer(params, "&");		
		while (st.hasMoreTokens()) {
			tok = st.nextToken();
			idx = tok.indexOf("=");
			if (idx == -1) {
				throw new MalformedURLException("Malformed parameters: "+tok);
			}
			
			key = tok.substring(0, idx);
			val = tok.substring(idx+1, tok.length());
			
			if (map.containsKey(key)) {
				Object exist = map.get(key);
				if (exist instanceof String) {
					List<String> list = new ArrayList<String>();
					list.add((String) exist);
					list.add(val);
					map.remove(key);
					map.put(key, list);
				}
				else if (exist instanceof List) {
					List<String> list = (List<String>) exist;
					list.add(val);
				}
			}
			else {
				map.put(key, val);
			}
		}		
		return map;
	}
	
	public static void main(String [] args) throws MalformedURLException {
		String url = "http://localhost/admin/kaka.do?m=m8&id=1&region=G1&region=G2&region=G3&name=kaka";
		URLUtils u = new URLUtils(url);
		
		System.out.println(u.getHost());
		
		System.out.println(u.getPath());
		
		Map<String, Object> parmas = u.getParameters();		
		System.out.println(parmas);
	}
	
}
