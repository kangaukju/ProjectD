package kr.co.projecta.matching.controller;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.projecta.matching.controller.Responser.CODE;
import kr.co.projecta.matching.dao.DAO;
import kr.co.projecta.matching.exception.NotAccessableException;
import kr.co.projecta.matching.exception.NotNullException;
import kr.co.projecta.matching.exception.RSAException;
import kr.co.projecta.matching.exception.UserNotFoundException;
import kr.co.projecta.matching.security.Password;
import kr.co.projecta.matching.user.Identity;

@Controller
public class LoginController extends BaseController {
	
	/**
	 * 로그인 예외 처리 포함
	 * @param request
	 * @param dao
	 * @param target
	 * @param id
	 * @param password
	 * @return
	 */
	protected Responser loginR(
			HttpServletRequest request, 
			DAO<? extends Identity> dao, 
			String target, 
			String id, 
			String password)
	{
		HttpSession session = request.getSession();
		Responser responser = new Responser();
		try {
			id = request.getParameter(id);
			password = request.getParameter(password);
			
			checkMustNotNull(id, password);
			id = getCleanSecurity(session, id);
			password = getCleanSecurity(session, password);
			
			Identity identity = dao.selectOne("id", id);
			if (identity == null) {
				throw new UserNotFoundException(
						String.format("[%s] not found %s id.", id, target));
			}
			if (!Password.hash(password).equals(identity.getPassword())) {
				throw new AuthenticationException(
						String.format("[%s] mismatch %s password.", id, target));
			}			
			saveSession(session, target, identity);
		} catch (NullPointerException e) {
			responser = new Responser(CODE.PARAMETERS_ERROR, true);
		} catch (NotAccessableException e) {
			responser = new Responser(CODE.NOT_ACCESSABLE, true);
		} catch (RSAException e) {
			responser = new Responser(CODE.SERVER_ERROR, true);
		} catch (NotNullException e) {
			responser = new Responser(CODE.PARAMETERS_ERROR, true);
		} catch (UserNotFoundException e) {
			responser = new Responser(CODE.USER_NOT_FOUND, true);
		} catch (AuthenticationException e) {
			responser = new Responser(CODE.FAILURE_AUTHENTICATION, true);
		}
		return responser;
	}
	
	/**
	 * 관리자 로그인
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login/admin_r.do")
	@ResponseBody
	public Responser loginAdminR(HttpServletRequest request) {
		return loginR(request, adminDAO, "admin", "adminId", "adminPassword");
	}
	
	/**
	 * 구직자 로그인
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login/seeker_r.do")
	@ResponseBody
	public Responser loginSeekerR(HttpServletRequest request) {
		return loginR(request, seekerDAO, "seeker", "seekerId", "seekerPassword");
	}
	
	/**
	 * 업주 로그인
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login/offerer_r.do")
	@ResponseBody
	public Responser loginOffererR(HttpServletRequest request) {
		return loginR(request, offererDAO, "offerer", "offererId", "offererPassword");
	}
}
