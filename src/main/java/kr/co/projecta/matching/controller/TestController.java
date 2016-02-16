package kr.co.projecta.matching.controller;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.projecta.matching.dao.OffererDAO;
import kr.co.projecta.matching.dao.RequirementDAO;
import kr.co.projecta.matching.dao.SeekerDAO;

@Controller
public class TestController extends BaseController {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="SeekerDAO")
	SeekerDAO seekerDAO;
	
	@Resource(name="OffererDAO")
	OffererDAO offererDAO;
	
	@Resource(name="RequirementDAO")
	RequirementDAO requirementDAO;
	
	
	@RequestMapping(value="/test.do")
	public ModelAndView test(ModelAndView mv) {
		return mv;	
	}
}

