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
			
			HttpSession session = request.getSession(true);
			String menu = request.getParameter("m");
			if (menu != null) {
				if (modelAndView != null) {
					modelAndView.addObject("m", menu);
				}
				if (session != null) {
					// 메뉴 번호가 request parameter로 안들어오는 경우에는 세션에 저장
					session.setAttribute("m", menu);
				}
				return;
			}
			
			if (session != null) {
				menu = (String) session.getAttribute("m");
				if (menu != null) {
					// 세션에 메뉴 번호가 존재하는 경우 view로 전달한다.
					if (modelAndView != null) {
						modelAndView.addObject("m", menu);
					}
					return;
				}
			}
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
