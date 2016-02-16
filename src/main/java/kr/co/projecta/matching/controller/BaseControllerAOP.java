package kr.co.projecta.matching.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.context.ContextDatabase;

@Aspect
public class BaseControllerAOP {
	@Resource(name="ContextDatabase")
	ContextDatabase contextDatabase;
	
    @Pointcut("within(kr.co.projecta.matching.controller..*Controller)")
    public void BaseControllerPointCut() { }
    
    @Before("BaseControllerPointCut()")
    public void before(JoinPoint joinPoint) {
    	String typeName = joinPoint.getSignature().getDeclaringTypeName();
    	
    	// 주소록, 컨텍스트 데이터베이스를 AOP에서 저장
    	if (typeName.lastIndexOf("Controller") > -1) {
        	Object [] args = joinPoint.getArgs();
        	for (Object arg : args) {
        		if (arg != null) {
        			// check type 'ModelAndView'
        			if (arg.getClass().isAssignableFrom(ModelAndView.class)) {
        				ModelAndView mv = (ModelAndView) arg;        				
        				mv.addObject("jusoAllMap", contextDatabase.getJusoAllMap());
        				mv.addObject("jusoSeoulMap", contextDatabase.getJusoSeoulMap());
        				mv.addObject("jusoSeoulList", contextDatabase.getJusoSeoulList());
        				mv.addObject("context", contextDatabase.getContext());
        				break;
        			}
        			// check type 'HttpServletRequest'
        			else if (arg.getClass().isAssignableFrom(HttpServletRequest.class)) {
        				HttpServletRequest request = (HttpServletRequest) arg;
        				request.setAttribute("jusoAllMap", contextDatabase.getJusoAllMap());
        				request.setAttribute("jusoSeoulMap", contextDatabase.getJusoSeoulMap());
        				request.setAttribute("jusoSeoulList", contextDatabase.getJusoSeoulList());
        				request.setAttribute("context", contextDatabase.getContext());
        				break;
        			}
        		}
        	}
    	} else if (typeName.lastIndexOf("Service") > -1) {
    		
    	} else if (typeName.lastIndexOf("DAO") > -1) {
    		
    	} else if (typeName.startsWith("/matching")) {
    		
    	}

    }
}
