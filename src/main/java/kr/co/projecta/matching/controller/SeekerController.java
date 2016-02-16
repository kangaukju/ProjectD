package kr.co.projecta.matching.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
