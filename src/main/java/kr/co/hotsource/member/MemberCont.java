package kr.co.hotsource.member;

import java.util.Collections;
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

import kr.co.hotsource.member.MemberDTO;
import kr.co.hotsource.pjt.PjtDAO;
import kr.co.hotsource.member.MemberDAO;
import net.utility.Utility;

@Controller
public class MemberCont {

  @Autowired
  MemberDAO dao;
  
  
  public MemberCont() {
    System.out.println("---MemberCont() 객체 생성됨...");
  }
  @RequestMapping(value="member/join.do", method=RequestMethod.GET)
  public ModelAndView memberform() {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("member/joinform");
    mav.addObject("root", Utility.getRoot());
      
    return mav;
  } //memberform() end
  
  @RequestMapping(value="member/join.do", method=RequestMethod.POST)
  public ModelAndView insertProc(HttpServletRequest req) {
    ModelAndView mav = new ModelAndView();
    
    MemberDTO dto = new MemberDTO();
    String birth = "";
    birth += req.getParameter("year");
    birth += req.getParameter("month");
    birth += req.getParameter("date");
    dto.setId(req.getParameter("id"));
    dto.setName(req.getParameter("name"));
    dto.setPasswd(req.getParameter("passwd"));
    dto.setBirth(birth);
    dto.setPh_no(req.getParameter("ph_no"));
    dto.setEmail(req.getParameter("email"));

    mav.setViewName("member/msgView");
    mav.addObject("root", Utility.getRoot());
      
    int cnt = dao.insert(dto);
    if(cnt==0) {
       mav.addObject("msg1", "<p>회원가입 실패하였습니다.</p>");
       mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
    }else {
      String script="";
      script+="<script>";
      script+=" alert('회원가입을 환영합니다.');";
      script+=" location.href='../index.do '";
      script+="</script>"; 

      mav.addObject("msg1", script);
    }
    return mav;
  } //insertProc() end
  
  @RequestMapping(value="member/duplicateID.do", method=RequestMethod.GET)
  public ModelAndView duplicateID(MemberDTO dto) {
    ModelAndView mav= new ModelAndView();
    
    mav.setViewName("member/idCheckForm");
    mav.addObject("root", Utility.getRoot());
    
    return mav;
  } //duplicateID() end
  
  @RequestMapping(value="member/duplicateID.do", method=RequestMethod.POST)
  public ModelAndView duplicateIDProc(MemberDTO dto) {
       ModelAndView mav = new ModelAndView();
       mav.setViewName("member/msgView");
       mav.addObject("root", Utility.getRoot());
       
       int cnt = dao.duplicateID(dto.getId());
       
       if(cnt==0) {
          mav.addObject("msg1", "<p>사용 가능한 아이디 입니다.</p>");
          mav.addObject("link1", "<input type='button' value='사용하기' onclick='javascript:apply(\"" + dto.getId() +"\")'>");
          mav.addObject("link2", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
          mav.addObject("link3", "<input type='button' value='취소' onclick='javascript:window.close()'>"); 
    }else {
          mav.addObject("msg1", "<p>이미 사용중인 아이디 입니다.</p>");
          mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
          mav.addObject("link2", "<input type='button' value='취소' onclick='javascript:window.close()'>");      
    }
    
    return mav;
  } //duplicateProc() end
  @RequestMapping(value="/member/login.do", method=RequestMethod.GET)
  public ModelAndView loginForm() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("member/loginForm");  
    return mav;   
  }//loginForm() end

  @RequestMapping(value="/member/login.do", method=RequestMethod.POST)
  public ModelAndView loginProc(String id, String passwd, HttpSession session){
    
    ModelAndView mav = new ModelAndView();
    mav.setViewName("member/msgView");
    String rating = dao.loginProc(id, passwd);    
    
    if(rating==null) {  //로그인 실패 시
      String script="";
      script+="<script>";
      script+=" alert('ID와 PASSWORD를 확인해주세요.');";
      script+=" location.href=\"./login.do\"";
      script+="</script>";

      mav.addObject("msg1", script);
    }else if(rating.equals("O")) {
      String script="";
      script+="<script>";
      script+=" alert('탈퇴 회원입니다. \n');";
      script+=" location.href=\"./login.do\"";
      script+="</script>";

      mav.addObject("msg1", script);
    }else {
      session.setAttribute("s_id", id);
      session.setAttribute("s_passwd", passwd);
      session.setAttribute("s_rating", rating);
      
      mav.setViewName("redirect:/index.do");
    }
    return mav;
  } //loginProc() end 
  
  @RequestMapping("member/logout.do")
  public ModelAndView logout(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    session.removeAttribute("s_id");
    session.removeAttribute("s_pw");
    session.removeAttribute("s_rating");
    
    mav.setViewName("redirect:/member/login.do");
    
    return mav;
  }//logout() end
  

  
  @RequestMapping(value="member/findIdpw.do", method=RequestMethod.GET)
  public ModelAndView findIdpw() {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("member/findIdpw");
    mav.addObject("root", Utility.getRoot());
      
    return mav;
  } //findIdpw() end  
  
  @RequestMapping(value="member/findIdpw.do", method=RequestMethod.POST)
  public ModelAndView findIdpwProc(MemberDTO dto, HttpServletRequest req) {
      ModelAndView mav = new ModelAndView();
      mav.addObject("root", Utility.getRoot());
            
      String birth = "";
      birth += req.getParameter("birthyy");
      birth += req.getParameter("birthmm");
      birth += req.getParameter("birthdd");

      dto.setId(req.getParameter("id").trim());
      dto.setName(req.getParameter("name").trim());
      dto.setBirth(birth);

      String id=dao.findIdpw(dto);
      dto.setId(id);
      
      if(id==null) {
        mav.addObject("msg1", "일치하는 아이디가 없습니다.");
        mav.addObject("link1", "<input type='button' value='다시 시도' onclick='javascript:history.back()'>");
        
        mav.setViewName("member/msgView");
      }else {
        mav.addObject("dto", dto);
        mav.addObject("id", id);
        mav.addObject("msg1", "당신의 아이디는 "+id+"입니다.");
        mav.addObject("link1", "<input type='submit' method='get' value='비밀번호 변경' onclick='location.href=\"./pwReset.do?id="+ id +"\"'>");
          mav.setViewName("member/msgView");
      }   
      return mav;
    } //findIdpw() end
  
  @RequestMapping(value="member/pwReset.do", method=RequestMethod.GET)
  public ModelAndView pwreset(MemberDTO dto) {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("member/pwReset");
    mav.addObject("root", Utility.getRoot());
    mav.addObject("id", dto.getId());
      
    return mav;
  } //pwReset() end
  
  @RequestMapping(value="member/pwReset.do", method=RequestMethod.POST)
  public ModelAndView pwReset(MemberDTO dto, HttpServletRequest req) {
      ModelAndView mav = new ModelAndView();
      mav.addObject("root", Utility.getRoot());
      
      dto.setPasswd(req.getParameter("passwd"));
      
      int cnt = dao.pwReset(dto);
      System.out.println(cnt);            
      if(cnt==0) {
        mav.addObject("msg1", "비밀번호 변경을 실패했습니다..");
        mav.addObject("link1", "<input type='button' value='다시 시도' onclick='javascript:history.back()'>");
        
        mav.setViewName("member/msgView");
      }else {
        String script="";
              script+="<script>";
              script+=" alert('비밀번호가 변경되었습니다.');";
              script+=" location.href='./login.do'";
              script+="</script>";
              mav.addObject("msg1", script);
              mav.setViewName("member/msgView");
      }   
      return mav;
    } //pwReset() end

//http://localhost:9090/radesk/member/myinfo.do
  @RequestMapping(value="member/myinfo.do", method=RequestMethod.GET)
  public ModelAndView read(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    MemberDTO dto = dao.read((String)session.getAttribute("s_id"));
    mav.setViewName("member/myinfo");
    mav.addObject("root", Utility.getRoot());
    mav.addObject("dto", dto);
    
    return mav;
  } //read() end
  
  @RequestMapping(value="member/myinfoUpdate.do", method=RequestMethod.GET)
  public ModelAndView myinfoUpdate() {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("member/myinfoUpdate");
    mav.addObject("root", Utility.getRoot());
      
    return mav;
  } //myinfoUpdate() end
  
  @RequestMapping(value="member/myinfoUpdate.do", method=RequestMethod.POST)
  public ModelAndView update(MemberDTO dto, String passwd, HttpSession session) {
      ModelAndView mav = new ModelAndView();
      dto.setId((String)session.getAttribute("s_id"));
      dto.setPasswd(passwd);

      dto=dao.select(dto);
      if(dto==null) {
        mav.addObject("msg1", "비밀번호가 맞지않습니다.");
        mav.addObject("link1", "<input type='button' value='다시 시도' onclick='javascript:history.back()'>");
        
        mav.setViewName("member/msgView");
      }else {
          mav.setViewName("member/myinfoUpdateForm"); 
          mav.addObject("root", Utility.getRoot());
          mav.addObject("dto", dto);
      }   
      return mav;
    } //update() end
  
  @RequestMapping(value="member/myinfoUpdateProc.do", method=RequestMethod.POST)
  public ModelAndView updateProc(HttpServletRequest req, HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    MemberDTO dto = new MemberDTO();
    String birth = "";
    birth += req.getParameter("year");
    birth += req.getParameter("month");
    birth += req.getParameter("date");
    dto.setName(req.getParameter("name"));
    dto.setPasswd(req.getParameter("passwd"));
    dto.setBirth(birth);
    dto.setPh_no(req.getParameter("ph_no"));
    dto.setEmail(req.getParameter("email"));
    
    mav.setViewName("member/msgView");
    mav.addObject("root", Utility.getRoot());
    
    dto.setId((String)session.getAttribute("s_id"));
    
    int cnt = dao.update(dto);
    
    if(cnt == 0) {
          mav.addObject("msg1", "회원정보 수정 실패");
          mav.addObject("img",  "<img src='../images/fail.png'>");
          mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
          mav.addObject("link2", "<input type='button' value='취소' onclick='location.href=\"./myinfo.do\"'>");
        }
        else {
          String script="";
          script+="<script>";
          script+="   alert('회원정보가 수정 되었습니다.');";
          script+="   location.href=\"../index.do\"";
          script+="</script>";
          mav.addObject("msg1", script);
        }   
    return mav; 
  } //end updateProc()
  
  //http://localhost:9090/radesk/member/myinfoUpdateForm.do
  @RequestMapping(value="member/myinfoUpdateForm.do", method=RequestMethod.GET)
  public ModelAndView updateForm(MemberDTO dto) {
    ModelAndView mav = new ModelAndView();
    dto = dao.read(dto.getId());
    
    mav.setViewName("member/myinfoUpdateForm"); 
    mav.addObject("root", Utility.getRoot());
      mav.addObject("dto", dto);
    
    return mav;
  } //updateForm() end
  
  @RequestMapping(value="member/withdraw.do", method=RequestMethod.GET)
  public ModelAndView withdraw() {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("member/withdraw");
    mav.addObject("root", Utility.getRoot());
      
    return mav;
  } //withdraw() end
  
  @RequestMapping(value="member/withdraw.do", method=RequestMethod.POST)
  public ModelAndView withdraw(MemberDTO dto, String passwd, HttpSession session) {
      ModelAndView mav = new ModelAndView();
      mav.addObject("root", Utility.getRoot());
      dto.setId((String)session.getAttribute("s_id"));
      dto.setPasswd(passwd);

      dto=dao.select(dto);
      if(dto==null) {
        mav.addObject("msg1", "비밀번호가 맞지않습니다.");
        mav.addObject("link1", "<input type='button' value='다시 시도' onclick='javascript:history.back()'>");
        
        mav.setViewName("member/msgView");
      }else {
          mav.setViewName("member/withdrawProc"); 
          
          mav.addObject("dto", dto);
      }   
      return mav;
    } //withdraw() end
  
  @RequestMapping(value="member/withdrawProc.do", method=RequestMethod.POST)
  public ModelAndView withdrawProc(MemberDTO dto, HttpSession session) {
    ModelAndView mav = new ModelAndView();
        
    mav.setViewName("member/msgView");
    mav.addObject("root", Utility.getRoot());
    
    dto.setId((String)session.getAttribute("s_id"));
    
    int cnt = dao.withdraw(dto);
    
    if(cnt == 0) {
      mav.addObject("msg1", "탈퇴에 실패하였습니다.");
        mav.addObject("img",  "<img src='../images/fail.png'>");
        mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
        mav.addObject("link2", "<input type='button' value='취소' onclick='location.href=\"./myinfo.do\"'>");
    }else {
        session.removeAttribute("s_id");
      session.removeAttribute("s_passwd");
      session.removeAttribute("s_rating");
      String script="";
            script+="<script>";
            script+=" alert('탈퇴가 완료되었습니다.');";
            script+=" location.href='../index.do'";
            script+="</script>";
            mav.addObject("msg1", script);              
    }   
    return mav; 
    
  } //end withdrawProc()
  
  
  @RequestMapping("/mylist/mylist.do")
	public ModelAndView list(@RequestParam Map map , String s_id, HttpServletRequest req,HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		String s_id2 = (String)session.getAttribute("s_id");
		mav.setViewName("member/list");
		mav.addObject("root",Utility.getRoot()); // /hotsource
		mav.addObject("s_id", s_id);
		System.out.println("s_id2   : "+ s_id2);
		
		
		String strSearchtype = (String)map.get("searchtype");
		String strSearch = (String)map.get("search");
		System.out.println("strSearchtypt = " +strSearchtype);
		System.out.println("strSearch = " + strSearch);
		
	    int total_cnt = 0;
	    total_cnt=dao.getArticleCount();
	    
	  
	    int numPerPage=10;       
	    int pagePerBlock = 10;  
	  
	    String pageNum=(String)map.get("pageNum");
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
	      
	        list=dao.list(startRow, endRow, strSearchtype, strSearch, s_id2);
	        System.out.println("여기야  :"+list.get(0));
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
		
		
		return mav;
	} // list() end
  
  


} //class end
