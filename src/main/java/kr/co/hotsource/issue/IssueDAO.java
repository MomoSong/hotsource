package kr.co.hotsource.issue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.hotsource.issue.IssueDTO;
import kr.co.hotsource.issue.ReplyDTO;
import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class IssueDAO {
	@Autowired
	private DBOpen dbopen;
	
	@Autowired
	private DBClose dbclose;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<IssueDTO> list = null;
	ArrayList<ReplyDTO> reply = null;
	
	public IssueDAO() {
		System.out.println("----IssueDAO() 객체 생성");
	}
	
	//비지니스 로직 작성 
	public int create(IssueDTO dto){
		   
	    int res=0;
	    try {
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" INSERT INTO icboard(icno, prono, title, content, id, c_filename, c_filesize, code) ");
	      sql.append(" VALUES ((SELECT IFNULL(MAX(icno),0)+1 FROM icboard as ICB),?, ?, ?, ?, ?, ?, 'I') ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, dto.getProno());
	      pstmt.setString(2, dto.getTitle());
	      pstmt.setString(3, dto.getContent());
	      pstmt.setString(4, dto.getId());
	      pstmt.setString(5, dto.getC_filename());
	      pstmt.setLong(6, dto.getC_filesize());
	      
	      res=pstmt.executeUpdate();
	      
	      } catch(Exception e) {
	        System.out.println("추가실패:" + e);
	      } finally {
	      dbclose.close(con, pstmt);
	      }
	      return res;
	  }//create() end
	
	public List list(int start, int end, String strSearchtype, String strSearch, int prono) throws Exception{
	    List list=null;
	    sql = new StringBuffer();
	    try {
	      con=dbopen.getConnection();
	      
	      sql.append(" SELECT a.* ");
	      sql.append(" FROM ( ");
	      sql.append("       SELECT @rownum:=@rownum+1 as RNUM, b.* ");
	      sql.append("       FROM ( ");
	      sql.append("             SELECT icno,title,id,date, c_filename, readcnt,rcmcnt,replycnt ");
	      sql.append("             FROM icboard, (SELECT @rownum:=0)R ");
	      sql.append("             WHERE code='I' and prono = ? ORDER BY icno ASC ");
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
	      sql.append(" GROUP BY a.icno ");
	      sql.append(" ORDER BY RNUM DESC ");
	      
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, prono);
	      pstmt.setInt(2, start);
          pstmt.setInt(3, end);
          if("2".equals(strSearchtype) || "3".equals(strSearchtype)) {
        	  pstmt.setString(4, strSearch);
          }
          else if("3".equals(strSearchtype)) {
        	  pstmt.setString(4, strSearch);
        	  pstmt.setString(5, strSearch);
          }
          
	      rs=pstmt.executeQuery();
	      
	      if(rs.next()) {
	        list=new ArrayList(end);
	        do {
	          IssueDTO dto=new IssueDTO();
	          dto.setRnum(rs.getInt("rnum"));
	          dto.setIcno(rs.getInt("icno"));
	          dto.setTitle(rs.getString("title"));
	          dto.setId(rs.getString("id"));
	          dto.setC_filename(rs.getString("c_filename"));
	          dto.setDate(rs.getString("date"));
	          dto.setReadcnt(rs.getInt("readcnt"));
	          dto.setRcmcnt(rs.getInt("rcmcnt"));
	          dto.setReplycnt(rs.getInt("replycnt"));
	        
	          
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
	
	public IssueDTO read(int icno) {
		IssueDTO dto = null;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" SELECT icno, prono, title, content, id, readcnt, rcmcnt, c_filename, c_filesize ");
	      sql.append(" FROM icboard ");
	      sql.append(" WHERE icno = ? "); 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, icno);
	      rs = pstmt.executeQuery();
	      if(rs.next()) {
	        dto = new IssueDTO();
	        dto.setIcno(rs.getInt("icno"));
	        dto.setProno(rs.getInt("prono"));
	        dto.setTitle(rs.getString("title"));
	        dto.setContent(rs.getString("content"));
	        dto.setId(rs.getString("id"));
	        dto.setReadcnt(rs.getInt("readcnt"));
	        dto.setRcmcnt(rs.getInt("rcmcnt"));
	        dto.setC_filename(rs.getString("c_filename"));
	        dto.setC_filesize(rs.getLong("c_filesize"));
	      }

	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return dto;
	  }//read() end
	
	public int delete(int icno) {
	    int cnt = 0;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" DELETE FROM icboard ");
	      sql.append(" WHERE icno=? ");  
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, icno);
	      cnt = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbclose.close(con, pstmt);
	    }
	    return cnt;
	  }//delete() end
	
	public int update(IssueDTO dto){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE icboard ");
	      sql.append(" SET title=?, content=?, c_filename=?, c_filesize=? ");
	      sql.append(" WHERE icno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getTitle());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setString(3, dto.getC_filename());
	      pstmt.setLong(4, dto.getC_filesize());
	      pstmt.setInt(5, dto.getIcno());
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("수정 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//update() end
	
	public int recommend(int icno){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE icboard ");
	      sql.append(" SET rcmcnt = rcmcnt+1 ");
	      sql.append(" WHERE icno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, icno);
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("추천 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//update() end
	
	public ArrayList<ReplyDTO> reply(int icno) {
	    ArrayList<ReplyDTO> reply=null;
	    ReplyDTO dto=null;
	    try {
	      con=dbopen.getConnection();
	      
	      sql=new StringBuffer();
	      sql.append(" SELECT icrno, id, content, date, icno");
	      sql.append(" FROM icreply ");
	      sql.append(" WHERE icno = ? ");
	      sql.append(" ORDER BY icrno ASC ");
	      
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, icno);
	      
	      rs=pstmt.executeQuery();
	      if(rs.next()) {
	        reply=new ArrayList<ReplyDTO>();
	        do {
	          dto=new ReplyDTO();
	          dto.setIcrno(rs.getInt("icrno"));
	          dto.setId(rs.getString("id"));
	          dto.setContent(rs.getString("content"));
	          dto.setDate(rs.getString("date"));
	          dto.setIcno(rs.getInt("icno"));
	          
	          reply.add(dto);
	        }while(rs.next());
	      }
	      
	    }catch (Exception e) {
	      System.out.println("목록실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return reply;
	  }//list() end
	
	public int replycreate(ReplyDTO dto){
		   
	    int res=0;
	    try {
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" INSERT INTO icreply(icrno, id, content, icno) ");
	      sql.append(" VALUES ((SELECT IFNULL(MAX(icrno),0)+1 FROM icreply as ICR),?, ?, ?) ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getId());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setInt(3, dto.getIcno());
	     
	      res=pstmt.executeUpdate();
	      
	      } catch(Exception e) {
	        System.out.println("추가실패:" + e);
	      } finally {
	      dbclose.close(con, pstmt);
	      }
	      return res;
	  }//replycreate() end
	
	public int replydelete(int icrno) {
	    int cnt = 0;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" DELETE FROM icreply ");
	      sql.append(" WHERE icrno=? ");  
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, icrno);
	      cnt = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbclose.close(con, pstmt);
	    }
	    return cnt;
	  }//replydelete() end
	
	public ReplyDTO replyread(int icrno) {
		ReplyDTO dto = null;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" SELECT icrno, id, content, date, icno ");
	      sql.append(" FROM icreply ");
	      sql.append(" WHERE icrno = ? "); 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, icrno);
	      rs = pstmt.executeQuery();
	      if(rs.next()) {
	        dto = new ReplyDTO();
	        dto.setIcrno(rs.getInt("icrno"));
	        dto.setId(rs.getString("id"));
	        dto.setContent(rs.getString("content"));
	        dto.setDate(rs.getString("date"));
	        dto.setIcno(rs.getInt("icno"));
	      }

	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return dto;
	  }//replyread() end
	
	public int replyupdate(ReplyDTO dto){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE icreply ");
	      sql.append(" SET content = ? ");
	      sql.append(" WHERE icrno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getContent());
	      pstmt.setInt(2, dto.getIcrno());
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("수정 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//replyupdate() end
	
	public int readcntup(int icno){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE icboard ");
	      sql.append(" SET readcnt = readcnt+1 ");
	      sql.append(" WHERE icno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, icno);
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("조회수 증가 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//readcntup() end
	
	//글갯수 구하기
	  public int getArticleCount() throws Exception{
	      int res=0;
	      try{
	          con=dbopen.getConnection();
	          pstmt=con.prepareStatement("SELECT count(*) FROM icboard WHERE code='I'");
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
	  
	  public int replycntup(int icno){
		    int res=0;
		    try {    
		      con=dbopen.getConnection();
		      sql=new StringBuffer();
		      sql.append(" UPDATE icboard ");
		      sql.append(" SET replycnt = replycnt+1 ");
		      sql.append(" WHERE icno=? ");
		      pstmt=con.prepareStatement(sql.toString());
		      
		      pstmt.setInt(1, icno);
		      
		      res=pstmt.executeUpdate();

		    }catch(Exception e) {
		      System.out.println("댓글 수 증가 실패 : " + e);
		    }finally {
		      dbclose.close(con, pstmt);
		    }    
		    return res;
		  }//replycntup() end
	  
	  public int replycntdown(int icno){
		    int res=0;
		    try {    
		      con=dbopen.getConnection();
		      sql=new StringBuffer();
		      sql.append(" UPDATE icboard ");
		      sql.append(" SET replycnt = replycnt-1 ");
		      sql.append(" WHERE icno=? ");
		      pstmt=con.prepareStatement(sql.toString());
		      
		      pstmt.setInt(1, icno);
		      
		      res=pstmt.executeUpdate();

		    }catch(Exception e) {
		      System.out.println("댓글 수 감소 실패 : " + e);
		    }finally {
		      dbclose.close(con, pstmt);
		    }    
		    return res;
		  }//replycntdown() end

} // class end
