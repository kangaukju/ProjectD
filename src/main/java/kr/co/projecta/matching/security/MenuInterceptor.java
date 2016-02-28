package kr.co.projecta.matching.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MenuInterceptor 
	implements HandlerInterceptor
{
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception 
	{
		return true;
	}

	private void appendAttribute(
			HttpServletRequest request,
			ModelAndView modelAndView,
			String [] attributes)
	{ 
		HttpSession session = request.getSession(true);
		
		for (String attr : attributes) {
			
			String attrValue = request.getParameter(attr);
			if (attrValue != null) {
				if (modelAndView != null) {
					modelAndView.addObject(attr, attrValue);
				}
				if (session != null) {
					// request parameter로 안들어오는 경우에는 세션에 저장
					session.setAttribute(attr, attrValue);
				}
				return;
			}
			
			if (session != null) {
				attrValue = (String) session.getAttribute(attr);
				if (attrValue != null) {
					// 세션에 존재하는 경우 view로 전달한다.
					if (modelAndView != null) {
						modelAndView.addObject(attr, attrValue);
					}
					return;
				}
			}	
		}
	}
	
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView modelAndView)
					throws Exception 
	{
		try {
			if (request == null) {
				return;
			}
			appendAttribute(request, modelAndView, 
					new String[] {"m", "s"});
		}
		catch (IllegalStateException e) {
//			e.printStackTrace();
		}
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
