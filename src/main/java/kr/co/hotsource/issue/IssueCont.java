package kr.co.hotsource.issue;

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
public class IssueCont {
	
	@Autowired
	IssueDAO dao;
	
	public IssueCont() {
		System.out.println("----IssueCont() ��ü ����");
	}
	
	//hotsource Issue �Խ��� ù ������ ȣ�� 
	//http://localhost:9090/hotsource/project/issue/create.do
	
	@RequestMapping(value="/issue/create.do", method=RequestMethod.GET)
	public ModelAndView createForm(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("issue/createForm");
	    mav.addObject("root", Utility.getRoot()); 
	    mav.addObject("prono",req.getParameter("prono"));
		return mav; // issue/createForm.jsp
	} // createForm() end
	
	@RequestMapping(value="/issue/create.do", method=RequestMethod.POST)
	public ModelAndView createProc(IssueDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("issue/msgView");
		mav.addObject("root", Utility.getRoot());
		//------------------------------------------------------------    
		//  ���۵� ������ ����Ǵ� ���� ���
		    String basePath = req.getRealPath("/issue/i_storage");
		    
		    //2)<input type='file' name='comfile'>
		    MultipartFile comfile = dto.getComfile();
		    String filename = UploadSaveManager.saveFileSpring30(comfile, basePath);
		    dto.setC_filename(filename);          //���ϸ�
		    dto.setC_filesize(comfile.getSize()); //����ũ��
		    dto.setProno(Integer.parseInt(req.getParameter("prono")));
		//------------------------------------------------------------
		
		int cnt = dao.create(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>issue �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do?prono="+Integer.parseInt(req.getParameter("prono"))+"\"'>");
		} else {
			mav.addObject("msg1", "<p>issue �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='��ӵ��' onclick='location.href=\"./create.do?prono="+Integer.parseInt(req.getParameter("prono"))+"\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do?prono="+Integer.parseInt(req.getParameter("prono"))+"\"'>");
		}
		
		return mav;
	} // createProc() end
	
	
	@RequestMapping("/issue/list.do")
	public ModelAndView list(@RequestParam Map map, HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("issue/list");
		mav.addObject("root",Utility.getRoot()); // /hotsource
		String strSearchtype = (String)map.get("searchtype");
		String strSearch = (String)map.get("search");
		System.out.println("strSearchtypt = " +strSearchtype);
		System.out.println("strSearch = " + strSearch);
		int prono = Integer.parseInt(req.getParameter("prono"));
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
	      
	        list=dao.list(startRow, endRow, strSearchtype, strSearch, prono);
	        
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
	    mav.addObject("prono",prono);
	    mav.addObject("list", list);
		
		
		return mav;
	} // list() end
	
	@RequestMapping(value="/issue/read.do", method={RequestMethod.GET, RequestMethod.POST})
	  public ModelAndView read(int icno) { 
	                                          //HttpServletRequest req
	    ModelAndView mav = new ModelAndView();
	    IssueDTO dto = dao.read(icno);
	    
	    mav.addObject("reply",dao.reply(icno));
	    mav.addObject("readcnt",dao.readcntup(icno));

	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dto);
	    
	    return mav;
	  }//read() end
	
	@RequestMapping(value="/issue/delete.do", method=RequestMethod.GET)  
	  public ModelAndView deleteForm(IssueDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("issue/deleteForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getIcno()));//������������
	    return mav;
	  }//deleteForm() end
	  

	  @RequestMapping(value="/issue/delete.do", method=RequestMethod.POST)
	  public ModelAndView deleteProc(IssueDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("issue/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    //���̺� ����Ǿ� �ִ� ���� ���� ��������
	    IssueDTO oldDTO = dao.read(dto.getIcno());
	    
	    int cnt = dao.delete(dto.getIcno());
	    if(cnt==0) {
			mav.addObject("msg1", "<p>issue �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do?prono="+oldDTO.getProno()+"\"'>");
		} else {
		//���� ���� ����
		    String basepath = req.getRealPath("/issue/i_storage");
		    UploadSaveManager.deleteFile(basepath, oldDTO.getC_filename());
			mav.addObject("msg1", "<p>issue �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='���ε��' onclick='location.href=\"./create.do?prono="+oldDTO.getProno()+"\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do?prono="+oldDTO.getProno()+"\"'>");
		}    
	    return mav;
	  }//deleteProc() end
	  
	  @RequestMapping(value="/issue/update.do", method=RequestMethod.GET)  
	  public ModelAndView updateForm(IssueDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("issue/updateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getIcno()));
	    return mav;
	  }//updateForm() end
	  
	  
	  @RequestMapping(value="/issue/update.do", method=RequestMethod.POST)  
	  public ModelAndView updateProc(IssueDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("issue/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    String basePath = req.getRealPath("/issue/i_storage");
	    
	    //������ ����� ���� ��������
	    IssueDTO oldDTO = dao.read(dto.getIcno());
	//---------------------------------------------------------    
	//  ������ �����Ұ�����?
	    MultipartFile comfile = dto.getComfile();
	    if(comfile.getSize()>0) { // ���ε� ������ ���۵� ���
	      //���� ���� ����
	      UploadSaveManager.deleteFile(basePath, oldDTO.getC_filename());      
	      //�ű� ���� ����
	      String filename = UploadSaveManager.saveFileSpring30(comfile, basePath);
	      dto.setC_filename(filename);
	      dto.setC_filesize(comfile.getSize());
	    }else { //������ �������� �ʴ� ���
	      dto.setC_filename(oldDTO.getC_filename());
	      dto.setC_filesize(oldDTO.getC_filesize());
	    }//if end
	//---------------------------------------------------------    
	    int cnt = dao.update(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>issue �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�׷���' onclick='location.href=\"./list.do?prono="+oldDTO.getProno()+"\"'>");
		} else {
			mav.addObject("msg1", "<p>issue �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='�׷���' onclick='location.href=\"./list.do?prono="+oldDTO.getProno()+"\"'>");
		}
	    
	    return mav;
	  }//updateProc() end
	  
	  @RequestMapping(value="/issue/recommend.do", method=RequestMethod.POST)
	  public void recommend(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	        String s_icno = req.getParameter("icno");   
	        //System.out.println(s_icno);
	        int icno = Integer.parseInt(s_icno);
	        String message = "";
	          int cnt = dao.recommend(icno);
	          if(cnt==0) {
	            message = "��õ ����";
	          }else {
	            message = "�Խñ��� ��õ �Ͽ����ϴ�";
	          }			           
	        
	        resp.setContentType("text/plain; charset=UTF-8");
	        PrintWriter out = resp.getWriter(); //��°�ü
	        out.println(message);               //����޼���
	        out.flush();
	        out.close();
	        
	      }catch (Exception e) {
	        System.out.println("���� : " + e);
	      }
	  }//recommend() end
	  
	  @RequestMapping(value="/issue/replycreate.do", method={RequestMethod.GET, RequestMethod.POST})
	  public void replycreate(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	    	ReplyDTO dto = new ReplyDTO();
	    	
	    	//1) ����ڰ� ��û�� ���� �����ͼ� ������ ��� 
	        String s_icno = req.getParameter("icno");   
	        int icno = Integer.parseInt(s_icno); // getParameter�� String���̶� ����ȯ
	        String id = req.getParameter("id");
	        String content = req.getParameter("content");
	        
	        //2) 1)�� �������� dto��ü�� ���
	        dto.setIcno(icno);
	        dto.setId(id);
	        dto.setContent(content);
	        
	        String message = "";
	          int cnt = dao.replycreate(dto);
	          if(cnt==0) {
	            message = "��� �ۼ� ����";
	          }else {
	            message = "��� �ۼ� ����";
	            dao.replycntup(icno);
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
	  
	  @RequestMapping(value="/issue/replydelete.do", method=RequestMethod.POST)
	  public void replydelete(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	    	
	        String s_icrno = req.getParameter("icrno");   
	        //System.out.println(s_icrno);
	        int icrno = Integer.parseInt(s_icrno); // getParameter�� String���̶� ����ȯ
	        
	        String s_icno = req.getParameter("icno");   
	        //System.out.println(s_icno);
	        int icno = Integer.parseInt(s_icno); // getParameter�� String���̶� ����ȯ
	        
	        String message = "";
	          int cnt = dao.replydelete(icrno);
	          if(cnt==0) {
	            message = "��� ���� ����";
	          }else {
	            message = "��� ���� ����";
	            dao.replycntdown(icno);
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
	  
	  @RequestMapping(value="/issue/replyupdate.do", method=RequestMethod.GET)  
	  public ModelAndView replyupdate(int icrno) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("issue/reupdateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.replyread(icrno));
	    return mav;
	  }//replyupdate() end
	  	  
	  @RequestMapping(value="/issue/replyupdate.do", method=RequestMethod.POST)  
	  public ModelAndView replyupdateProc(ReplyDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("issue/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    int cnt = dao.replyupdate(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>issue ��� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۷� �̵�' onclick='opener.parent.location.reload(); window.close();'>");
		} else {
			mav.addObject("msg1", "<p>issue ��� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='�Խñ۷� �̵�' onclick='opener.parent.location.reload(); window.close();'>");
		}
	    
	    return mav;
	  }//updateProc() end
		  	
} // class end
