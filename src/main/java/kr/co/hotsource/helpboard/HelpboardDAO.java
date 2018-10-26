package kr.co.hotsource.helpboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.hotsource.helpboard.HelpboardDTO;
import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class HelpboardDAO {
	@Autowired
	private DBOpen dbopen;
	
	@Autowired
	private DBClose dbclose;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<HelpboardDTO> list = null;
	
	public HelpboardDAO() {
		System.out.println("----HelpboardDAO() 객체 생성");
	}
	
	//비지니스 로직 작성 
	public int create(HelpboardDTO dto){
		   
	    int res=0;
	    try {
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" INSERT INTO helpboard(helpno, title, content, id, filename, filesize, passwd, locked, grpno) ");
	      sql.append(" VALUES ((SELECT IFNULL(MAX(helpno),0)+1 FROM helpboard as HB) ");
	      sql.append(" ,?, ?, ?, ?, ?, ?, ?, (SELECT IFNULL(MAX(helpno),0)+1 FROM helpboard as HB) ) ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getTitle());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setString(3, dto.getId());
	      pstmt.setString(4, dto.getFilename());
	      pstmt.setLong(5, dto.getFilesize());
	      pstmt.setString(6, dto.getPasswd());
	      pstmt.setString(7, dto.getLocked());
	      
	      res=pstmt.executeUpdate();
	      
	      } catch(Exception e) {
	        System.out.println("추가실패:" + e);
	      } finally {
	      dbclose.close(con, pstmt);
	      }
	      return res;
	  }//create() end

	public List list(int start, int end, String strSearchtype, String strSearch) throws Exception{
	    List list=null;
	    sql = new StringBuffer();
	    try {
	      con=dbopen.getConnection();
	      
	      sql.append(" SELECT a.* ");
	      sql.append(" FROM ( ");
	      sql.append("       SELECT @rownum:=@rownum+1 as RNUM, b.* ");
	      sql.append("       FROM ( ");
	      sql.append("             SELECT helpno,title,id,date,readcnt,indent,locked ");
	      sql.append("             FROM helpboard, (SELECT @rownum:=0)R ");
	      sql.append("             ORDER BY grpno DESC, ansno ASC ");
	      sql.append("            ) b ");
	      sql.append("       ) a ");
	      sql.append(" WHERE a.RNUM >=? AND a.RNUM<=? ");
	      if("1".equals(strSearchtype)) { //자바에서 String은 부등호( == , != 같은)를 사용하지 않고 .equals를 사용한다.
	    	  sql.append(" AND a.title like concat('%', ?, '%')");
	      }
	      else if("2".equals(strSearchtype)) {
	    	  sql.append(" AND a.content like concat('%', ?, '%')");
	      }
	      else if("3".equals(strSearchtype)) {
	    	  sql.append(" AND a.title like concat('%', ?, '%') OR a.content like concat('%', ?, '%')");
	      }
	      
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, start);
          pstmt.setInt(2, end);
          if("1".equals(strSearchtype) || "2".equals(strSearchtype)) {
        	  pstmt.setString(3, strSearch);
          }
          else if("3".equals(strSearchtype)) {
        	  pstmt.setString(3, strSearch);
        	  pstmt.setString(4, strSearch);
          }
	      rs=pstmt.executeQuery();
	      
	      if(rs.next()) {
	        list=new ArrayList(end);
	        do {
	          HelpboardDTO dto=new HelpboardDTO();
	          dto.setHelpno(rs.getInt("helpno"));
	          dto.setTitle(rs.getString("title"));
	          dto.setId(rs.getString("id"));
	          dto.setDate(rs.getString("date"));
	          dto.setReadcnt(rs.getInt("readcnt"));
	          dto.setIndent(rs.getInt("indent"));
	          dto.setLocked(rs.getString("locked"));
	        
	          
	          list.add(dto);
	        }while(rs.next());
	      }
	      
	    }catch (Exception e) {
	      System.out.println("목록실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return list;
	  }//list() end
	
	//글갯수 구하기
	  public int getArticleCount() throws Exception{
	      int res=0;
	      try{
	          con=dbopen.getConnection();
	          pstmt=con.prepareStatement("SELECT count(*) FROM helpboard ");
	          rs=pstmt.executeQuery();
	          if(rs.next()){
	              res=rs.getInt(1);             
	          }
	      }catch(Exception e){
	          e.printStackTrace();
	      }finally{
	          if(rs!=null) try { rs.close(); } catch(SQLException e){}
	          if(pstmt!=null) try { pstmt.close(); } catch(SQLException e){}
	          if(con!=null) try { con.close(); } catch(SQLException e){}
	      }
	      
	      return res;
	      
	  }//getArticleCount() end
	  

	public HelpboardDTO read(int helpno) {
		HelpboardDTO dto = null;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" SELECT helpno, title, content, id, readcnt, filename, filesize, grpno ");
	      sql.append(" FROM helpboard ");
	      sql.append(" WHERE helpno = ? "); 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, helpno);
	      rs = pstmt.executeQuery();
	      if(rs.next()) {
	        dto = new HelpboardDTO();
	        dto.setHelpno(rs.getInt("helpno"));
	        dto.setTitle(rs.getString("title"));
	        dto.setContent(rs.getString("content"));
	        dto.setId(rs.getString("id"));
	        dto.setReadcnt(rs.getInt("readcnt"));
	        dto.setFilename(rs.getString("filename"));
	        dto.setFilesize(rs.getLong("filesize"));
	        dto.setGrpno(rs.getInt("grpno"));
	      }

	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return dto;
	  }//read() end
	
	public int readcntup(int helpno){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE helpboard ");
	      sql.append(" SET readcnt = readcnt+1 ");
	      sql.append(" WHERE helpno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, helpno);
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("조회수 증가 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//readcntup() end
	
	public int hbpsw(HelpboardDTO dto){
		int res=0;
		try {
		      con = dbopen.getConnection();
		      sql = new StringBuffer();
		      sql.append(" SELECT COUNT(*) as cnt ");
		      sql.append(" FROM helpboard ");
		      sql.append(" WHERE helpno=? AND passwd = ? "); 
		      pstmt = con.prepareStatement(sql.toString());
		      pstmt.setInt(1, dto.getHelpno());
		      pstmt.setString(2, dto.getPasswd());
		      rs = pstmt.executeQuery();
		      if(rs.next()) {
		        res = rs.getInt("cnt");
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      dbclose.close(con, pstmt, rs);
		    }
		return res;
	}
	
	public int answer(HelpboardDTO dto) {
	    int res=0;
	    try {
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      
	      //1) 부모 글정보 가져오기(그룹번호,들여쓰기,글순서) SELECT문
	      int grpno=0, indent=0, ansno=0;
	      sql.append(" SELECT grpno, indent, ansno");
	      sql.append(" FROM helpboard");
	      sql.append(" WHERE helpno=?");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, dto.getHelpno());
	      rs=pstmt.executeQuery();
	     
	      if(rs.next()) {
	        grpno =rs.getInt("grpno");     //부모글 그룹번호
	        indent=rs.getInt("indent")+1;//부모글 들여쓰기+1
	        ansno=rs.getInt("ansno")+1;//부모글 글순서+1
	      }//if end
	      
	      
	      //2) 글순서 재조정 UPDATE문
	      //   1)에서 사용했던 sql객체 초기화
	      sql.delete(0, sql.length()); //또는 sql=new StringBuilder()
	      sql.append(" UPDATE helpboard");
	      sql.append(" SET ansno=ansno+1");
	      sql.append(" WHERE grpno=? AND ansno>=?");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, grpno);
	      pstmt.setInt(2, ansno);
	      pstmt.executeUpdate();
	      
	      
	      //3) 답변글 추가 INSERT문
	      sql.delete(0, sql.length());
	      sql.append(" INSERT INTO helpboard(helpno, title, content, id, filename, filesize, passwd, locked, grpno, indent, ansno)");
	      sql.append(" VALUES((SELECT IFNULL(MAX(helpno),0)+1 FROM helpboard as HB), ?,?,?,?,?,?,'O',?,?,?)");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getTitle());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setString(3, dto.getId());
	      pstmt.setString(4, dto.getFilename());
	      pstmt.setLong(5, dto.getFilesize());
	      pstmt.setString(6, dto.getPasswd());
	      pstmt.setInt(7, grpno);
	      pstmt.setInt(8, indent);
	      pstmt.setInt(9, ansno);
	      res=pstmt.executeUpdate();
	      
	    }catch (Exception e) {
	      System.out.println("답변쓰기 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return res;
	  }//answer() end
	
	public String getPsw(int helpno){
		String res="";
		try {
		      con = dbopen.getConnection();
		      sql = new StringBuffer();
		      sql.append(" SELECT passwd ");
		      sql.append(" FROM helpboard ");
		      sql.append(" WHERE helpno=? "); 
		      pstmt = con.prepareStatement(sql.toString());
		      pstmt.setInt(1, helpno);
		      rs = pstmt.executeQuery();
		      if(rs.next()) {
		        res = rs.getString("passwd");
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      dbclose.close(con, pstmt, rs);
		    }
		return res;
	} //getPsw() end
	

	public int delete(int helpno) {
	    int cnt = 0;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" DELETE FROM helpboard ");
	      sql.append(" WHERE helpno=? ");  
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, helpno);
	      cnt = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbclose.close(con, pstmt);
	    }
	    return cnt;
	  }//delete() end
	
	public int update(HelpboardDTO dto){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE helpboard ");
	      sql.append(" SET title=?, content=?, filename=?, filesize=? ");
	      sql.append(" WHERE helpno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getTitle());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setString(3, dto.getFilename());
	      pstmt.setLong(4, dto.getFilesize());
	      pstmt.setInt(5, dto.getHelpno());
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("수정 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//update() end
	

} // class end
