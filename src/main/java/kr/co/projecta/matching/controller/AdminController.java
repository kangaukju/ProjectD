package kr.co.projecta.matching.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.dao.PageDAO;
import kr.co.projecta.matching.user.Assignment;
import kr.co.projecta.matching.user.Offerer;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;
import kr.co.projecta.matching.user.types.Gender;
import kr.co.projecta.matching.user.types.MatchStatus;
import kr.co.projecta.matching.user.types.Nation;
import kr.co.projecta.matching.user.types.WorkAbility;
import kr.co.projecta.matching.util.Times;

@Controller
public class AdminController extends BaseController {
	
	/**
	 * 관리자 수동 배정요청
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/requirement.do")
	public ModelAndView adminRequirement(
			@RequestParam(value="offererId") String offererId,
			ModelAndView mv, 
			HttpServletRequest request) 
	{
		Offerer offerer = offererDAO.selectOne(offererId);
		mv.addObject("offerer", offerer);
		generateRSAKeyPair(mv, request.getSession());
		return mv;
	}
	
	/**
	 * 관리자 수동 배정요청 처리
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/requirement_r.do")
	@ResponseBody
	public Responser adminRequirementR(
			RequestMap params, 
			HttpServletRequest request) 
	{
		return proxy(
				NOHUP,
				new Callback() {
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
	 * 업체목록 (관리자 수동 배정요청)
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/admin/match/offererlist.do")
	public ModelAndView adminMatchRequirement(
			ModelAndView mv, 
			RequestMap params,
			HttpServletRequest request) 
	{
		return pagingData(mv, params, request, offererDAO);
	}
	
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
	@RequestMapping(value="/admin/select/seekerlist.do")
	public ModelAndView adminSelectSeekerList(
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
		mv = pagingData(mv, params, request, requirementDAO);
		
		String requirementId;
		List<Requirement> list = (List<Requirement>) mv.getModel().get("list");		
		Map<String, List<Assignment>> assignMap = new HashMap<>();
		for (Requirement r : list) {
			requirementId = r.getId();
			List<Assignment> assignments = assignmentDAO.selectList(requirementId);
			assignMap.put(requirementId, assignments);
		}
		mv.addObject("assignMap", assignMap);
		
		return mv;
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
	
	/**
	 * 배정 안된 구직자 목록 조회
	 * @param requirementId
	 * @param mv
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/admin/select/notAssignSeekerlist.do")
	public ModelAndView adminSelectNotAssignSeekerlist(
			@RequestParam(value="requirementId") String requirementId,
			ModelAndView mv,
			HttpServletRequest request,
			RequestMap params) 
	{	
		Requirement requirement = requirementDAO.selectOne(requirementId);
		if (requirement == null) {
			return mv;
		}
		long assignedCount = assignmentDAO.selectCount(requirementId);
		// (임시, 확정)배정이 필요한 경우에만 수동배정을 수행한다.
		if (requirement.getPerson() == assignedCount) {
			return mv;
		}
		// 배정 안된 구직자 목록을 조회한다
		mv = pagingData(mv, params, request,
			new PageDAO<Seeker>() {
				public long getCount(Map<String, Object> params) {
					return seekerDAO.selectNotAssignSeekerCount(params);
				}
				public List<Seeker> getList(Map<String, Object> params) {
					return seekerDAO.selectNotAssignSeeker(params);
				}
		});
		mv.addObject("requirement", requirement);
		mv.addObject("assignedCount", assignedCount);
		return mv;
	}
	
	@RequestMapping(value="/admin/match/automatch.do")
	public ModelAndView adminMatchAutoMatch(
			ModelAndView mv,
			HttpServletRequest request)
	{
		return mv;
	}
	
	/**
	 * 
	 * @param requirementId
	 * @param assign
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/admin/match/manualmatch.do")
	@ResponseBody
	public Responser adminMatchMaualMatch(
			@RequestParam(value="requirementId") String requirementId,
			@RequestParam(value="seekers") String[] seekers,
			HttpServletRequest request,
			HttpSession session)
	{
		return proxy(new Callback() {
			public void tryCatchRun() throws Exception {
				for (String seekerId : seekers) {
					assignmentDAO.insert(requirementId, seekerId);
					
					// 수동배정 후 배정요청 인원과 배정된 인원이 같으면 배정상태를 '완료'로 변경한다.
					long assignedCount = assignmentDAO.selectCount(requirementId);
					Requirement requirement = requirementDAO.selectOne(requirementId);
					int matchStatus = requirement.getMatchStatus().getMatchStatus();
					// 배정상태 '완료'
					if (requirement.getPerson() == assignedCount) {
						if (matchStatus != MatchStatus.COMPLETION) {
							requirementDAO.updateMatchStatus(requirementId, MatchStatus.COMPLETION);
						}
					}
					// 배정상태 '미완료(배정중)'
					else {
						if (matchStatus != MatchStatus.INCOMPLETION) {
							requirementDAO.updateMatchStatus(requirementId, MatchStatus.INCOMPLETION);
						}
					}
				}
			}
		});
	}
}
