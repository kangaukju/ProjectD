package kr.co.projecta.matching.log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import kr.co.projecta.matching.user.Seeker;

public class Plogger {
	Logger log;
	Map<String, Method> methodCache = new HashMap<String, Method>();
	
	private Plogger(Logger log) {
		this.log = log;
	}
	
	public boolean isDebug() {
		return log.isDebugEnabled();
	}
	public boolean isTrace() {
		return log.isTraceEnabled();
	}
	public boolean isInfo() {
		return log.isInfoEnabled();
	}
	
	public static Plogger getLogger(Class<?> clazz) {
		return new Plogger(Logger.getLogger(clazz));
	}
		
	public void I(Object message) {
		log.info(message);
	}
	public void E(Object message) {
		log.error(message);
	}	
	public void T(Object message) {
		log.trace(message);
	}	
	public void D(Object message) {
		log.debug(message);
	}	
	public void W(Object message) {
		log.warn(message);
	}	
	public void F(Object message) {
		log.fatal(message);
	}
	public void i(Object message) {
		log.info(message);
	}
	public void e(Object message) {
		log.error(message);
	}	
	public void t(Object message) {
		log.trace(message);
	}	
	public void d(Object message) {
		log.debug(message);
	}	
	public void w(Object message) {
		log.warn(message);
	}	
	public void f(Object message) {
		log.fatal(message);
	}
	
	public void i(Class<?> filterClass, Object filter, Object target, Object message) {
		logging(filterClass, filter, target, "info", message);
	}
	public void e(Class<?> filterClass, Object filter, Object target, Object message) {
		logging(filterClass, filter, target, "error", message);
	}
	public void t(Class<?> filterClass, Object filter, Object target, Object message) {
		logging(filterClass, filter, target, "trace", message);
	}
	public void d(Class<?> filterClass, Object filter, Object target, Object message) {
		logging(filterClass, filter, target, "debug", message);
	}
	public void w(Class<?> filterClass, Object filter, Object target, Object message) {
		logging(filterClass, filter, target, "warn", message);
	}
	public void f(Class<?> filterClass, Object filter, Object target, Object message) {
		logging(filterClass, filter, target, "fatal", message);
	}
	
	private void logging(Class<?> filterClass, Object filter, Object target, String methodName, Object message) {
		// TODO: getDeclaredMethods is only declared methods in this class
		for (Method method : filterClass.getMethods()) {			
			// only public getter method
			if (method.getName().startsWith("get") 
					&& !"getClass".equals(method.getName()))
			{
				try {
					Object filterResult = method.invoke(filter);
					Object targetResult = method.invoke(target);					
					
					if (targetResult == null || filterResult == null) {
						continue;
					}
					
					if (filterResult.equals(targetResult)) {
						
						// check cache
						Method logMethod = methodCache.get(methodName);
						if (logMethod == null) {
							logMethod = findLog4jLogMethod(methodName);
							methodCache.put(methodName, logMethod);
						}
						/*
						else System.out.println("find cache method - " + methodName);
						*/
						logMethod.invoke(log, message);
					}
					
				} catch (	IllegalAccessException | 
							IllegalArgumentException | 
							InvocationTargetException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	private Method findLog4jLogMethod(String name) {
		for (Method method : Logger.class.getMethods()) {
			if (method.getName().equals(name)) {
				if (method.getParameterTypes().length == 1) {
					return method;
				}
			}
		}
		throw new RuntimeException("Could not find Log4j method - "+name);
	}
	
	
	
	
	public static void main(String [] args) {
		Plogger log = Plogger.getLogger(Plogger.class);
		
		log.i("11111"+"22222222"+3);
		log.e("11111"+"22222222"+3);
		log.t("11111"+"22222222"+3);
		log.d("11111"+"22222222"+3);
		log.w("11111"+"22222222"+3);
		log.f("11111"+"22222222"+3);
		
		
		Seeker s1 = new Seeker(); s1.setName("kaka");
		Seeker s2 = new Seeker(); s2.setName("kinow");
		Seeker s3 = new Seeker(); s3.setName("mama");
		Seeker s4 = new Seeker(); s4.setName("kaka");
		
		log.d(Seeker.class, s1, s4, "find: "+s4);
		log.d(Seeker.class, s1, s4, "find: "+s4);
		log.d(Seeker.class, s1, s4, "find: "+s4);
		
		System.out.println("end");
	}
}
