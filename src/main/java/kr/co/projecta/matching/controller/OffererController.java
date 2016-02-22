package kr.co.projecta.matching.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.user.Assignment;
import kr.co.projecta.matching.user.Identity;
import kr.co.projecta.matching.user.Offerer;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;
import kr.co.projecta.matching.user.types.Gender;
import kr.co.projecta.matching.user.types.Nation;
import kr.co.projecta.matching.user.types.WorkAbility;
import kr.co.projecta.matching.util.Times;

@Controller
public class OffererController extends BaseController {
	
	/**
	 * 업체의 배정내역 조회
	 * @param mv
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/offerer/my_requirementlist.do")
	public ModelAndView offererMyrequirementlist(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) 
	{
		Identity identity = (Identity) session.getAttribute("identity");
		if (identity == null) {
			goHome(response);
			return mv;
		}
		params.put("offererId", identity.getId());
		return pagingData(mv, params, request, requirementDAO);
	}
	
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
				String person = request.getParameter("person");
				
				checkMustNotNull(offererId, workDate1, workDate2, workTime, workAbility, gender, ageRange, nation);
				
				offererId = getCleanSecurity(request.getSession(), offererId);
				
				requirement.setOffererId(offererId);
				requirement.setWorkDate(Times.getDateYYYYMMDDHH(workDate1+" "+workDate2));
				requirement.setWorkTime(Integer.valueOf(workTime));
				requirement.setAgeRange(Integer.valueOf(ageRange));
				requirement.setWorkAbility(WorkAbility.valueOf(workAbility).getWorkAbility());
				requirement.setNation(Nation.valueOf(nation).getNation());
				requirement.setGender(Gender.valueOf(gender).getGender());
				requirement.setPerson(Integer.valueOf(person));
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
	
	/**
	 * 배정된 구직자 리스트
	 * @param mv
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/offerer/match/seekerlist.do")
	public ModelAndView offererMatchSeekerlist(
			@RequestParam(value="requirementId") String requirementId,
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request) 
	{
		List<Seeker> candidateSeekerList = new ArrayList<>();
		List<Seeker> confirmSeekerList = new ArrayList<>();
		
		Requirement requirement = requirementDAO.selectOne("id", requirementId);
		List<Assignment> assignmentList = assignmentDAO.selectList(requirementId);
		
		Seeker seeker;
		for (Assignment a : assignmentList) {
			seeker = seekerDAO.selectOne("id", a.getSeekerId());
			switch (a.getConfirm()) {
			case Assignment.CANDIDATE:
				candidateSeekerList.add(seeker);
				break;
			case Assignment.CONFIRM:
				confirmSeekerList.add(seeker);
				break;
			}
		}
		
		mv.addObject("requirement", requirement);		
		mv.addObject("candidateSeekerList", candidateSeekerList);
		mv.addObject("confirmSeekerList", confirmSeekerList);		
		return mv;
	}
}
