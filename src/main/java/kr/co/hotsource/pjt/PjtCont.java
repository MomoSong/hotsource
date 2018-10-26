package kr.co.hotsource.pjt;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.utility.UploadSaveManager;
import net.utility.Utility;

@Controller
public class PjtCont {

	@Autowired
	DeleteDir DeleteDir;
	@Autowired
	PjtDAO dao;
	@Autowired
	JsonCreateTest JCT;
	@Autowired
	Decompless DC;
	@Autowired
	FileRead fileread;
	@Autowired
	printFileSystem pfs;

	public PjtCont() {
		System.out.println("----pjtCont() ��ü ����");
	}

	// hotsource commit �Խ��� ù ������ ȣ��
	// http://localhost:9090/hotsource/project/commit/create.do

	@RequestMapping(value = "/pjt/insert.do", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/insertForm");
		mav.addObject("root", Utility.getRoot());
		return mav; // /project/commit/createForm.jsp
	} // createForm() end

	@RequestMapping(value = "/pjt/insert.do", method = RequestMethod.POST)
	public ModelAndView createProc(PjtDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/msgView");
		mav.addObject("root", Utility.getRoot());

		Zipentry test = new Zipentry();
		ArrayList<String> al = null;
		// ------------------------------------------------------------
		// ���۵� ������ ����Ǵ� ���� ���
		String basePath = req.getRealPath("/pjt/p_storage");
		// -------------------------------------------------------------
		String id = req.getParameter("id");
		String verInfo = req.getParameter("verInfo");
		String Pexplain = req.getParameter("Pexplain");
		String Ptitle = req.getParameter("ptitle");
		String Planguage = req.getParameter("planguage");
		// 2)<input type='file' name='comfile'>
		MultipartFile comfile = dto.getComfile();
		String filename = UploadSaveManager.saveFileSpring30(comfile, basePath);
		// ������ ���� ����Ǯ��
		try {
			Decompless.decompress(basePath + "\\" + filename, basePath);
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			System.out.print("����Ǯ����� : ");
			e1.printStackTrace();
		}
		dto.setPtitle(Ptitle);
		dto.setPname(filename); // ���ϸ�
		dto.setFilesize(comfile.getSize()); // ����ũ��
		dto.setPcourse(basePath);
		dto.setPexplain(Pexplain);
		dto.setId(id);
		dto.setPversion(verInfo);
		dto.setPlanguage(Planguage);
		// ------------------------------------------------------------
		// ��� �� ���� ��Ʈ�� ��������
		try {
			al = test.entry(basePath + "\\" + filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < al.size(); i++) {
			String[] en_1 = al.get(i).split("/");
			System.out.println("entry���� : " + en_1.length);
			for (int k = 0; k < en_1.length; k++) {
				System.out.println("HC : " + en_1[k]);
			}
			dao.closerTB(en_1, filename);
		}
		// -------------------------------------------------------------
		int cnt = dao.insert(dto);
		if (cnt == 0) {
			mav.addObject("msg1", "<p>pjt �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='������Ʈ ���' onclick='location.href=\"./pjtlist.do\"'>");
		} else {
			mav.addObject("msg1", "<p>pjt �Խñ� ��� ����</p>");
			mav.addObject("img", "<img src='../../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='��ӵ��' onclick='location.href=\"./insertForm.do\"'>");
			mav.addObject("link2", "<input type='button' value='������Ʈ ���' onclick='location.href=\"./pjtlist.do\"'>");
		}

		return mav;
	} // createProc() end

	@RequestMapping(value = "/pjt/pjtread.do", method = RequestMethod.GET)
	public ModelAndView pjtRead(int prono) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/pjtread");
		mav.addObject("root", Utility.getRoot());
		PjtDTO dto = dao.read(prono);
		dao.readcntup(prono);
		mav.addObject("dto", dto);
		String pname = dto.getPname().substring(0, dto.getPname().length()-4);
		mav.addObject("pname",pname);
		return mav;
	} // read() end
	@RequestMapping(value="/pjt/downcnt.do", method=RequestMethod.GET)
	  public void recommend(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("�ٿ�ε� ī��Ʈ : ");
	    try {
	        String s_icno = "0"+ req.getParameter("prono");   
	        System.out.println(s_icno);
	        int prono = Integer.parseInt(s_icno);
	        String message = "";
	          int cnt = dao.downcnt(prono);
	          if(cnt==0) {
	            message = "�ٿ�ε� �� ���� ����";
	          }else {
	            message = "�ٿ�ε� �� ���� ����";
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

	@RequestMapping(value = "/pjt/pjtgood.do", method = RequestMethod.GET)
	public ModelAndView pjtGood(int prono) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/pjtread");
		mav.addObject("root", Utility.getRoot());
		dao.goodup(prono);
		PjtDTO dto = dao.read(prono);
		mav.addObject("dto", dto);
		return mav;
	} // read() end

	@RequestMapping(value = "/pjt/pjtread2.do", method = RequestMethod.GET)
	public ModelAndView pjtRead(HttpServletRequest req, int prono, String node) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/pjtread");
		mav.addObject("root", Utility.getRoot());
		String basepath = req.getRealPath("/pjt/p_storage");
		// ������� ���� �о����
		System.out.println("Ž���� ��� : " + node);
		System.out.println("���� Ž���� ��� : " + basepath);
		System.out.println("Ž���ؼ� ���� �޾ƿ� ���� ��� : " + pfs.printFileSystem2(basepath, node));
		System.out.println("Ž���� ���� ���� : " + fileread.FR(pfs.printFileSystem2(basepath, node)));
		mav.addObject("contents", fileread.FR(pfs.printFileSystem2(basepath, node)));
		// -----------------------------json
		PjtDTO dto = dao.read(prono);
		mav.addObject("dto", dto);
		return mav;
	} // Read() end

	@RequestMapping(value = "/pjt/pjtlist.do")
	public ModelAndView list(@RequestParam Map map) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/pjtlist");
		mav.addObject("root", Utility.getRoot());
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
		
	@RequestMapping(value = "/release/list.do")
	public ModelAndView releaselist(int prono, @RequestParam Map map) throws Exception {
		ModelAndView mav = new ModelAndView();
		System.out.println("������ ����Ʈ ���� ����");
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
	    	PjtDTO dto = dao.read(prono);
			String pname = dto.getPname().substring(0, dto.getPname().length()-4);
			System.out.println("������ : " + prono);
			System.out.println("������ �̸� :" + pname);
			mav.setViewName("/release/list");
			mav.addObject("root", Utility.getRoot());
			mav.addObject("list", dao.releaselist(pname));
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
	    mav.addObject("prono", prono);
	    
		

		return mav;

	} // class end

	@RequestMapping(value = "/pjt/ListByAjax")
	public void boardListByAjax(HttpServletResponse response, HttpServletRequest req, ModelMap modelMap) {
		String beforesplit = req.getParameter("pname");
		String filename = beforesplit.substring(1, beforesplit.length() - 5);
		System.out.println("ajax��� ���� �̸� : " + filename);

		// String treeStr = JCT.getJsonString(filename);
		List<?> listview = JCT.list(filename);

		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(listview);

		response.setContentType("application/json;charset=UTF-8");
		System.out.println(treeStr);
		try {
			response.getWriter().print(treeStr);
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	// ������Ʈ
	@RequestMapping(value = "/pjt/pjtupdate.do", method = RequestMethod.GET)
	public ModelAndView updateForm(PjtDTO dto) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/pjtupdateForm");
		mav.addObject("root", Utility.getRoot());
		mav.addObject("dto", dao.read(dto.getProno()));
		return mav;
	}// updateForm() end

	@RequestMapping(value = "/pjt/update.do", method = RequestMethod.POST)
	public ModelAndView updateProc(PjtDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/msgView");
		mav.addObject("root", Utility.getRoot());

		System.out.println(dto.getProno());
		String basePath = req.getRealPath("/pjt/p_storage");

		// ������ ����� ���� ��������
		PjtDTO oldDTO = dao.read(dto.getProno());
		// ---------------------------------------------------------
		// ������ �����Ұ�����?
		MultipartFile comfile = dto.getComfile();
		if (comfile.getSize() > 0) { // ���ε� ������ ���۵� ���
			// ���� ���� ����
			DeleteDir DD = new DeleteDir();
			UploadSaveManager.deleteFile(basePath, oldDTO.getPname());
			String foldername = oldDTO.getPname().substring(0, oldDTO.getPname().length() - 4); // ���� ������ ���丮�� ��
																								// closer���̺� �� ������
			// ���������� ���丮 ����
			File deletefile = new File(basePath + "\\\\" + foldername);
			DD.deleteDirectory(deletefile);
			// �ش� clsoer���̺� ����
			dao.droptable(foldername);
			// �ű� ���� ����
			String filename = UploadSaveManager.saveFileSpring30(comfile, basePath);
			// ������ ���� ����Ǯ��
			try {
				Decompless.decompress(basePath + "\\" + filename, basePath);
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				System.out.print("����Ǯ����� : ");
				e1.printStackTrace();
			}
			//���� �����ϴ� ���Ͽ� closer���̺� �ۼ�
			Zipentry test = new Zipentry();
			ArrayList<String> al = null;
			try {
				al = test.entry(basePath + "\\" + filename);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < al.size(); i++) {
				String[] en_1 = al.get(i).split("/");
				System.out.println("entry���� : " + en_1.length);
				for (int k = 0; k < en_1.length; k++) {
					System.out.println("HC : " + en_1[k]);
				}
				dao.closerTB(en_1, filename);
			}
			dto.setPname(filename);
			dto.setFilesize(comfile.getSize());
		} else { // ������ �������� �ʴ� ���
			dto.setPname(oldDTO.getPname());
			dto.setFilesize(oldDTO.getFilesize());
		} // if end
			// ---------------------------------------------------------
		int cnt = dao.update(dto);
		if (cnt == 0) {
			mav.addObject("msg1", "<p>�Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�׷���' onclick='location.href=\"./pjtlist.do\"'>");
		} else {
			mav.addObject("msg1", "<p>�Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='�׷���' onclick='location.href=\"./pjtlist.do\"'>");
		}

		return mav;
	}// updateProc() end

	@RequestMapping(value = "/pjt/pjtdelete.do", method = RequestMethod.GET)
	public ModelAndView deleteForm(PjtDTO dto) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/pjtdeleteForm");
		mav.addObject("root", Utility.getRoot());
		mav.addObject("dto", dao.read(dto.getProno()));// ������������
		return mav;
	}// deleteForm() end

	@RequestMapping(value = "/pjt/pjtdelete.do", method = RequestMethod.POST)
	public ModelAndView deleteProc(PjtDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/msgView");
		mav.addObject("root", Utility.getRoot());

		// ���̺� ����Ǿ� �ִ� ���� ���� ��������
		System.out.println(dto.getProno());
		PjtDTO oldDTO = dao.read(dto.getProno());

		int cnt = dao.delete(dto.getProno());
		if (cnt == 0) {
			mav.addObject("msg1", "<p>Pjt �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='�ٽýõ�' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./pjtlist.do\"'>");
		} else {
			// ���� ���� ����
			DeleteDir DD = new DeleteDir();
			String basepath = req.getRealPath("/pjt/p_storage");
			UploadSaveManager.deleteFile(basepath, oldDTO.getPname());
			String foldername = oldDTO.getPname().substring(0, oldDTO.getPname().length() - 4); // ���� ������ ���丮�� ��
																								// closer���̺� �� ������
			// ���������� ���丮 ����
			File deletefile = new File(basepath + "\\\\" + foldername);
			DD.deleteDirectory(deletefile);
			// �ش� clsoer���̺� ����
			dao.droptable(foldername);

			mav.addObject("msg1", "<p>Pjt �Խñ� ���� ����</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='���ε��' onclick='location.href=\"./insetForm.do\"'>");
			mav.addObject("link2", "<input type='button' value='�Խñ۸��' onclick='location.href=\"./pjtlist.do\"'>");
		}
		return mav;
	}// deleteProc() end
}
