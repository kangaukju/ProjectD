package kr.co.projecta.matching.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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

import kr.co.projecta.matching.match.MatchService2;
import kr.co.projecta.matching.user.Assignment;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;
import kr.co.projecta.matching.user.types.MatchStatus;

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
		
		MatchService2 ms2 = new MatchService2(
				seekerDAO, requirementDAO, assignmentDAO, topLimit);
		
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
		return pagingData(mv, params, request, offererDAO);
	}
	
	/**
	 * 자세한 배정내역
	 * @param target
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/admin/match/matchresult_offerer.do")
	public ModelAndView adminMatchMatchResultDetail(
			@RequestParam(value="id") String id,
			ModelAndView mv,
			RequestMap params,
			HttpServletRequest request) 
	{
		String database = servletContext.getRealPath("matching/"+id);
		params.put("database", database);
System.out.println(database);
		return pagingData(mv, params, request, matchResultDAO);
	}
	
	/**
	 * 
	 * @param target
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/admin/match/matchresult_detail.do")
	@ResponseBody
	public void adminMatchMatchResultDetail(
			@RequestParam(value="id") String id,
			@RequestParam(value="target") String target,
			HttpServletRequest request,
			HttpServletResponse response) 
	{
		if (!target.endsWith(".json")) {
			target += ".json";
		}
		// matching 디렉토리 리소스
		String resource = servletContext.getRealPath("matching");
		String filename = resource+"/"+id+"/"+target;
		BufferedReader br = null;
		String line = null;
		PrintWriter writer = null;
		try {
			response.setContentType("text/html;charset=utf-8");
			writer = response.getWriter();
			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				writer.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) try { br.close(); } catch (IOException e) { }
			writer.flush();
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
	
	/**
	 * 배정된 구직자 리스트
	 * @param mv
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/match/seekerlist.do")
	public ModelAndView adminMatchSeekerlist(
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
	

	/**
	 * 관리자가 구직자의 배정을 확정한다.
	 * @param requirementId
	 * @param seekerId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/admin/match/seeker/confirm.do")
	@ResponseBody
	public Responser adminMatchSeekerConfirm(
			@RequestParam(value="requirementId") String requirementId,
			@RequestParam(value="seekerId") String seekerId,
			HttpServletRequest request,
			HttpSession session)
	{
		return proxy(
			"/admin/match/seekerlist.do",
			new Callback() {
				public void tryCatchRun() throws Exception {
				// 구직자가 배정 확정함.
				if (!assignmentDAO.setAssignConfirm(requirementId, seekerId)) {
					throw new Exception(String.format(
						"Error confirm [requirementId: %s, seekerId: %s]", requirementId, seekerId));
				}
				
				Requirement requirement = requirementDAO.selectOne("id", requirementId);
				// 배정확정 수가 배정요청 수와 같으면 배정의 상태를 '완료'로 변경한다.
				long confirmCount = assignmentDAO.getAssignConfirm(requirementId);
				if (requirement.getPerson() == confirmCount) {
					requirementDAO.updateMatchStatus(requirementId, MatchStatus.COMPLETION);
				}
			}
		});
	}
	
	@RequestMapping(value="/admin/match/offerer/confirm.do")
	@ResponseBody
	public Responser adminMatchOffererConfirm(
			@RequestParam(value="requirementId") String requirementId,
			HttpServletRequest request,
			HttpSession session)
	{
		return null;
	}
	
	/**
	 * 관리자가 구직자의 배정을 취소한다.
	 * @param requirementId
	 * @param seekerId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/admin/match/seeker/cancel.do")
	@ResponseBody
	public Responser adminMatchSeekerCancel(
			@RequestParam(value="requirementId") String requirementId,
			@RequestParam(value="seekerId") String seekerId,
			HttpServletRequest request,
			HttpSession session)
	{
		return proxy(
			"/admin/match/seekerlist.do",
			new Callback() {
				public void tryCatchRun() throws Exception {				
				// 구직자가 배정 확정함.
				if (!assignmentDAO.setAssignCancel(requirementId, seekerId)) {
					throw new Exception(String.format(
						"Error confirm [requirementId: %s, seekerId: %s]", requirementId, seekerId));
				}
				// 배정취소로 배정상태를 '미완료'로 변경한다. 
				requirementDAO.updateMatchStatus(requirementId, MatchStatus.INCOMPLETION);
			}
		});
	}
	
	@RequestMapping(value="/admin/match/offerer/cancel.do")
	@ResponseBody
	public Responser adminMatchOffererCancel(
			@RequestParam(value="requirementId") String requirementId,
			@RequestParam(value="seekerId") String seekerId,
			HttpServletRequest request,
			HttpSession session)
	{
		return null;
	}
}