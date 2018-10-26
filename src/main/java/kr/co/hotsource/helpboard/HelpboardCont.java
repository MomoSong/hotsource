package kr.co.hotsource.helpboard;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.utility.UploadSaveManager;
import net.utility.Utility;

@Controller
public class HelpboardCont {
	
	@Autowired
	HelpboardDAO dao;
	
	public HelpboardCont() {
		System.out.println("----HelpboardCont() 객체 생성");
	}
	
	//hotsource Helpboard 게시판 첫 페이지 호출 
	//http://localhost:9090/hotsource/helpboard/create.do
	
	@RequestMapping(value="/helpboard/create.do", method=RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("helpboard/createForm");
	    mav.addObject("root", Utility.getRoot()); 
		return mav; // helpboard/createForm.jsp
	} // createForm() end
	
	@RequestMapping(value="/helpboard/create.do", method=RequestMethod.POST)
	public ModelAndView createProc(HelpboardDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("helpboard/msgView");
		mav.addObject("root", Utility.getRoot());
		//------------------------------------------------------------    
		//  전송된 파일이 저장되는 실제 경로
		    String basePath = req.getRealPath("/helpboard/help_storage");
		    
		    //2)<input type='file' name='file'>
		    MultipartFile file = dto.getFile();
		    String filename = UploadSaveManager.saveFileSpring30(file, basePath);
		    dto.setFilename(filename);          //파일명
		    dto.setFilesize(file.getSize()); //파일크기
		//------------------------------------------------------------
		    
		int cnt = dao.create(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>helpboard 게시글 등록 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
		} else {
			mav.addObject("msg1", "<p>helpboard 게시글 등록 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='계속등록' onclick='location.href=\"./create.do\"'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
		}
		
		return mav;
	} // createProc() end
	

	@RequestMapping("/helpboard/list.do")
	public ModelAndView list(@RequestParam Map map) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("helpboard/list");
		mav.addObject("root",Utility.getRoot()); // /hotsource
		String strSearchtype = (String)map.get("searchtype");
		String strSearch = (String)map.get("search");
		System.out.println("strSearchtypt = " +strSearchtype);
		System.out.println("strSearch = " + strSearch);
		
		//총 게시글 수
	    int total_cnt = 0;
	    total_cnt=dao.getArticleCount();
	    
	    //페이징
	    int numPerPage=10;       // 한 페이지당 레코드 갯수
	    int pagePerBlock = 10;  // 페이지 리스트
	  
	    String pageNum=(String)map.get("pageNum");
	    if(pageNum==null){
	        pageNum="1";
	    }
	    
	    int currentPage=Integer.parseInt(pageNum);
	    
	    // 등차수열 a+(n-1)d
	    //int startRow = ((total_cnt-numPerPage)+(currentPage-1)*(-numPerPage))+1;
	    //int endRow = total_cnt+(currentPage-1)*(-numPerPage);
	    
	    int startRow=(currentPage-1)*numPerPage+1;
	    int endRow=currentPage*numPerPage;
	    
	    
	    //페이지수
	    double totcnt = (double)total_cnt/numPerPage;
	    int totalPage = (int)Math.ceil(totcnt);
	    
	    double d_page = (double)currentPage/pagePerBlock;
	    int Pages = (int)Math.ceil(d_page)-1;
	    int startPage = Pages*pagePerBlock;
	    int endPage = startPage+pagePerBlock+1;
	    
	    
	    List list=null;      
	    if(total_cnt>0){
	      
	        list=dao.list(startRow, endRow, strSearchtype, strSearch);
	        
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

	@RequestMapping(value="/helpboard/read.do", method={RequestMethod.GET, RequestMethod.POST})
	  public ModelAndView read(int helpno) { 
	                                          //HttpServletRequest req
	    ModelAndView mav = new ModelAndView();
	    HelpboardDTO dto = dao.read(helpno);
	    
	    mav.addObject("readcnt",dao.readcntup(helpno));

	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dto);
	    
	    return mav;
	  }//read() end
	
	@RequestMapping(value="/helpboard/hbpsw.do", method=RequestMethod.GET)  
	  public ModelAndView hbpsw(int helpno) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("helpboard/hbpswForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("helpno", helpno);
	    return mav;
	  }//hbpsw() end
	  	  
	  @RequestMapping(value="/helpboard/hbpsw.do", method=RequestMethod.POST)  
	  public ModelAndView hbpswProc(HelpboardDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("helpboard/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    int cnt = dao.hbpsw(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>비밀번호가 틀립니다</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='목록으로 이동' onclick='opener.parent.location.reload(); window.close();'>");
		} else {
			mav.addObject("msg1", "<p>비밀번호 일치</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='게시글로 이동' "
						+ "onclick='opener.parent.location.href=\"./read.do?helpno="+dto.getHelpno()+"\"; window.close();'>");
		}
	    
	    return mav;
	  }//hbpswProc() end
	  
	  @RequestMapping(value="/helpboard/answer.do", method=RequestMethod.GET)
		public ModelAndView answerForm(HttpServletRequest req) {
		  	String s_helpno = req.getParameter("helpno");   
	        //System.out.println(s_helpno);
	        int helpno = Integer.parseInt(s_helpno);
		  
			ModelAndView mav = new ModelAndView();
			mav.setViewName("helpboard/answerForm");
		    mav.addObject("root", Utility.getRoot());
		    mav.addObject("passwd", dao.getPsw(helpno));
		    mav.addObject("helpno",helpno);
			return mav; // helpboard/createForm.jsp
		} // answerForm() end
		
		@RequestMapping(value="/helpboard/answer.do", method=RequestMethod.POST)
		public ModelAndView answerProc(HelpboardDTO dto, HttpServletRequest req) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("helpboard/msgView");
			mav.addObject("root", Utility.getRoot());
			//------------------------------------------------------------    
			//  전송된 파일이 저장되는 실제 경로
			    String basePath = req.getRealPath("/helpboard/help_storage");
			    
			    //2)<input type='file' name='file'>
			    MultipartFile file = dto.getFile();
			    String filename = UploadSaveManager.saveFileSpring30(file, basePath);
			    dto.setFilename(filename);          //파일명
			    dto.setFilesize(file.getSize()); //파일크기
			//------------------------------------------------------------
			    
			int cnt = dao.answer(dto);
			if(cnt==0) {
				mav.addObject("msg1", "<p>helpboard 답변 등록 실패</p>");
				mav.addObject("img", "<img src='../resources/images/fail.png'>");
				mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
				mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
			} else {
				mav.addObject("msg1", "<p>helpboard 답변 등록 성공</p>");
				mav.addObject("img", "<img src='../resources/images/success.png'>");
				mav.addObject("link1", "<input type='button' value='계속등록' onclick='location.href=\"./create.do\"'>");
				mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
			}
			
			return mav;
		} // answerProc() end
	  
	
	@RequestMapping(value="/helpboard/delete.do", method=RequestMethod.GET)  
	  public ModelAndView deleteForm(HelpboardDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("helpboard/deleteForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getHelpno()));//삭제관련정보
	    return mav;
	  }//deleteForm() end
	  

	  @RequestMapping(value="/helpboard/delete.do", method=RequestMethod.POST)
	  public ModelAndView deleteProc(HelpboardDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("helpboard/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    //테이블에 저장되어 있는 파일 정보 가져오기
	    HelpboardDTO oldDTO = dao.read(dto.getHelpno());
	    
	    int cnt = dao.delete(dto.getHelpno());
	    if(cnt==0) {
			mav.addObject("msg1", "<p>helpboard 게시글 삭제 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
		} else {
		//관련 파일 삭제
		    String basepath = req.getRealPath("/helpboard/help_storage");
		    UploadSaveManager.deleteFile(basepath, oldDTO.getFilename());
			mav.addObject("msg1", "<p>helpboard 게시글 삭제 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='새로등록' onclick='location.href=\"./create.do\"'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
		}    
	    return mav;
	  }//deleteProc() end
	  
	  @RequestMapping(value="/helpboard/update.do", method=RequestMethod.GET)  
	  public ModelAndView updateForm(HelpboardDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("helpboard/updateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getHelpno()));
	    return mav;
	  }//updateForm() end
	  
	  
	  @RequestMapping(value="/helpboard/update.do", method=RequestMethod.POST)  
	  public ModelAndView updateProc(HelpboardDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("helpboard/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    String basePath = req.getRealPath("/helpboard/help_storage");
	    
	    //기존에 저장된 정보 가져오기
	    HelpboardDTO oldDTO = dao.read(dto.getHelpno());
	//---------------------------------------------------------    
	//  파일을 수정할것인지?
	    MultipartFile file = dto.getFile();
	    if(file.getSize()>0) { // 업로드 파일이 전송된 경우
	      //기존 파일 삭제
	      UploadSaveManager.deleteFile(basePath, oldDTO.getFilename());      
	      //신규 파일 저장
	      String filename = UploadSaveManager.saveFileSpring30(file, basePath);
	      dto.setFilename(filename);
	      dto.setFilesize(file.getSize());
	    }else { //파일을 수정하지 않는 경우
	      dto.setFilename(oldDTO.getFilename());
	      dto.setFilesize(oldDTO.getFilesize());
	    }//if end
	//---------------------------------------------------------    
	    int cnt = dao.update(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>helpboard 게시글 수정 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='그룹목록' onclick='location.href=\"./list.do\"'>");
		} else {
			mav.addObject("msg1", "<p>helpboard 게시글 수정 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='그룹목록' onclick='location.href=\"./list.do\"'>");
		}
	    
	    return mav;
	  }//updateProc() end

} // class end
