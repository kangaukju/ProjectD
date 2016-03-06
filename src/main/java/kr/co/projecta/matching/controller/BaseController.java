package kr.co.projecta.matching.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.context.ContextDatabase;
import kr.co.projecta.matching.controller.Responser.CODE;
import kr.co.projecta.matching.dao.AdminDAO;
import kr.co.projecta.matching.dao.AssignmentDAO;
import kr.co.projecta.matching.dao.CommonDAO;
import kr.co.projecta.matching.dao.DAO;
import kr.co.projecta.matching.dao.HistoryDAO;
import kr.co.projecta.matching.dao.MatchResultDAO;
import kr.co.projecta.matching.dao.OffererDAO;
import kr.co.projecta.matching.dao.PageDAO;
import kr.co.projecta.matching.dao.RequirementDAO;
import kr.co.projecta.matching.dao.SeekerDAO;
import kr.co.projecta.matching.exception.InvalidPhoneNumberException;
import kr.co.projecta.matching.exception.NotAccessableException;
import kr.co.projecta.matching.exception.NotNullException;
import kr.co.projecta.matching.exception.RSAException;
import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.security.RSA;
import kr.co.projecta.matching.user.Identity;
import kr.co.projecta.openapi.naver.NaverMapResultJSON;

@Controller
abstract public class BaseController {
	
	protected Plogger log = Plogger.getLogger(this.getClass());
	
	// 서블릿 컨텍스트
	@Autowired
	protected ServletContext servletContext;
	
	// 컨텍스트 데이터베이
	@Resource(name="ContextDatabase")
	ContextDatabase contextDatabase;
	
	// 구직자 DAO
	@Resource(name="SeekerDAO")
	protected SeekerDAO seekerDAO;

	// 업주 DAO
	@Resource(name="OffererDAO")
	protected OffererDAO offererDAO;
	
	// 배정요청 DAO
	@Resource(name="RequirementDAO")
	RequirementDAO requirementDAO;
	
	// 관리자 DAO
	@Resource(name="AdminDAO")
	protected AdminDAO adminDAO;
	
	// 기본 유틸 DAO
	@Resource(name="CommonDAO")
	CommonDAO commonDAO;

	// 매칭 결과 DAO
	@Resource(name="MatchResultDAO")
	MatchResultDAO matchResultDAO;	
	
	// 배정 DAO
	@Resource(name="AssignmentDAO")
	AssignmentDAO assignmentDAO;
	
	// 로그내역 DAO
	@Resource(name="HistoryDAO")
	HistoryDAO historyDAO;
	
	public static final int DEFAULT_LINE = 10;
	
	
	/**
	 * 홈으로 이동
	 * @param response
	 */
	public static void goHome(HttpServletResponse response) {
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * request parameter 값이 null이면 default 값을 리턴
	 * @param request
	 * @param param
	 * @param def
	 * @return
	 */
	public static String parameter(
			HttpServletRequest request,
			String param,
			Object def)
	{
		String value = request.getParameter(param);
		if (value == null || "".equals(value)) {
			return String.valueOf(def);
		}
		return value;
	}
	
	/**
	 * session에 저장된 개인를 이용하여 RSA 데이터를 복호화
	 * @param session
	 * @param dirty
	 * @return
	 * @throws NotAccessableException
	 * @throws RSAException
	 */
	protected String getCleanSecurity(
			HttpSession session, 
			String dirty) 
			throws NotAccessableException, RSAException 
	{
		PrivateKey privateKey = (PrivateKey) session.getAttribute("privateKey");
		if (privateKey == null) {
			throw new NotAccessableException("Not exist privateKey");
		}
		return RSA.decrypt(privateKey, dirty);
	}
	
	/**
	 * JSP 페이지 데이터를 암호화할 때 사용할 RSA 키 쌍 생성
	 * @param mv
	 * @param session
	 */
	protected void generateRSAKeyPair(
			ModelAndView mv, 
			HttpSession session)
	{
		if (session == null) {
			return;
		}
		
		RSA rsa = new RSA();
		String publicKeyModulus = rsa.getPublicKeyModulus();
		String publicKeyExponent = rsa.getPublicKeyExponent();
		
		// front-end 암호화된 데이터를 복호화할 private key 저장
		PrivateKey privateKey = rsa.getPrivateKey();
		session.setAttribute("privateKey", privateKey);
		
		// front-end 데이터를 암호화 할 public key 전달
		mv.addObject("publicKeyModulus", publicKeyModulus);
		mv.addObject("publicKeyExponent", publicKeyExponent);
	}
	
	/**
	 * NULL 값이 존재 여부 판단
	 * 하나라도 NULL이면 NullPointerException 예외 발생
	 * @param objects
	 * @throws NullPointerException
	 */
	protected void checkMustNotNull(Object ...objects) 
			throws NotNullException
	{
		for (Object o : objects) {
			if (o == null) {
				throw new NotNullException(o+" is null");
			}
		}
	}
	
	/**
	 * 로그인 후 사용자 신원정보(Identity)를 세션에 저장
	 * @param session
	 * @param target
	 * @param identity
	 */
	protected void saveSession(
			HttpSession session, 
			String target, 
			Identity identity)
	{
		session.setAttribute(target, identity);
		session.setAttribute("identity", identity);
	}
	
	/**
	 * 페이징 처리
	 * @param mv
	 * @param params
	 * @param request
	 * @param dao
	 * @return
	 */
	protected ModelAndView pagingData(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request,
			DAO<?> dao) 
	{
		return this.pagingData(mv, params, request, dao, "list");
	}
	
	protected ModelAndView pagingData(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request,
			DAO<?> dao,
			String listname) 
	{
		long page = Long.valueOf(parameter(request, "page", 1));
		long line = Long.valueOf(parameter(request, "line", DEFAULT_LINE));
		long navCount = 0;
		long count = 0;
		
		if (listname == null) {
			listname = "list";
		}
		
		params.put("start", (page-1) * line);
		params.put("end", line);
		
		count = dao.selectCount(params.getMap());
		navCount = (count % line == 0) ? count/line : count/line+1;
		mv.addObject("navCount", navCount);
		mv.addObject("count", count);
		mv.addObject(listname, dao.selectList(params.getMap()));
		mv.addObject("line", line);
		mv.addObject("page", page);
		mv.addAllObjects(params.getMap());
		
		return mv;
	}
	
	/**
	 * 페이징 처리
	 * @param mv
	 * @param params
	 * @param request
	 * @param dao page처리 만을 위한 DAO인터페이스
	 * @return
	 */
	protected ModelAndView pagingData(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request,
			PageDAO<?> dao) 
	{	
		long page = Long.valueOf(parameter(request, "page", 1));
		long line = Long.valueOf(parameter(request, "line", DEFAULT_LINE));
		long navCount = 0;
		long count = 0;
		
		params.put("start", (page-1) * line);
		params.put("end", line);
		
		count = dao.getCount(params.getMap());
		navCount = (count % line == 0) ? count/line : count/line+1;
		mv.addObject("navCount", navCount);
		mv.addObject("count", count);
		mv.addObject("list", dao.getList(params.getMap()));
		mv.addObject("line", line);
		mv.addObject("page", page);
		mv.addAllObjects(params.getMap());
		
		return mv;
	}
	
	/**
	 * HTTP 서블릿의 요청 파라메터 디버그
	 * @param request
	 */
	protected void debugParameters(HttpServletRequest request) {
		Map map = request.getParameterMap();
		Iterator<String> it = map.keySet().iterator();
		String key;
		String [] values;	
		StringBuffer sb;
		long elementCount = 0;
		
		log.d("[debugHttpParameter]==========");
		
		while (it.hasNext()) {
			key = it.next();
			values = (String[]) map.get(key);
			
			sb = new StringBuffer(key);
			sb.append(": ");
			
			for (String val : values) {
				sb.append("'"+val+"'");
				sb.append(",");
			}
			log.d(sb.toString());
			elementCount++;
		}
		if (elementCount == 0) {
			log.d("HTTP request is empty");
		}
		
		log.d("==============================");
	}
	
	protected void debugRequestMap(RequestMap request) {
		Map<String, Object> map = request.getMap();
		Iterator<String> it = map.keySet().iterator();
		String key;
		Object value;	
		StringBuffer sb;
		long elementCount = 0;
		
		log.d("[debugRequestMap]==========");
		
		while (it.hasNext()) {
			key = it.next();
			value = (Object) map.get(key);
			
			sb = new StringBuffer(key);
			sb.append(": ");
			
			if (value instanceof List) {
				List<String> list = (List<String>) value;
				for (String val : list) {
					sb.append("'"+val+"'");
					sb.append(",");	
				}
			}
			else if (value instanceof String) {
				sb.append("'"+value+"'");
				sb.append(",");
			}
			log.d(sb.toString());
			elementCount++;
		}
		if (elementCount == 0) {
			log.d("HTTP request is empty");
		}
		
		log.d("==============================");
	}
	
	/**
	 * address(주소)의 해당하는 지도 파일(PNG)을 다운로드
	 * @param filename: 저장할 파일명
	 * @param address: 조회할 주소
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public String downloadMapFile(String filename, String address) 
			throws MalformedURLException, IOException, ParseException 
	{
		String downloadFilename = String.format("/resources/offerer/%s.png", filename);
		NaverMapResultJSON.Point point = requestNaverMapAPI(address);
		getNaverMapImageDownload(downloadFilename, point);
		return downloadFilename;
	}
	
	private static final String clientId = "MruWBuR3YupBBXUT53p0";
	private static final String clientSecret = "1fL8iUhQyu";
	private static final String clientHost = "http://localhost";
	
	/**
	 * 네이버 지도를 다운로드 (현재(당시)로써는 무료 조회 허용 횟가 가장 높음)
	 * @param filename
	 * @param point
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private void getNaverMapImageDownload(
			String filename,
			NaverMapResultJSON.Point point) 
			throws ClientProtocolException, IOException 
	{
		String url = 
				"https://openapi.naver.com/v1/map/staticmap.bin?"
				+ "clientId="+clientId+"&"
				+ "url="+clientHost+"&"
				+ "center="+point.getX()+","+point.getY()+"&"
				+ "level=10&"
				+ "crs=EPSG:4326&"
				+ "w=250&"
				+ "h=220&"
				+ "baselayer=default&"
				+ "markers="+point.getX()+","+point.getY();
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new IOException("HTTP Status code: "+
					response.getStatusLine().getStatusCode());
		}
		
		OutputStream os = null;
		InputStream is = null;
		try {
			String resource = servletContext.getRealPath("");
			File file = new File(resource+filename);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			os = new FileOutputStream(file);
			is = response.getEntity().getContent();
			byte [] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = is.read(buffer, 0, buffer.length)) >= 0) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) is.close();
			if (os != null) os.close();
		}
	}
	
	/**
	 * 네이버 지도 검색 openAPI 질의
	 * @param address
	 * @return: 네이버 지도 검색 JSON 결과값
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	private NaverMapResultJSON.Point requestNaverMapAPI(String address) 
			throws MalformedURLException, IOException, ParseException 
	{
		String url = "https://openapi.naver.com/v1/map/geocode";
		url += "?query="+URLEncoder.encode(address, "UTF-8");
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		request.addHeader("X-Naver-Client-Id", clientId);
		request.addHeader("X-Naver-Client-Secret", clientSecret);
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new IOException("HTTP Status code: "+
						response.getStatusLine().getStatusCode());
		}
		
		String content = EntityUtils.toString(response.getEntity());
		JSONParser parser = new JSONParser();
		JSONObject root = (JSONObject) parser.parse(content);
		JSONObject result = (JSONObject) root.get("result");
		
		ObjectMapper mapper = new ObjectMapper();
		NaverMapResultJSON naverMapResultJSON 
			= mapper.readValue(result.toJSONString(), NaverMapResultJSON.class);
		
		if (naverMapResultJSON.getTotal() == 0 ||
			naverMapResultJSON.getItems().size() == 0) {
			throw new IOException("result item is empty");
		}
		return naverMapResultJSON.getItems().get(0).getPoint();
	}
	
	/**
	 * 처리 페이지 콜백 인터페이스
	 * @author root
	 *
	 */
	protected static interface Callback {
		public void tryCatchRun() throws Exception;
	}
	
	public static final String NOHUP = "nohup";
	/**
	 * 처리 페이지
	 * @param callback
	 * @return
	 */
	protected Responser proxy(Callback callback) {
		return proxy("/", false, callback);
	}
	protected Responser proxy(String success, Callback callback) {
		return proxy(success, false, callback);
	}
	protected Responser proxy(String success, boolean popup, Callback callback) {
		Responser responser = new Responser(success, popup);
		String message = null;
		try {
			callback.tryCatchRun();
		} catch (NumberFormatException e) {
			message = e.getMessage();
			responser = new Responser(CODE.PARAMETERS_ERROR, true);
		} catch (DuplicateKeyException e) {
			message = e.getMessage();
			responser = new Responser(CODE.DUPLICATE_KEY, true);
		} catch (NotAccessableException e) {
			message = e.getMessage();
			responser = new Responser(CODE.NOT_ACCESSABLE, true);
		} catch (RSAException e) {
			message = e.getMessage();
			responser = new Responser(CODE.SERVER_ERROR, true);
		} catch (InvalidPhoneNumberException e) {
			message = e.getMessage();
			responser = new Responser(CODE.PARAMETERS_ERROR, true);
		} catch (NotNullException e) {
			message = e.getMessage();
			responser = new Responser(CODE.PARAMETERS_ERROR, true);
		} catch (Exception e) {
			message = e.getMessage();
			responser = new Responser(CODE.SERVER_ERROR, true);
		}
		if (message != null) {
			System.err.println(message);
		}
		return responser;
	}
}
