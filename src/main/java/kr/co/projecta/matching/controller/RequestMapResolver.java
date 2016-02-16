package kr.co.projecta.matching.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestMapResolver implements HandlerMethodArgumentResolver {

	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer container, 
			NativeWebRequest webRequest,
			WebDataBinderFactory factory)  throws Exception 
	{
		RequestMap requestMap = new RequestMap();
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Enumeration<?> enumeration = request.getParameterNames();
		
		String key = null;
		String[] values = null;
        while(enumeration.hasMoreElements()){
            key = (String) enumeration.nextElement();
            values = request.getParameterValues(key);
            if(values != null){
            	if (values.length == 1) {
            		if (!"".equals(values[0])) {
            			requestMap.put(key, values[0]);
            		}
            	} else {
            		for (String v : values) {
            			List<String> list = new ArrayList<>();
            			if (!"".equals(v)) {
            				list.add(v);
            			}
            			if (list.size() > 1) {
            				requestMap.put(key, list.toArray());
            			}
            		}
            	}
            }
        }
        return requestMap;
	}

	public boolean supportsParameter(MethodParameter parameter) {
		return RequestMap.class.isAssignableFrom(parameter.getParameterType());
	}
}
