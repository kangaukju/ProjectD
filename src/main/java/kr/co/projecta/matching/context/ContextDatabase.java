package kr.co.projecta.matching.context;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import kr.co.projecta.gis.map.SimilarityTable;
import kr.co.projecta.gis.map.SimilarityTableSeoul;
import kr.co.projecta.matching.dao.CommonDAO;
import kr.co.projecta.matching.exception.ContextDatabaseException;
import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.user.Region;

@Component("ContextDatabase")
public class ContextDatabase {
	static Plogger log = Plogger.getLogger(ContextDatabase.class);
	
	public static final String pakagename = "kr.co.projecta";
	
	Map<String, List<Object>> database;
	
	@Resource(name="CommonDAO")
	CommonDAO commonDAO;
	
	private static ContextDatabase contextDatabase = null;
	
	public static ContextDatabase getInstance() {
		return contextDatabase;
	}
	
	public ContextDatabase() {
		log.d("load context database");
		database = loadContextDatabase();
		showDatabase();
		// spring bean and singleton
		contextDatabase = this;
	}
	
	public SimilarityTable getSimilarityTableSeoul() {
		try {
			List list = database.get("SimilarityTable.seoul");
			if (list == null || list.get(0) == null) {
				
				List<Region> regions = getJusoSeoulList();
				if (regions == null || regions.size() == 0) {
					return null;
				}
				int startNominal = (int)regions.get(0).getId();
				String []regionNames = new String[regions.size()];
				for (int i=0; i<regions.size(); i++) {
					regionNames[i] = regions.get(i).getSigunguName();
				}
				SimilarityTable similarityTable = 
						new SimilarityTableSeoul(startNominal, regionNames);
				
				list = new ArrayList();
				list.add(0, similarityTable);
				
				database.put("SimilarityTable.seoul", list);
			}
			return (SimilarityTable) list.get(0);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public List<Region> getJusoSeoulList() {
		List seoul = database.get("juso.seoul.list");
		if (seoul == null) {
			seoul = commonDAO.selectListSeoul();
			database.put("juso.seoul.list", seoul);
		}
		return seoul;
	}
	
	public Map<Integer, Region> getJusoSeoulMap() {
		List seoul = database.get("juso.seoul.map");
		if (seoul == null) {
			seoul = new ArrayList(1);
			seoul.add(commonDAO.selectMapSeoul());
			database.put("juso.seoul.map", seoul);
		}
		return (Map<Integer, Region>) seoul.get(0);
	}
	
	public Map<Integer, Region> getJusoAllMap() {
		List all = database.get("juso.all.map");
		if (all == null) {
			all = new ArrayList(1);
			all.add(commonDAO.selectMap());
			database.put("juso.all.map", all);
		}
		return (Map<Integer, Region>) all.get(0);
	}

	public Object getContext(String key) {
		return database.get(key);
	}
	
	public Map<String, List<Object>> getContext() {
		return database;
	}
		
	public void showDatabase() {		
		for(String key : database.keySet()) {
			for (Object o : database.get(key)) {
				log.d("key: "+key+", "+o);
			}
		}
	}
	
	private Map<String, List<Object>> loadContextDatabase() {
		// context DB 생
		Map<String, List<Object>> contextMap = new HashMap<String, List<Object>>();
		
		log.d("pakage is "+pakagename);
		List<Class<?>> classlist = getFilterClasses(pakagename, ContextSchemable.class);
		
		try {
			ContextSchemable schemable;
			
			for (Class<?> clazz : classlist) {
				if (clazz.isEnum()) {
					// enum 타입
					schemable = (ContextSchemable) clazz.getEnumConstants()[0];
//					log.d("Type of enum: "+clazz.getSimpleName());
				} else {
					// 그외 interface 타입
					schemable = (ContextSchemable) clazz.newInstance();
//					log.d("Type of class: "+clazz.getSimpleName());
				}
				
				List<Object> list = schemable.getContextSchemaData();				
				if (list != null) {
					contextMap.put(schemable.getClass().getSimpleName(), list);
				}
			}
		} catch (InstantiationException e) {
			log.e(e.getMessage());
		} catch (IllegalAccessException e) {
			log.e(e.getMessage());
		}
		return contextMap;
	}
	
	public static List<Class<?>> getFilterClasses(String packagename, Class<?> filter)
			throws ContextDatabaseException
	{
		List<Class<?>> classlist = new ArrayList<Class<?>>();
		
		for (Class<?> clazz : getClasses(packagename, null)) {
			if (!clazz.isInterface()) {
				// 타입 필터 검사
				if (filter.isAssignableFrom(clazz)) {
					log.d("database added '"+clazz+"'");
					classlist.add(clazz);
				}
			}
		}
		return classlist;
	}
	
	// 패키지의 모든 클래스 정보 조회
	public static List<Class<?>> getClasses(String packagename, List<Class<?>> classlist) 
			throws ContextDatabaseException
	{
		if (classlist == null) {
			classlist = new ArrayList<Class<?>>();
		}
		
		// 패키지명을 디렉토리명으로 변경
		String pakagePath = packagename.replace(".", "/");
		// class 파일이 설치된 환경정보
		URL path = Thread.currentThread().getContextClassLoader().getResource("");		
		String resource = path + pakagePath;
		
		try {
			resource = URLDecoder.decode(resource, "UTF-8");
			String resourceProtocol = "file:";
			if (!resource.startsWith(resourceProtocol)) {
				throw new ContextDatabaseException(
						"Unkown resource path - "+resource);
			}
			resource = resource.substring(resourceProtocol.length());
			
			log.d("pakage path is "+resource);
		} catch (UnsupportedEncodingException e) {
			throw new ContextDatabaseException(e);
		}
		
		File resourceDir = new File(resource);
		if (!resourceDir.exists()) {
			throw new ContextDatabaseException(
					"packagename: "+packagename+" not exist "
					+"[resource: "+resource+"]");
		}
		
		try {
			String classFile;
			String classFilename;
			Class<?> clazz;
			for (File file : resourceDir.listFiles()) {
				classFilename = file.getName();
				if (file.isDirectory()) {
					getClasses(packagename+"."+classFilename, classlist);
				}
				else if (classFilename.endsWith(".class")) {
					// class filename 에서 '.class' 확장자 제거
					classFile = packagename+"."
							+classFilename.substring(0, classFilename.length()-".class".length());
					// class 객체 로드
					clazz = Class.forName(classFile);
					classlist.add(clazz);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new ContextDatabaseException(e);
		}
		
		return classlist;
	}
	
	public static void main(String [] args) {
		ContextDatabase context = new ContextDatabase();
		log.d("show context database");
		context.showDatabase();
	}
}
