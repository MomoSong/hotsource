package kr.co.hotsource.master;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hotsource.member.MemberDTO;
import net.utility.Utility;

@Controller
public class MasterCont {
	
	@Autowired
	MasterDAO dao;

	public MasterCont() {
		System.out.println("----MasterCont() 생성");
	} 
	
	@RequestMapping(value="/master/manage.do", method=RequestMethod.GET)
	public ModelAndView manage(HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("master/manage");
	    mav.addObject("root", Utility.getRoot()); 
	    
	    int total_cnt = 0;
	    total_cnt=dao.getArticleCount();
	    
	    
	    int numPerPage=10;       
	    int pagePerBlock = 10;  
	  
	    String pageNum=req.getParameter("pageNum");
	    if(pageNum==null){
	        pageNum="1";
	    }
	    
	    int currentPage=Integer.parseInt(pageNum);
	    
	    int startRow = ((total_cnt-numPerPage)+(currentPage-1)*(-numPerPage))+1;
	    int endRow = total_cnt+(currentPage-1)*(-numPerPage);
	    
	    //int startRow=(currentPage-1)*numPerPage+1;
	    //int endRow=currentPage*numPerPage;
	    
	    
	    
	    double totcnt = (double)total_cnt/numPerPage;
	    int totalPage = (int)Math.ceil(totcnt);
	    
	    double d_page = (double)currentPage/pagePerBlock;
	    int Pages = (int)Math.ceil(d_page)-1;
	    int startPage = Pages*pagePerBlock;
	    int endPage = startPage+pagePerBlock+1;
	    
	    
	    List list=null;      
	    if(total_cnt>0){
	      
	        list=dao.list(startRow, endRow);
	        
	    } else {
	      
	        list=Collections.emptyList();
	        
	    }//if end
	    
	    int number=0;
	    number=total_cnt-(currentPage-1)*numPerPage;
	    
	    mav.addObject("number",    new Integer(number));
	    mav.addObject("pageNum",   new Integer(currentPage));
	    mav.addObject("startRow",  new Integer(startRow));
	    mav.addObject("endRow",    new Integer(endRow));
	    mav.addObject("count",     new Integer(total_cnt));
	    mav.addObject("pageSize",  new Integer(pagePerBlock));
	    mav.addObject("totalPage", new Integer(totalPage));
	    mav.addObject("startPage", new Integer(startPage));
	    mav.addObject("endPage",   new Integer(endPage));
	    mav.addObject("list", list);
	    
		return mav; // 
	} // manage() end
	
	@RequestMapping(value="/master/ratingupdate.do", method=RequestMethod.GET)  
	  public ModelAndView ratingupdate(String id) throws Exception {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("master/ratingUpdate");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("rating", dao.ratingread(id));
	    mav.addObject("id",id);
	    return mav;
	  }//ratingupdate() end
	  	  
	  @RequestMapping(value="/master/ratingupdate.do", method=RequestMethod.POST)  
	  public ModelAndView ratingupdateProc(MemberDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("master/msgView");
	    mav.addObject("root", Utility.getRoot());
	    System.out.println(dto.getId());
	    System.out.println(dto.getRating());
	    
	    int cnt = dao.ratingupdate(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>등급 변경 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='게시글로 이동' onclick='opener.parent.location.reload(); window.close();'>");
		} else {
			mav.addObject("msg1", "<p>등급 변경 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='게시글로 이동' onclick='opener.parent.location.reload(); window.close();'>");
		}
	    
	    return mav;
	  }//ratingupdateProc() end
	  
	  @RequestMapping(value="/master/memberdelete.do", method=RequestMethod.GET)  
	  public ModelAndView memberdelete(String id) throws Exception {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("master/deleteForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("id",id);
	    return mav;
	  }//memberdelete() end
	  	  
	  @RequestMapping(value="/master/memberdelete.do", method=RequestMethod.POST)  
	  public ModelAndView memberdeleteProc(String id, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("master/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    int cnt = dao.memberdelete(id);
		if(cnt==0) {
			mav.addObject("msg1", "<p>회원 강퇴 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='게시글로 이동' onclick='opener.parent.location.reload(); window.close();'>");
		} else {
			mav.addObject("msg1", "<p>회원 강퇴 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='게시글로 이동' onclick='opener.parent.location.reload(); window.close();'>");
		}
	    
	    return mav;
	  }//memberdeleteProc() end
	
	
}
