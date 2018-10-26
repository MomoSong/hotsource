package kr.co.hotsource.follow;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hotsource.commit.CommitDAO;
import kr.co.hotsource.issue.ReplyDTO;
import kr.co.hotsource.member.MemberDTO;
import net.utility.Utility;

@Controller
public class FollowCont {
  
  @Autowired
  FollowDAO dao;
  
  public FollowCont() {
    System.out.println("----FollowCont() 객체 생성");
  }
  
  @RequestMapping(value="/follow/follow.do", method=RequestMethod.GET)  
  public ModelAndView follow(String flw, HttpServletRequest req) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("follow/follow");
    mav.addObject("root", Utility.getRoot());
    mav.addObject("flw", flw);
    return mav;
  }//follow() end
      
  @RequestMapping(value="/follow/follow.do", method=RequestMethod.POST)  
  public ModelAndView followProc(FollowDTO dto, HttpServletRequest req) {
    
    ModelAndView mav = new ModelAndView();

    dto.setFlw(req.getParameter("flw"));
    dto.setFlwr(req.getParameter("flwr"));
    
    mav.setViewName("follow/msgView");
    mav.addObject("root", Utility.getRoot());
    
    System.out.println(dto.getFlw());
    System.out.println(dto.getFlwr());
    
    int cnt = dao.follow(dto);
  if(cnt==0) {
    mav.addObject("msg1", "<p>follow 실패</p>");
    mav.addObject("img", "<img src='../resources/images/fail.png'>");
    mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
    mav.addObject("link2", "<input type='button' value='게시글로 이동' onclick='opener.parent.location.reload(); window.close();'>");
  } else {
    mav.addObject("msg1", "<p>follow 성공</p>");
    mav.addObject("img", "<img src='../resources/images/success.png'>");
    mav.addObject("link1", "<input type='button' value='게시글로 이동' onclick='opener.parent.location.reload(); window.close();'>");
  }
    
    return mav;
  }//updateProc() end

  @RequestMapping(value="/follow/myfollow.do",method=RequestMethod.GET)
  public ModelAndView myfollow(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    System.out.println(session.getAttribute("s_id"));
    List list = dao.myfollow((String)session.getAttribute("s_id"));
    mav.setViewName("follow/myfollow");
    mav.addObject("root",Utility.getRoot());
    mav.addObject("list", list);
    return mav;
  }//my end
  
  @RequestMapping(value="/follow/followpjt.do")  
  public ModelAndView followpjt(@RequestParam Map map, HttpServletRequest req, String flw)throws Exception {
    ModelAndView mav = new ModelAndView();
    List list = dao.list(flw);
    mav.setViewName("follow/followpjt");
    mav.addObject("root", Utility.getRoot());
    mav.addObject("flw", flw);
    mav.addObject("list",list);
    return mav;
  }//followpjt() end
  
  
  
}//class end



