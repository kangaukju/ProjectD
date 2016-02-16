package kr.co.projecta.matching.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController extends BaseController {
	
	/**
	 * 관리자 메인 화면
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/admin/admin.do")
	public ModelAndView joinAdmin(
			ModelAndView mv) 
	{
		return mv;
	}
	
	/**
	 * 구직자 조회
	 * @param mv
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/select/seeker.do")
	public ModelAndView adminSelectSeeker(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request)
	{
		return pagingData(mv, params, request, seekerDAO);
	}
	
	/**
	 * 배정요청 목록 조회
	 * @param mv
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/select/requirementlist.do")
	public ModelAndView adminSelectRequirementlist(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request)
	{
		return pagingData(mv, params, request, requirementDAO);
	}
	
	/**
	 * 주소록 조회
	 * @param mv
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/admin/select/juso.do")
	public ModelAndView selectJuso(
			ModelAndView mv, 
			RequestMap params) 
	{
		mv.addObject("jusoSeoulList", commonDAO.selectList(params.getMap()));
		mv.addAllObjects(params.getMap());
		return mv;
	}
}
