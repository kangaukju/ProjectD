package kr.co.projecta.matching.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.user.Gender;
import kr.co.projecta.matching.user.Nation;
import kr.co.projecta.matching.user.Offerer;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.WorkAbility;
import kr.co.projecta.matching.util.Times;

@Controller
public class OffererController extends BaseController {
	
	/**
	 * 업주 상세 설명 조회
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/offerer/detail.do")
	public ModelAndView offererDetail(
			ModelAndView mv, 
			HttpServletRequest request) 
	{
		String id = request.getParameter("id");
		Offerer offerer = offererDAO.selectOne("id", id);
		mv.addObject("offerer", offerer);
		
		/*
		// 강제로 이미지 생성
		try {
			downloadOffererMapFile(offerer);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return mv;
	}
	
	/**
	 * 배정요청
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/offerer/requirement.do")
	public ModelAndView joinRequirement(
			ModelAndView mv, 
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) 
	{
		if (session.getAttribute("offerer") == null) {
			goHome(response);
		}
		generateRSAKeyPair(mv, request.getSession());
		return mv;
	}
	
	/**
	 * 배정요청 처리
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/offerer/requirement_r.do")
	@ResponseBody
	public Responser joinRequirementR(
			RequestMap params, 
			HttpServletRequest request) 
	{
		return proxy(new Callback() {
			public void tryCatchRun() throws Exception {
				Requirement requirement = new Requirement();
				
				String offererId = request.getParameter("offererId");
				String workDate1 = request.getParameter("workDate1");
				String workDate2 = request.getParameter("workDate2");
				String workTime = request.getParameter("workTime");
				String workAbility = request.getParameter("workAbility");
				String gender = request.getParameter("gender");
				String ageRange = request.getParameter("ageRange");
				String nation = request.getParameter("nation");
				
				checkMustNotNull(offererId, workDate1, workDate2, workTime, workAbility, gender, ageRange, nation);
				
				offererId = getCleanSecurity(request.getSession(), offererId);
				
				requirement.setOffererId(offererId);
				requirement.setWorkDate(Times.getDateYYYYMMDDHH(workDate1+" "+workDate2));
				requirement.setWorkTime(Integer.valueOf(workTime));
				requirement.setAgeRange(Integer.valueOf(ageRange));
				requirement.setWorkAbility(WorkAbility.valueOf(workAbility));
				requirement.setNation(Nation.valueOf(nation));
				requirement.setGender(Gender.valueOf(gender));
				
				requirementDAO.join(requirement);
			}
		});
	}
	
	/**
	 * 업주 조회
	 * @param mv
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/offerer/offererlist.do")
	public ModelAndView offererOffererlist(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request) 
	{
		return pagingData(mv, params, request, offererDAO);
	}
}
