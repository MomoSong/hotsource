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
		System.out.println("----pjtCont() 객체 생성");
	}

	// hotsource commit 게시판 첫 페이지 호출
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
		// 전송된 파일이 저장되는 실제 경로
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
		// 저장한 파일 압축풀기
		try {
			Decompless.decompress(basePath + "\\" + filename, basePath);
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			System.out.print("압축풀기실패 : ");
			e1.printStackTrace();
		}
		dto.setPtitle(Ptitle);
		dto.setPname(filename); // 파일명
		dto.setFilesize(comfile.getSize()); // 파일크기
		dto.setPcourse(basePath);
		dto.setPexplain(Pexplain);
		dto.setId(id);
		dto.setPversion(verInfo);
		dto.setPlanguage(Planguage);
		// ------------------------------------------------------------
		// 등록 할 파일 엔트리 가져오기
		try {
			al = test.entry(basePath + "\\" + filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < al.size(); i++) {
			String[] en_1 = al.get(i).split("/");
			System.out.println("entry길이 : " + en_1.length);
			for (int k = 0; k < en_1.length; k++) {
				System.out.println("HC : " + en_1[k]);
			}
			dao.closerTB(en_1, filename);
		}
		// -------------------------------------------------------------
		int cnt = dao.insert(dto);
		if (cnt == 0) {
			mav.addObject("msg1", "<p>pjt 게시글 등록 실패</p>");
			mav.addObject("img", "<img src='../../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='프로젝트 목록' onclick='location.href=\"./pjtlist.do\"'>");
		} else {
			mav.addObject("msg1", "<p>pjt 게시글 등록 성공</p>");
			mav.addObject("img", "<img src='../../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='계속등록' onclick='location.href=\"./insertForm.do\"'>");
			mav.addObject("link2", "<input type='button' value='프로젝트 목록' onclick='location.href=\"./pjtlist.do\"'>");
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
		System.out.println("다운로드 카운트 : ");
	    try {
	        String s_icno = "0"+ req.getParameter("prono");   
	        System.out.println(s_icno);
	        int prono = Integer.parseInt(s_icno);
	        String message = "";
	          int cnt = dao.downcnt(prono);
	          if(cnt==0) {
	            message = "다운로드 수 증가 실패";
	          }else {
	            message = "다운로드 수 증가 성공";
	          }			           
	        
	        resp.setContentType("text/plain; charset=UTF-8");
	        PrintWriter out = resp.getWriter(); //출력객체
	        out.println(message);               //응답메세지
	        out.flush();
	        out.close();
	        
	      }catch (Exception e) {
	        System.out.println("실패 : " + e);
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
		// 노드파일 내용 읽어오기
		System.out.println("탐색할 노드 : " + node);
		System.out.println("최초 탐색할 경로 : " + basepath);
		System.out.println("탐색해서 리턴 받아온 파일 경로 : " + pfs.printFileSystem2(basepath, node));
		System.out.println("탐색한 파일 내용 : " + fileread.FR(pfs.printFileSystem2(basepath, node)));
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
		
	@RequestMapping(value = "/release/list.do")
	public ModelAndView releaselist(int prono, @RequestParam Map map) throws Exception {
		ModelAndView mav = new ModelAndView();
		System.out.println("릴리즈 리스트 맵핑 시작");
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
	    int startRow = ((total_cnt-numPerPage)+(currentPage-1)*(-numPerPage))+1;
	    int endRow = total_cnt+(currentPage-1)*(-numPerPage);
	    
	    //int startRow=(currentPage-1)*numPerPage+1;
	    //int endRow=currentPage*numPerPage;
	    
	    
	    //페이지수
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
			System.out.println("릴리즈 : " + prono);
			System.out.println("릴리즈 이름 :" + pname);
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
		System.out.println("ajax통신 파일 이름 : " + filename);

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

	// 업데이트
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

		// 기존에 저장된 정보 가져오기
		PjtDTO oldDTO = dao.read(dto.getProno());
		// ---------------------------------------------------------
		// 파일을 수정할것인지?
		MultipartFile comfile = dto.getComfile();
		if (comfile.getSize() > 0) { // 업로드 파일이 전송된 경우
			// 기존 파일 삭제
			DeleteDir DD = new DeleteDir();
			UploadSaveManager.deleteFile(basePath, oldDTO.getPname());
			String foldername = oldDTO.getPname().substring(0, oldDTO.getPname().length() - 4); // 같이 삭제할 디렉토리명 및
																								// closer테이블 명 얻어오기
			// 압축해제한 디렉토리 삭제
			File deletefile = new File(basePath + "\\\\" + foldername);
			DD.deleteDirectory(deletefile);
			// 해당 clsoer테이블 삭제
			dao.droptable(foldername);
			// 신규 파일 저장
			String filename = UploadSaveManager.saveFileSpring30(comfile, basePath);
			// 저장한 파일 압축풀기
			try {
				Decompless.decompress(basePath + "\\" + filename, basePath);
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				System.out.print("압축풀기실패 : ");
				e1.printStackTrace();
			}
			//새로 저장하는 파일에 closer테이블 작성
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
				System.out.println("entry길이 : " + en_1.length);
				for (int k = 0; k < en_1.length; k++) {
					System.out.println("HC : " + en_1[k]);
				}
				dao.closerTB(en_1, filename);
			}
			dto.setPname(filename);
			dto.setFilesize(comfile.getSize());
		} else { // 파일을 수정하지 않는 경우
			dto.setPname(oldDTO.getPname());
			dto.setFilesize(oldDTO.getFilesize());
		} // if end
			// ---------------------------------------------------------
		int cnt = dao.update(dto);
		if (cnt == 0) {
			mav.addObject("msg1", "<p>게시글 수정 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='그룹목록' onclick='location.href=\"./pjtlist.do\"'>");
		} else {
			mav.addObject("msg1", "<p>게시글 수정 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='그룹목록' onclick='location.href=\"./pjtlist.do\"'>");
		}

		return mav;
	}// updateProc() end

	@RequestMapping(value = "/pjt/pjtdelete.do", method = RequestMethod.GET)
	public ModelAndView deleteForm(PjtDTO dto) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/pjtdeleteForm");
		mav.addObject("root", Utility.getRoot());
		mav.addObject("dto", dao.read(dto.getProno()));// 삭제관련정보
		return mav;
	}// deleteForm() end

	@RequestMapping(value = "/pjt/pjtdelete.do", method = RequestMethod.POST)
	public ModelAndView deleteProc(PjtDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pjt/msgView");
		mav.addObject("root", Utility.getRoot());

		// 테이블에 저장되어 있는 파일 정보 가져오기
		System.out.println(dto.getProno());
		PjtDTO oldDTO = dao.read(dto.getProno());

		int cnt = dao.delete(dto.getProno());
		if (cnt == 0) {
			mav.addObject("msg1", "<p>Pjt 게시글 삭제 실패</p>");
			mav.addObject("img", "<img src='../resources/images/fail.png'>");
			mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./pjtlist.do\"'>");
		} else {
			// 관련 파일 삭제
			DeleteDir DD = new DeleteDir();
			String basepath = req.getRealPath("/pjt/p_storage");
			UploadSaveManager.deleteFile(basepath, oldDTO.getPname());
			String foldername = oldDTO.getPname().substring(0, oldDTO.getPname().length() - 4); // 같이 삭제할 디렉토리명 및
																								// closer테이블 명 얻어오기
			// 압축해제한 디렉토리 삭제
			File deletefile = new File(basepath + "\\\\" + foldername);
			DD.deleteDirectory(deletefile);
			// 해당 clsoer테이블 삭제
			dao.droptable(foldername);

			mav.addObject("msg1", "<p>Pjt 게시글 삭제 성공</p>");
			mav.addObject("img", "<img src='../resources/images/success.png'>");
			mav.addObject("link1", "<input type='button' value='새로등록' onclick='location.href=\"./insetForm.do\"'>");
			mav.addObject("link2", "<input type='button' value='게시글목록' onclick='location.href=\"./pjtlist.do\"'>");
		}
		return mav;
	}// deleteProc() end
}
