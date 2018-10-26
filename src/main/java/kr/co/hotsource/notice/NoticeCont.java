package kr.co.hotsource.notice;

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
public class NoticeCont {
	
	@Autowired
	NoticeDAO dao;
	
	public NoticeCont() {
		System.out.println("----NoticeCont() ��ü ����");
	}
	
	//hotsource notice �Խ��� ù ������ ȣ�� 
	//http://localhost:9090/hotsource/notice/create.do
	
	
	
	@RequestMapping(value="/notice/create.do", method=RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("notice/createForm");
	    mav.addObject("root", Utility.getRoot()); 
		return mav; // notice/createForm.jsp
	} // createForm() end
	
	@RequestMapping(value="/notice/create.do", method=RequestMethod.POST)
	public ModelAndView createProc(NoticeDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("notice/msgView");
		mav.addObject("root", Utility.getRoot());
		//------------------------------------------------------------    
		//  ���۵� ������ ����Ǵ� ���� ���
		    String basePath = req.getRealPath("/notice/n_storage");
		    
		    //2)<input type='file' name='file'>
		    MultipartFile file = dto.getFile();
		    String filename = UploadSaveManager.saveFileSpring30(file, basePath);
		    dto.setFilename(filename);          //���ϸ�
		    dto.setFilesize(file.getSize()); //����ũ��
		//------------------------------------------------------------
		
		int cnt = dao.create(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>���� �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
		} else {
			mav.addObject("msg1", "<p>���� �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='��ӵ��' onclick='location.href=\"./create.do\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
		}
		
		return mav;
	} // createProc() end
	
	
	@RequestMapping("/notice/list.do")
	public ModelAndView list(@RequestParam Map map) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("notice/list");
		mav.addObject("root",Utility.getRoot()); // /hotsource
		String strSearchtype = (String)map.get("searchtype");
		String strSearch = (String)map.get("search");
		System.out.println("strSearchtypt = " +strSearchtype);
		System.out.println("strSearch = " + strSearch);
		
		//�� �Խñ� ��
	    int total_cnt = 0;
	    total_cnt=dao.getArticleCount();
	    
	    //����¡
	    int numPerPage=10;       // �� �������� ���ڵ� ����
	    int pagePerBlock = 10;  // ������ ����Ʈ
	  
	    String pageNum=(String)map.get("pageNum");
	    if(pageNum==null){
	        pageNum="1";
	    }
	    
	    int currentPage=Integer.parseInt(pageNum);
	    // �������� a+(n-1)d
	    int startRow = ((total_cnt-numPerPage)+(currentPage-1)*(-numPerPage))+1;
	    int endRow = total_cnt+(currentPage-1)*(-numPerPage);
	    
	    //int startRow=(currentPage-1)*numPerPage+1;
	    //int endRow=currentPage*numPerPage;
	    
	    
	    //��������
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
	
	@RequestMapping(value="/notice/read.do", method={RequestMethod.GET, RequestMethod.POST})
	  public ModelAndView read(int noticeno) { 
	                                          //HttpServletRequest req
	    ModelAndView mav = new ModelAndView();
	    NoticeDTO dto = dao.read(noticeno);
	    
	    mav.addObject("reply",dao.reply(noticeno));
	    mav.addObject("readcnt",dao.readcntup(noticeno));

	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dto);
	    
	    return mav;
	  }//read() end
	
	@RequestMapping(value="/notice/delete.do", method=RequestMethod.GET)  
	  public ModelAndView deleteForm(NoticeDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("notice/deleteForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getNoticeno()));//������������
	    return mav;
	  }//deleteForm() end
	  

	  @RequestMapping(value="/notice/delete.do", method=RequestMethod.POST)
	  public ModelAndView deleteProc(NoticeDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("notice/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    //���̺� ����Ǿ� �ִ� ���� ���� ��������
	    NoticeDTO oldDTO = dao.read(dto.getNoticeno());
	    
	    int cnt = dao.delete(dto.getNoticeno());
	    if(cnt==0) {
			mav.addObject("msg1", "<p>notice �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
		} else {
		//���� ���� ����
		    String basepath = req.getRealPath("/notice/n_storage");
		    UploadSaveManager.deleteFile(basepath, oldDTO.getFilename());
			mav.addObject("msg1", "<p>notice �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='���ε��' onclick='location.href=\"./create.do\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
		}    
	    return mav;
	  }//deleteProc() end
	  
	  @RequestMapping(value="/notice/update.do", method=RequestMethod.GET)  
	  public ModelAndView updateForm(NoticeDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("notice/updateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getNoticeno()));
	    return mav;
	  }//updateForm() end
	  
	  
	  @RequestMapping(value="/notice/update.do", method=RequestMethod.POST)  
	  public ModelAndView updateProc(NoticeDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("notice/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    String basePath = req.getRealPath("/notice/n_storage");
	    
	    //������ ����� ���� ��������
	    NoticeDTO oldDTO = dao.read(dto.getNoticeno());
	//---------------------------------------------------------    
	//  ������ �����Ұ�����?
	    MultipartFile file = dto.getFile();
	    if(file.getSize()>0) { // ���ε� ������ ���۵� ���
	      //���� ���� ����
	      UploadSaveManager.deleteFile(basePath, oldDTO.getFilename());      
	      //�ű� ���� ����
	      String filename = UploadSaveManager.saveFileSpring30(file, basePath);
	      dto.setFilename(filename);
	      dto.setFilesize(file.getSize());
	    }else { //������ �������� �ʴ� ���
	      dto.setFilename(oldDTO.getFilename());
	      dto.setFilesize(oldDTO.getFilesize());
	    }//if end
	//---------------------------------------------------------    
	    int cnt = dao.update(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>notice �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�׷���' onclick='location.href=\"./list.do\"'>");
		} else {
			mav.addObject("msg1", "<p>notice �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='�׷���' onclick='location.href=\"./list.do\"'>");
		}
	    
	    return mav;
	  }//updateProc() end
	  
	  @RequestMapping(value="/notice/replycreate.do", method={RequestMethod.GET, RequestMethod.POST})
	  public void replycreate(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	    	ReplyDTO dto = new ReplyDTO();
	    	
	    	//1) ����ڰ� ��û�� ���� �����ͼ� ������ ��� 
	        String s_noticeno = req.getParameter("noticeno");   
	        int noticeno = Integer.parseInt(s_noticeno); // getParameter�� String���̶� ����ȯ
	        String id = req.getParameter("id");
	        String content = req.getParameter("content");
	        
	        //2) 1)�� �������� dto��ü�� ���
	        dto.setNoticeno(noticeno);
	        dto.setId(id);
	        dto.setContent(content);
	        
	        String message = "";
	          int cnt = dao.replycreate(dto);
	          if(cnt==0) {
	            message = "��� �ۼ� ����";
	          }else {
	            message = "��� �ۼ� ����";
	            dao.replycntup(noticeno);
	          }			           
	        
	        resp.setContentType("text/plain; charset=UTF-8");
	        PrintWriter out = resp.getWriter(); //��°�ü
	        out.println(message);               //����޼���
	        out.flush();
	        out.close();
	        
	      }catch (Exception e) {
	        System.out.println("���� : " + e);
	      }
	  }//replycreate() end
	  
	  @RequestMapping(value="/notice/replydelete.do", method=RequestMethod.POST)
	  public void replydelete(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	    	
	        String s_ntrno = req.getParameter("ntrno");   
	        //System.out.println(s_ntrno);
	        int ntrno = Integer.parseInt(s_ntrno); // getParameter�� String���̶� ����ȯ
	        
	        String s_noticeno = req.getParameter("noticeno");   
	        //System.out.println(s_noticeno);
	        int noticeno = Integer.parseInt(s_noticeno); // getParameter�� String���̶� ����ȯ
	        
	        String message = "";
	          int cnt = dao.replydelete(ntrno);
	          if(cnt==0) {
	            message = "��� ���� ����";
	          }else {
	            message = "��� ���� ����";
	            dao.replycntdown(noticeno);
	          }			           
	        
	        resp.setContentType("text/plain; charset=UTF-8");
	        PrintWriter out = resp.getWriter(); //��°�ü
	        out.println(message);               //����޼���
	        out.flush();
	        out.close();
	        
	      }catch (Exception e) {
	        System.out.println("���� : " + e);
	      }
	  }//replydelete() end
	  
	  @RequestMapping(value="/notice/replyupdate.do", method=RequestMethod.GET)  
	  public ModelAndView replyupdate(int ntrno) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("notice/reupdateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.replyread(ntrno));
	    return mav;
	  }//replyupdate() end
	  	  
	  @RequestMapping(value="/notice/replyupdate.do", method=RequestMethod.POST)  
	  public ModelAndView replyupdateProc(ReplyDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("notice/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    int cnt = dao.replyupdate(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>notice ��� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۷� �̵�' onclick='opener.parent.location.reload(); window.close();'>");
		} else {
			mav.addObject("msg1", "<p>notice ��� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='�Խñ۷� �̵�' onclick='opener.parent.location.reload(); window.close();'>");
		}
	    
	    return mav;
	  }//replyupdateProc() end
	  
	
	
} // class end
