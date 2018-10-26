package kr.co.hotsource.faq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.hotsource.faq.FaqDTO;

import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class FaqDAO {
	@Autowired
	private DBOpen dbopen;
	
	@Autowired
	private DBClose dbclose;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<FaqDTO> list = null;
	
	public FaqDAO() {
		System.out.println("----FaqDAO() 객체 생성");
	}
	
	//비지니스 로직 작성 
	
	
	
	public int create(FaqDTO dto){
		   
	    int res=0;
	    try {
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" INSERT INTO notice(noticeno, code, title, content, answer, filename, filesize) ");
	      sql.append(" VALUES ((SELECT IFNULL(MAX(noticeno),0)+1 FROM notice as NB),'F', ?, ?, ?, ?, ?) ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getTitle());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setString(3, dto.getAnswer());
	      pstmt.setString(4, dto.getFilename());
	      pstmt.setLong(5, dto.getFilesize());
	      
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
	      sql.append("             SELECT noticeno,title,date,readcnt ");
	      sql.append("             FROM notice, (SELECT @rownum:=0)R ");
	      sql.append("             WHERE code='F' ORDER BY noticeno ASC ");
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
	      sql.append(" GROUP BY a.noticeno ");
	      sql.append(" ORDER BY RNUM DESC ");
	      
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
	          FaqDTO dto=new FaqDTO();
	          dto.setRnum(rs.getInt("rnum"));
	          dto.setNoticeno(rs.getInt("noticeno"));
	          dto.setTitle(rs.getString("title"));
	          dto.setDate(rs.getString("date"));
	          dto.setReadcnt(rs.getInt("readcnt"));
	        
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
	          pstmt=con.prepareStatement("SELECT count(*) FROM notice WHERE code='F'");
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
	
	

	public FaqDTO read(int noticeno) {
		FaqDTO dto = null;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" SELECT noticeno, title, content, answer, readcnt, filename, filesize ");
	      sql.append(" FROM notice ");
	      sql.append(" WHERE noticeno = ? "); 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, noticeno);
	      rs = pstmt.executeQuery();
	      if(rs.next()) {
	        dto = new FaqDTO();
	        dto.setNoticeno(rs.getInt("noticeno"));
	        dto.setTitle(rs.getString("title"));
	        dto.setContent(rs.getString("content"));
	        dto.setAnswer(rs.getString("answer"));
	        dto.setReadcnt(rs.getInt("readcnt"));
	        dto.setFilename(rs.getString("filename"));
	        dto.setFilesize(rs.getLong("filesize"));
	      }

	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return dto;
	  }//read() end
	  
	public int readcntup(int noticeno){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE notice ");
	      sql.append(" SET readcnt = readcnt+1 ");
	      sql.append(" WHERE noticeno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, noticeno);
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("조회수 증가 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	}//readcntup() end

	public int delete(int noticeno) {
	    int cnt = 0;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" DELETE FROM notice ");
	      sql.append(" WHERE noticeno=? ");  
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, noticeno);
	      cnt = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbclose.close(con, pstmt);
	    }
	    return cnt;
	  }//delete() end
	
	public int update(FaqDTO dto){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE notice ");
	      sql.append(" SET title=?, content=?, answer=?, filename=?, filesize=? ");
	      sql.append(" WHERE noticeno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getTitle());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setString(3, dto.getAnswer());
	      pstmt.setString(4, dto.getFilename());
	      pstmt.setLong(5, dto.getFilesize());
	      pstmt.setInt(6, dto.getNoticeno());
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("수정 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//update() end

} // class end
