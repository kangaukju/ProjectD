package kr.co.projecta.matching.controller;


import java.security.PrivateKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.security.RSA;
import kr.co.projecta.matching.user.Identity;
import kr.co.projecta.matching.user.Offerer;
import kr.co.projecta.matching.user.Seeker;

@Controller
public class CommonController extends BaseController {
	Plogger log = Plogger.getLogger(this.getClass());
	
	/**
	 * 공지사항
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/notice.do")
	public ModelAndView notice(ModelAndView mv) {
		return mv;
	}
	
	/**
	 * 이용방법
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/howto.do")
	public ModelAndView howTo(ModelAndView mv) {
		return mv;
	}
	
	/**
	 * 요금안내
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/howmoney.do")
	public ModelAndView howMoney(ModelAndView mv) {
		return mv;
	}
	
	/**
	 * 메인화면
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/home.do")
	public ModelAndView home(ModelAndView mv) {
		//mv.addObject("popuper", new Popuper("test", 100, 200, "head2", "body11111111", "foot3"));
		return mv;
	}
	
	/**
	 * 회원가입
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/join.do")
	public ModelAndView join(ModelAndView mv) {
		return mv;
	}
	
	/**
	 * 로그인
	 * @param mv
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login.do")
	public ModelAndView login(
			ModelAndView mv, 
			HttpServletRequest request,
			HttpSession session) 
	{
		RSA rsa = new RSA();
		String publicKeyModulus = rsa.getPublicKeyModulus();
		String publicKeyExponent = rsa.getPublicKeyExponent();
		
		// front-end 암호화된 데이터를 복호화할 private key 저장
		PrivateKey privateKey = rsa.getPrivateKey();
		session.setAttribute("privateKey", privateKey);
		
		// front-end 데이터를 암호화 할 public key 전달
		mv.addObject("publicKeyModulus", publicKeyModulus);
		mv.addObject("publicKeyExponent", publicKeyExponent);
		return mv;
	}
	
	@RequestMapping(value="/login/seeker.do")
	public ModelAndView loginSeeker(
			ModelAndView mv, 
			HttpServletRequest request,
			HttpSession session) 
	{
		return login(mv, request, session);
	}
	@RequestMapping(value="/login/offerer.do")
	public ModelAndView loginOfferer(
			ModelAndView mv, 
			HttpServletRequest request,
			HttpSession session) 
	{
		return login(mv, request, session);
	}
	@RequestMapping(value="/login/admin.do")
	public ModelAndView loginAdmin(
			ModelAndView mv, 
			HttpServletRequest request,
			HttpSession session) 
	{
		return login(mv, request, session);
	}
	
	/**
	 * 로그아웃
	 * @param request
	 * @param session
	 * @param response
	 */
	@RequestMapping(value="/logout.do")
	public void logout(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) 
	{
		Identity identity = (Identity) session.getAttribute("identity");
		if (identity != null) {
			log.i("logout: "+identity.getName()+"("+identity.getId()+")");
		}
		session.invalidate();
		BaseController.goHome(response);
	}
	
	/**
	 * 에러화면
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/error.do")
	public ModelAndView error(ModelAndView mv) {
		return mv;
	}
	
	/**
	 * 구직자 아이디 중복 검사
	 * @param id
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/check_dup_seeker.do")
	@ResponseBody
	public Responser seekerCheckDupSeeker(
			@RequestParam(value="id") String seekerId,
			ModelAndView mv, 
			HttpServletRequest request)
	{
		return proxy(new Callback() {
			public void tryCatchRun() throws Exception {
				Seeker seeker = seekerDAO.selectOne(seekerId);
				if (seeker != null && 
					seeker.getId().equals(seekerId)) {
					throw new DuplicateKeyException("seeker ["+seekerId+"]id duplicated");
				}
			}
		});
	}
	
	/**
	 * 고용주 아이디 중복 검사
	 * @param id
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/check_dup_offerer.do")
	@ResponseBody
	public Responser seekerCheckDupOfferer(
			@RequestParam(value="id") String offererId,
			ModelAndView mv, 
			HttpServletRequest request)
	{
		return proxy(new Callback() {
			public void tryCatchRun() throws Exception {
				Offerer offerer = offererDAO.selectOne(offererId);
				if (offerer != null && 
					offerer.getId().equals(offererId)) {
					throw new DuplicateKeyException("offerer ["+offererId+"]id duplicated");
				}
			}
		});
	}
}

