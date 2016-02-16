package kr.co.projecta.matching.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.controller.BaseController;

public class AdminSessionInterceptor
	extends BaseSessionInterceptor
	implements HandlerInterceptor 
{	
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception 
	{
		if (excludeURI(request.getRequestURI().toString())) {
			return true;
		}
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			BaseController.goHome(response);
			return false;
		}
		
		boolean result;
		boolean bad2 = (session.getAttribute("admin") == null);
		
		result = !(bad2);
		if (!result) {
			BaseController.goHome(response);
		}
		return result;
	}

	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView modelAndView) 
					throws Exception 
	{
	}

	public void afterCompletion(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			Exception ex)
			throws Exception 
	{	
	}

}
