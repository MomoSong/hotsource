package kr.co.hotsource.qboard;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class QboardCont {
	
	@Autowired
	QboardDAO dao;
	
	public QboardCont() {
		System.out.println("----QboardCont() 객체 생성");
	}
	
	//hotsource Issue 
	//http://localhost:9090/hotsource/project/issue/create.do
	
	@RequestMapping(value="/qboard/create.do", method=RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("qboard/createForm");
	    mav.addObject("root", Utility.getRoot()); 
		return mav; // issue/createForm.jsp
	} // createForm() end
	
	@RequestMapping(value="/qboard/create.do", method=RequestMethod.POST)
	public ModelAndView createProc(QboardDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("qboard/msgView");
		mav.addObject("root", Utility.getRoot());
		//------------------------------------------------------------    
		//  
		    String basePath = req.getRealPath("/qboard/q_storage");
		    
		    //2)<input type='file' name='comfile'>
		    MultipartFile comfile = dto.getComfile();
		    String filename = UploadSaveManager.saveFileSpring30(comfile, basePath);
		    dto.setFilename(filename);         
		    dto.setFilesize(comfile.getSize()); 
		//------------------------------------------------------------
		
		int cnt = dao.create(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>qboard 게시글 등록 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
		} else {
			mav.addObject("msg1", "<p>qboard 게시글 등록 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='계속등록' onclick='location.href=\"./create.do\"'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
		}
		
		return mav;
	} // createProc() end
	
	
	@RequestMapping("/qboard/list.do")
	public ModelAndView list(@RequestParam Map map) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("qboard/list");
		mav.addObject("root",Utility.getRoot()); // /hotsource
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
	
	@RequestMapping(value="/qboard/read.do", method={RequestMethod.GET, RequestMethod.POST})
	  public ModelAndView read(int boardno) { 
	                                          //HttpServletRequest req
	    ModelAndView mav = new ModelAndView();
	    QboardDTO dto = dao.read(boardno);
	    
	    mav.addObject("reply",dao.reply(boardno));
	    mav.addObject("readcnt",dao.readcntup(boardno));

	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dto);
	    
	    return mav;
	  }//read() end
	
	@RequestMapping(value="/qboard/delete.do", method=RequestMethod.GET)  
	  public ModelAndView deleteForm(QboardDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("qboard/deleteForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getBoardno()));
	    return mav;
	  }//deleteForm() end
	  

	  @RequestMapping(value="/qboard/delete.do", method=RequestMethod.POST)
	  public ModelAndView deleteProc(QboardDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("qboard/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    
	    QboardDTO oldDTO = dao.read(dto.getBoardno());
	    
	    int cnt = dao.delete(dto.getBoardno());
	    if(cnt==0) {
			mav.addObject("msg1", "<p>qboard 게시글 삭제 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
		} else {
		
		    String basepath = req.getRealPath("/qboard/b_storage");
		    UploadSaveManager.deleteFile(basepath, oldDTO.getFilename());
			mav.addObject("msg1", "<p>qboard 게시글 삭제 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='새로등록' onclick='location.href=\"./create.do\"'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./list.do\"'>");
		}    
	    return mav;
	  }//deleteProc() end
	  
	  @RequestMapping(value="/qboard/update.do", method=RequestMethod.GET)  
	  public ModelAndView updateForm(QboardDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("qboard/updateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getBoardno()));
	    return mav;
	  }//updateForm() end
	  
	  
	  @RequestMapping(value="/qboard/update.do", method=RequestMethod.POST)  
	  public ModelAndView updateProc(QboardDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("qboard/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    String basePath = req.getRealPath("/qboard/q_storage");
	    
	    
	    QboardDTO oldDTO = dao.read(dto.getBoardno());
	//---------------------------------------------------------    
	//  
	    MultipartFile comfile = dto.getComfile();
	    if(comfile.getSize()>0) { 
	      
	      UploadSaveManager.deleteFile(basePath, oldDTO.getFilename());      
	      
	      String filename = UploadSaveManager.saveFileSpring30(comfile, basePath);
	      dto.setFilename(filename);
	      dto.setFilesize(comfile.getSize());
	    }else { 
	      dto.setFilename(oldDTO.getFilename());
	      dto.setFilesize(oldDTO.getFilesize());
	    }//if end
	//---------------------------------------------------------    
	    int cnt = dao.update(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>qboard 게시글 수정 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='그룹목록' onclick='location.href=\"./list.do\"'>");
		} else {
			mav.addObject("msg1", "<p>qboard 게시글 수정 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='그룹목록' onclick='location.href=\"./list.do\"'>");
		}
	    
	    return mav;
	  }//updateProc() end
	  
	  @RequestMapping(value="/qboard/recommend.do", method=RequestMethod.POST)
	  public void recommend(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	        String s_boardno = req.getParameter("boardno");   
	        //System.out.println(s_icno);
	        int boardno = Integer.parseInt(s_boardno);
	        String message = "";
	          int cnt = dao.recommend(boardno);
	          if(cnt==0) {
	            message = "추천 실패";
	          }else {
	            message = "게시글을 추천 하였습니다";
	          }			           
	        
	        resp.setContentType("text/plain; charset=UTF-8");
	        PrintWriter out = resp.getWriter();
	        out.println(message);             
	        out.flush();
	        out.close();
	        
	      }catch (Exception e) {
	        System.out.println("실패 : " + e);
	      }
	  }//recommend() end
	  
	  @RequestMapping(value="/qboard/replycreate.do", method={RequestMethod.GET, RequestMethod.POST})
	  public void replycreate(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	    	ReplyDTO dto = new ReplyDTO();
	    	
	    	
	        String s_boardno = req.getParameter("boardno");   
	        int boardno = Integer.parseInt(s_boardno);
	        String id = req.getParameter("id");
	        String content = req.getParameter("content");
	        
	        
	        dto.setBoardno(boardno);
	        dto.setId(id);
	        dto.setContent(content);
	        
	        String message = "";
	          int cnt = dao.replycreate(dto);
	          if(cnt==0) {
	            message = "댓글 작성 실패";
	          }else {
	            message = "댓글 작성 성공";
	            dao.replycntup(boardno);
	          }			           
	        
	        resp.setContentType("text/plain; charset=UTF-8");
	        PrintWriter out = resp.getWriter(); 
	        out.println(message);               
	        out.flush();
	        out.close();
	        
	      }catch (Exception e) {
	        System.out.println("실패 : " + e);
	      }
	  }//replycreate() end
	  
	  @RequestMapping(value="/qboard/replydelete.do", method=RequestMethod.POST)
	  public void replydelete(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	    	
	        String s_rno = req.getParameter("rno");   
	        //System.out.println(s_icrno);
	        int rno = Integer.parseInt(s_rno); 
	        
	        String s_boardno = req.getParameter("boardno");   
	        //System.out.println(s_icno);
	        int boardno = Integer.parseInt(s_boardno); 
	        
	        String message = "";
	          int cnt = dao.replydelete(rno);
	          if(cnt==0) {
	            message = "댓글 삭제 실패";
	          }else {
	            message = "댓글 삭제 성공";
	            dao.replycntdown(boardno);
	          }			           
	        
	        resp.setContentType("text/plain; charset=UTF-8");
	        PrintWriter out = resp.getWriter(); 
	        out.println(message);               
	        out.flush();
	        out.close();
	        
	      }catch (Exception e) {
	        System.out.println("실패 : " + e);
	      }
	  }//replydelete() end
	  
	  @RequestMapping(value="/qboard/replyupdate.do", method=RequestMethod.GET)  
	  public ModelAndView replyupdate(int rno) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("board/reupdateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.replyread(rno));
	    return mav;
	  }//replyupdate() end
	  	  
	  @RequestMapping(value="/qboard/replyupdate.do", method=RequestMethod.POST)  
	  public ModelAndView replyupdateProc(ReplyDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("board/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    int cnt = dao.replyupdate(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>qboard 댓글 수정 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='게시글로 이동' onclick='opener.parent.location.reload(); window.close();'>");
		} else {
			mav.addObject("msg1", "<p>qboard 댓글 수정 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='게시글로 이동' onclick='opener.parent.location.reload(); window.close();'>");
		}
	    
	    return mav;
	  }//updateProc() end
		  	
} // class end
