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
		System.out.println("----HelpboardCont() ��ü ����");
	}
	
	//hotsource Helpboard �Խ��� ù ������ ȣ�� 
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
		//  ���۵� ������ ����Ǵ� ���� ���
		    String basePath = req.getRealPath("/helpboard/help_storage");
		    
		    //2)<input type='file' name='file'>
		    MultipartFile file = dto.getFile();
		    String filename = UploadSaveManager.saveFileSpring30(file, basePath);
		    dto.setFilename(filename);          //���ϸ�
		    dto.setFilesize(file.getSize()); //����ũ��
		//------------------------------------------------------------
		    
		int cnt = dao.create(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>helpboard �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
		} else {
			mav.addObject("msg1", "<p>helpboard �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='��ӵ��' onclick='location.href=\"./create.do\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
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
	    //int startRow = ((total_cnt-numPerPage)+(currentPage-1)*(-numPerPage))+1;
	    //int endRow = total_cnt+(currentPage-1)*(-numPerPage);
	    
	    int startRow=(currentPage-1)*numPerPage+1;
	    int endRow=currentPage*numPerPage;
	    
	    
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
			mav.addObject("msg1", "<p>��й�ȣ�� Ʋ���ϴ�</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='������� �̵�' onclick='opener.parent.location.reload(); window.close();'>");
		} else {
			mav.addObject("msg1", "<p>��й�ȣ ��ġ</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='�Խñ۷� �̵�' "
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
			//  ���۵� ������ ����Ǵ� ���� ���
			    String basePath = req.getRealPath("/helpboard/help_storage");
			    
			    //2)<input type='file' name='file'>
			    MultipartFile file = dto.getFile();
			    String filename = UploadSaveManager.saveFileSpring30(file, basePath);
			    dto.setFilename(filename);          //���ϸ�
			    dto.setFilesize(file.getSize()); //����ũ��
			//------------------------------------------------------------
			    
			int cnt = dao.answer(dto);
			if(cnt==0) {
				mav.addObject("msg1", "<p>helpboard �亯 ��� ����</p>");
				mav.addObject("img", "<img src='../resources/images/fail.png'>");
				mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
				mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
			} else {
				mav.addObject("msg1", "<p>helpboard �亯 ��� ����</p>");
				mav.addObject("img", "<img src='../resources/images/success.png'>");
				mav.addObject("link1", "<input type='button' value='��ӵ��' onclick='location.href=\"./create.do\"'>");
				mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
			}
			
			return mav;
		} // answerProc() end
	  
	
	@RequestMapping(value="/helpboard/delete.do", method=RequestMethod.GET)  
	  public ModelAndView deleteForm(HelpboardDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("helpboard/deleteForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getHelpno()));//������������
	    return mav;
	  }//deleteForm() end
	  

	  @RequestMapping(value="/helpboard/delete.do", method=RequestMethod.POST)
	  public ModelAndView deleteProc(HelpboardDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("helpboard/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    //���̺� ����Ǿ� �ִ� ���� ���� ��������
	    HelpboardDTO oldDTO = dao.read(dto.getHelpno());
	    
	    int cnt = dao.delete(dto.getHelpno());
	    if(cnt==0) {
			mav.addObject("msg1", "<p>helpboard �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
		} else {
		//���� ���� ����
		    String basepath = req.getRealPath("/helpboard/help_storage");
		    UploadSaveManager.deleteFile(basepath, oldDTO.getFilename());
			mav.addObject("msg1", "<p>helpboard �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='���ε��' onclick='location.href=\"./create.do\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do\"'>");
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
	    
	    //������ ����� ���� ��������
	    HelpboardDTO oldDTO = dao.read(dto.getHelpno());
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
			mav.addObject("msg1", "<p>helpboard �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�׷���' onclick='location.href=\"./list.do\"'>");
		} else {
			mav.addObject("msg1", "<p>helpboard �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='�׷���' onclick='location.href=\"./list.do\"'>");
		}
	    
	    return mav;
	  }//updateProc() end

} // class end
