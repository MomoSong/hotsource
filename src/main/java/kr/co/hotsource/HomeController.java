package kr.co.hotsource;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hotsource.board.boardDAO;
import kr.co.hotsource.notice.NoticeDAO;
import kr.co.hotsource.pjt.PjtDAO;
import net.utility.Utility;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	boardDAO dao;
	
	@Autowired
	NoticeDAO daoN;
	
	@Autowired
	PjtDAO daoP;
	
	public HomeController() {
		System.out.println("HomeController °´Ã¼ »ý¼º");
	}

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/index.do")
	public ModelAndView list(HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/index");
		mav.addObject("root",Utility.getRoot()); // /hotsource
		
		List list=null;      
	    list=dao.best();
	    mav.addObject("best", list);
	    
	    list=daoP.bestproject();
	    mav.addObject("bestproject", list);
	    
	    list=daoN.bestfaq();
	    mav.addObject("bestfaq", list);
	    
	    return mav;
	}
}
