package kr.co.projecta.matching.controller;


import java.security.PrivateKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.popup.Popuper;
import kr.co.projecta.matching.security.RSA;
import kr.co.projecta.matching.user.Identity;

@Controller
public class CommonController extends BaseController {
	Plogger log = Plogger.getLogger(this.getClass());
	
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
		log.i("logout: "+identity.getName()+"("+identity.getId()+")");
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
}

