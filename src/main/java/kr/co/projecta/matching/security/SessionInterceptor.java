package kr.co.projecta.matching.security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.Identity;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	Plogger log = Plogger.getLogger(this.getClass());
	
	static public class Target {
		String identity;
		String urlPattern;
		public String getIdentity() {
			return identity;
		}
		public void setIdentity(String identity) {
			this.identity = identity;
		}
		public String getUrlPattern() {
			return urlPattern;
		}
		public void setUrlPattern(String urlPattern) {
			this.urlPattern = urlPattern;
		}
	}
	
	List<Target> targetConfig = new ArrayList<Target>();
	
	
	public List<Target> getTargetConfig() {
		return targetConfig;
	}

	public void setTargetConfig(List<Target> targetConfig) {
		this.targetConfig = targetConfig;
	}

	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception 
	{
		HttpSession session = request.getSession();
		
		Identity identity;
		String url;
		for (Target target : targetConfig) {
			
			url = request.getRequestURL().toString();
			
			// 1. check URL pattern matching
			if (Pattern.matches(target.getUrlPattern(), url)) {
				
				// 2. check data of login user
				identity = (Identity) session.getAttribute(target.getIdentity());
				if (identity == null) {
					log.i(identity.getName()+"("+identity.getId()+") session is invalid.");
					return false;
				}
			}
		}
		return true;
	}
	
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView modelAndView) throws Exception 
	{
		super.postHandle(request, response, handler, modelAndView);
	}

}
