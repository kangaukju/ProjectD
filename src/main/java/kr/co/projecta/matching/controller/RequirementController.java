package kr.co.projecta.matching.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.match.MatchService2;
import kr.co.projecta.matching.user.Offerer;

@Controller
public class RequirementController extends BaseController {
	
	/**
	 * 수동배정 수행
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/match/manual.do")
	@ResponseBody
	public ModelAndView adminMatchManual(
			ModelAndView mv,
			HttpServletRequest request) 
	{
		return mv;
	}
	
	/**
	 * 자동배정 수행
	 * @param mv
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/admin/match/auto.do")
	@ResponseBody
	public void adminMatchAuto(
			ModelAndView mv,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException 
	{
		// matching 디렉토리 리소스
		String resource = servletContext.getRealPath("matching");
		int topLimit = 5;
		
		MatchService2 ms2 = new MatchService2(seekerDAO, requirementDAO, topLimit);
		// TODO: for debug
		ms2.setResultExportPath(resource);
		ms2.autoMatchService();
		
		String prev = request.getHeader("REFERER");
		if (prev == null) {
			prev = "/";
		}
		response.sendRedirect(prev);
	}
	
	/**
	 * 자동배정 결과 목록
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/match/matchresult.do")
	@ResponseBody
	public ModelAndView adminMatchMatchresult(
			ModelAndView mv,
			RequestMap params,
			HttpServletRequest request) 
	{
		String database = servletContext.getRealPath("matching");
		params.put("database", database);
		return pagingData(mv, params, request, matchResultDAO); 
	}
	
	/**
	 * 자동배정 결과 목록 2
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/match/matchresult2.do")
	@ResponseBody
	public ModelAndView adminMatchMatchresult2(
			ModelAndView mv,
			RequestMap params,
			HttpServletRequest request) 
	{
		String target = request.getParameter("target");
		String database = servletContext.getRealPath("matching");
		params.put("database", database+"/"+target);
		return pagingData(mv, params, request, matchResultDAO); 
	}
	
	/**
	 * 자세한 배정내역
	 * @param target
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/admin/match/matchresult_detail.do")
	@ResponseBody
	public void adminMatchMatchResultDetail(
			@RequestParam(value="target") String target,
			HttpServletRequest request,
			HttpServletResponse response) 
	{
		// matching 디렉토리 리소스
		String resource = servletContext.getRealPath("matching");
		InputStreamReader input = null;
		try {		
			input = new InputStreamReader(
					new FileInputStream(resource+"/"+target));
			IOUtils.copy(input, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) try { input.close(); } catch (IOException e) { }
		}
	}
	
	/**
	 * 배정현황
	 * @param mv
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/select/requirement.do")
	public ModelAndView selectRequirement(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request) 
	{
		return pagingData(mv, params, request, requirementDAO);	
	}
}
