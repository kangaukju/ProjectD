package kr.co.projecta.matching.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.controller.BaseController.Callback;
import kr.co.projecta.matching.exception.NotAccessableException;
import kr.co.projecta.matching.user.Assignment;
import kr.co.projecta.matching.user.Identity;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;

@Controller
public class SeekerController extends BaseController {

	@RequestMapping(value="/seeker/detail.do")
	public ModelAndView seekerDetail(
			ModelAndView mv, 
			HttpServletRequest request) 
	{
		String id = request.getParameter("id");
		Seeker seekr = seekerDAO.selectOne("id", id);
		mv.addObject("seekr", seekr);
		return mv;
	}
	
	/**
	 * 나의 배정현황 조회
	 * @param mv
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/seeker/myrequirement.do")
	public ModelAndView seekerMyrequirement(
			ModelAndView mv, 
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session)
	{
		List<Requirement> candidateRequirementList = new ArrayList<>(); // 임시 배정
		List<Requirement> confirmRequirementList = new ArrayList<>(); // 확정 배정
		
		if (session == null) {
			goHome(response);
		}
		
		Identity identity = (Identity) session.getAttribute("identity");
		if (identity == null) {
			goHome(response);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seekerId", identity.getId());
		for (Assignment a : assignmentDAO.selectList(params)) {
			Requirement requirement = 
					requirementDAO.selectOne("id", a.getRequirementId());
			
			switch (a.getConfirm()) {
			case Assignment.CONFIRM:
				confirmRequirementList.add(requirement);
				break;
			case Assignment.CANDIDATE:
				candidateRequirementList.add(requirement);
				break;
			}
			
		}
		mv.addObject("confirmRequirementList", confirmRequirementList);
		mv.addObject("candidateRequirementList", candidateRequirementList);
		return mv;
	}	

	/**
	 * 구직자가 배정을 확정한다.
	 * @param requirementId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/seeker/confirm.do")
	@ResponseBody
	public Responser seekerConfirm(
			@RequestParam(value="requirementId") String requirementId,
			HttpServletRequest request,
			HttpSession session)
	{
		return proxy(new Callback() {
			public void tryCatchRun() throws Exception {
				Identity identity = (Identity) session.getAttribute("seeker");
				if (identity == null) {
					throw new NotAccessableException("You're not seeker.");
				}
				
				String seekerId = identity.getId();
				// 구직자가 배정 확정함.
				if (!assignmentDAO.setAssignConfirm(requirementId, seekerId)) {
					throw new Exception(String.format(
						"Error confirm [requirementId: %s, seekerId: %s]", requirementId, seekerId));
				}
			}
		});
	}
}
