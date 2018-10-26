package kr.co.hotsource.commit;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
public class CommitCont {
	
	@Autowired
	CommitDAO dao;
	
	public CommitCont() {
		System.out.println("----CommitCont() ��ü ����");
	}
	
	//hotsource commit �Խ��� ù ������ ȣ�� 
	//http://localhost:9090/hotsource/project/commit/create.do
	
	@RequestMapping(value="/commit/create.do", method=RequestMethod.GET)
	public ModelAndView createForm(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("commit/createForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("prono",req.getParameter("prono"));
		return mav; // commit/createForm.jsp
	} // createForm() end
	
	@RequestMapping(value="/commit/create.do", method=RequestMethod.POST)
	public ModelAndView createProc(CommitDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("commit/msgView");
		mav.addObject("root", Utility.getRoot());
		//------------------------------------------------------------    
		//  ���۵� ������ ����Ǵ� ���� ���
		    String basePath = req.getRealPath("/commit/c_storage");
		    
		    //2)<input type='file' name='comfile'>
		    MultipartFile comfile = dto.getComfile();
		    String filename = UploadSaveManager.saveFileSpring30(comfile, basePath);
		    dto.setC_filename(filename);          //���ϸ�
		    dto.setC_filesize(comfile.getSize()); //����ũ��
		    System.out.println(req.getParameter("prono"));
		    dto.setProno(Integer.parseInt(req.getParameter("prono")));
		//------------------------------------------------------------
		
		int cnt = dao.create(dto);
		if(cnt==0) {
			mav.addObject("msg1", "<p>commit �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do?prono="+Integer.parseInt(req.getParameter("prono"))+"\"'>");
		} else {
			mav.addObject("msg1", "<p>commit �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='��ӵ��' onclick='location.href=\"./create.do?prono="+Integer.parseInt(req.getParameter("prono"))+"\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do?prono="+Integer.parseInt(req.getParameter("prono"))+"\"'>");
		}
		
		return mav;
	} // createProc() end
	
	
	@RequestMapping("/commit/list.do")
	public ModelAndView list(@RequestParam Map map, HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("commit/list");
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
	
	@RequestMapping(value="/commit/read.do", method=RequestMethod.GET)
	  public ModelAndView read(int icno, HttpServletRequest req) {  //HttpServletRequest req
	    ModelAndView mav = new ModelAndView();
	    CommitDTO dto = dao.read(icno); 
	    mav.addObject("readcnt",dao.readcntup(icno));
	    
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dto);
	    
	    return mav;
	  }//read() end
	@RequestMapping(value="/commit/delete.do", method=RequestMethod.GET)  
	  public ModelAndView deleteForm(CommitDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("commit/deleteForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getIcno()));//������������
	    return mav;
	  }//deleteForm() end
	  

	  @RequestMapping(value="/commit/delete.do", method=RequestMethod.POST)
	  public ModelAndView deleteProc(CommitDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("commit/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    //���̺� ����Ǿ� �ִ� ���� ���� ��������
	    CommitDTO oldDTO = dao.read(dto.getIcno());
	    
	    int cnt = dao.delete(dto.getIcno());
	    if(cnt==0) {
			mav.addObject("msg1", "<p>commit �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do?prono="+oldDTO.getProno()+"\"'>");
		} else {
		//���� ���� ����
		    String basepath = req.getRealPath("/commit/c_storage");
		    UploadSaveManager.deleteFile(basepath, oldDTO.getC_filename());
			mav.addObject("msg1", "<p>commit �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='���ε��' onclick='location.href=\"./create.do?prono="+oldDTO.getProno()+"\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./list.do?prono="+oldDTO.getProno()+"\"'>");
		}    
	    return mav;
	  }//deleteProc() end
	  
	  @RequestMapping(value="/commit/update.do", method=RequestMethod.GET)  
	  public ModelAndView updateForm(CommitDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("commit/updateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getIcno()));
	    return mav;
	  }//updateForm() end
	  
	  
	  @RequestMapping(value="/commit/update.do", method=RequestMethod.POST)  
	  public ModelAndView updateProc(CommitDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("commit/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    System.out.println(dto.getIcno());
	    String basePath = req.getRealPath("/commit/c_storage");
	    
	    //������ ����� ���� ��������
	    CommitDTO oldDTO = dao.read(dto.getIcno());
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
			mav.addObject("msg1", "<p>�Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�׷���' onclick='location.href=\"./list.do?prono="+oldDTO.getProno()+"\"'>");
		} else {
			System.out.println("update after : " +oldDTO.getProno());
			mav.addObject("msg1", "<p>�Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='�׷���' onclick='location.href=\"./list.do?prono="+oldDTO.getProno()+"\"'>");
		}
	    
	    return mav;
	  }//updateProc() end
	  

		@RequestMapping("/chat/chat.do")
		public ModelAndView chat(HttpServletResponse resp,HttpServletRequest req) throws Exception {
			InetAddress local;
			String ip ="";
			try {
			    local = InetAddress.getLocalHost();
			    ip = local.getHostAddress();
			    System.out.println("local ip : "+ip);
			} catch (UnknownHostException e1) {
			    e1.printStackTrace();
			}
			
			ModelAndView mav =new ModelAndView();
			String testid = (String) req.getParameter("id");
			System.out.println(testid);
			mav.setViewName("/chat/chat");
		    mav.addObject("id", testid+" : ");
		    mav.addObject("ip",ip);
			return mav;
		}//chat.do end

} // class end
