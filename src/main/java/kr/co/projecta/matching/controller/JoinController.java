package kr.co.projecta.matching.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.security.Password;
import kr.co.projecta.matching.user.Admin;
import kr.co.projecta.matching.user.Offerer;
import kr.co.projecta.matching.user.Seeker;
import kr.co.projecta.matching.user.types.Gender;
import kr.co.projecta.matching.user.types.Nation;
import kr.co.projecta.matching.user.types.Region;
import kr.co.projecta.matching.user.types.WorkAbility;
import kr.co.projecta.matching.user.types.WorkMday;
import kr.co.projecta.matching.user.types.WorkQtime;
import kr.co.projecta.matching.util.Numbers;
import kr.co.projecta.matching.util.Times;

@Controller
public class JoinController extends BaseController {
	
	/**
	 * 관리자 회원가입 화면
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/join/admin.do")
	public ModelAndView joinAdmin(
			ModelAndView mv, 
			HttpServletRequest request) 
	{		
		generateRSAKeyPair(mv, request.getSession());
		return mv;
	}
	
	/**
	 * 업주 회원가입 화면
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/join/offerer.do")
	public ModelAndView joinOfferer(
			ModelAndView mv, 
			HttpServletRequest request) 
	{		
		generateRSAKeyPair(mv, request.getSession());
		return mv;
	}
	
	/**
	 * 구직자 회원가입 화면
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/join/seeker.do")
	public ModelAndView joinSeeker(
			ModelAndView mv,
			HttpServletRequest request) 
	{
		generateRSAKeyPair(mv, request.getSession());
		return mv;
	}
	
	/**
	 * 관리자 회원가입 처리
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/join/admin_r.do")
	@ResponseBody
	public Responser joinAdminR(HttpServletRequest request) {
		return proxy(new Callback() {
			public void tryCatchRun() throws Exception {
				Admin admin = new Admin();
				
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				
				checkMustNotNull(id, name, password);
				
				id = getCleanSecurity(request.getSession(), id);
				name = getCleanSecurity(request.getSession(), name);
				password = getCleanSecurity(request.getSession(), password);
				
				admin.setId(id);
				admin.setName(name);
				admin.setPassword(Password.hash(password));
				
				adminDAO.join(admin);
			}
		});
	}
	
	/**
	 * 업주 회원가입 처리
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/join/offerer_r.do")
	@ResponseBody
	public Responser joinOffererR(HttpServletRequest request) {
		return proxy(new Callback() {
			public void tryCatchRun() throws Exception {
				Offerer offerer = new Offerer();
				
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				String offererName = request.getParameter("offererName");
				String offererNumber = request.getParameter("offererNumber");
				String offererBrief = request.getParameter("offererBrief");
				String phone = request.getParameter("phone");
				String cellPhone = request.getParameter("cellPhone");
				String sidoId = request.getParameter("sidoId");
				String sigunguId = request.getParameter("sigunguId");
				String postcode = request.getParameter("postcode");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				
				checkMustNotNull(id, name, password, offererName, offererNumber, phone, cellPhone, sidoId, sigunguId, postcode, address1);
				
				id = getCleanSecurity(request.getSession(), id);
				name = getCleanSecurity(request.getSession(), name);
				password = getCleanSecurity(request.getSession(), password);
				
				offerer.setId(id);
				offerer.setName(name);
				offerer.setPassword(Password.hash(password));
				offerer.setOffererName(offererName);
				offerer.setOffererNumber(offererNumber.replaceAll("[ .\t-]", ""));
				offerer.setPhone(phone);
				offerer.setCellPhone(cellPhone);
				offerer.setOffererBrief(offererBrief);
				offerer.setSidoId(Integer.valueOf(sidoId));
				offerer.setSigunguId(Integer.valueOf(sigunguId));
				offerer.setPostcode(postcode);
				offerer.setAddress1(address1);
				offerer.setAddress2(address2);
				offerer.setMapFilename(downloadMapFile(offerer.getId(), offerer.getAddress1()));
				
				offererDAO.join(offerer);
			}
		});
	}
	
	/**
	 * 구직자 회원가입 처리
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/join/seeker_r.do")
	@ResponseBody
	public Responser joinSeekerR(HttpServletRequest request) {
		return proxy(new Callback() {
			public void tryCatchRun() throws Exception {
				Seeker seeker = new Seeker();
				
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				String gender = request.getParameter("gender");
				String years = request.getParameter("years");
				String []mdays = request.getParameterValues("mday");
				String nation = request.getParameter("nation");
				String []qtimes = request.getParameterValues("qtime");
				String region1 = request.getParameter("region1");
				String region2 = request.getParameter("region2");
				String region3 = request.getParameter("region3");
				String workAbility = request.getParameter("workAbility");
				
				checkMustNotNull(id, name, password, gender, years, mdays, nation, qtimes, region1, region2, region3, workAbility);
				
				id = getCleanSecurity(request.getSession(), id);
				name = getCleanSecurity(request.getSession(), name);
				password = getCleanSecurity(request.getSession(), password);
				
				seeker.setId(id);
				seeker.setName(name);
				seeker.setPassword(Password.hash(password));
				seeker.setGender(Gender.valueOf(gender).getGender());
				seeker.setBirth(Times.getDateYYYY(years));
				seeker.setWorkMday(WorkMday.valueOf(mdays).getWorkMday());
				seeker.setNation(Nation.valueOf(nation).getNation());
				seeker.setWorkQtime(WorkQtime.valueOf(qtimes).getWorkQtime());
				seeker.setWorkAbility(WorkAbility.valueOf(workAbility).getWorkAbility());
				List<Region> regions = new ArrayList<Region>();
				if (	"0".equals(region1) || 
						"0".equals(region2) || 
						"0".equals(region3)) {
					// XXX: region id가 0이면 '전지역'을 뜻한다.
					regions.add(new Region(0));
					regions.add(new Region(0));
					regions.add(new Region(0));
				} else {
					regions.add(new Region(Integer.valueOf(region1)));
					regions.add(new Region(Integer.valueOf(region2)));
					regions.add(new Region(Integer.valueOf(region3)));
				}
				seeker.setRegions(regions);
				
				seekerDAO.join(seeker);
			}
		});
	}
}
