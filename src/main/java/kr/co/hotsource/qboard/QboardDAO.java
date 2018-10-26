package kr.co.hotsource.qboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class QboardDAO {
	@Autowired
	private DBOpen dbopen;
	
	@Autowired
	private DBClose dbclose;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<QboardDTO> list = null;
	ArrayList<ReplyDTO> reply = null;
	
	public QboardDAO() {
		System.out.println("----QboardDAO() 객체 생성");
	}
	
	//鍮�吏����� 濡�吏� ���� 
	public int create(QboardDTO dto){
		   
	    int res=0;
	    try {
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" INSERT INTO board(boardno, id, title, content, code, filename, filesize) ");
	      sql.append(" VALUES ((SELECT IFNULL(MAX(boardno),0)+1 FROM board as ICB),?, ?, ?, 'Q', ?, ?) ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getId());
	      pstmt.setString(2, dto.getTitle());
	      pstmt.setString(3, dto.getContent());
	      pstmt.setString(4, dto.getFilename());
	      pstmt.setLong(5, dto.getFilesize());
	      
	      res=pstmt.executeUpdate();
	      
	      } catch(Exception e) {
	        System.out.println("추가 실패 :" + e);
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
	      sql.append("             SELECT boardno,title,id,date,readcnt,good,replycnt ");
	      sql.append("             FROM board, (SELECT @rownum:=0)R ");
	      sql.append("             WHERE code='Q' ORDER BY boardno ASC ");
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
	      sql.append(" GROUP BY a.boardno ");
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
	          QboardDTO dto=new QboardDTO();
	          dto.setRnum(rs.getInt("rnum"));
	          dto.setBoardno(rs.getInt("boardno"));
	          dto.setTitle(rs.getString("title"));
	          dto.setId(rs.getString("id"));
	          dto.setDate(rs.getString("date"));
	          dto.setReadcnt(rs.getInt("readcnt"));
	          dto.setGood(rs.getInt("good"));
	          dto.setReplycnt(rs.getInt("replycnt"));
	        
	          
	          list.add(dto);
	        }while(rs.next());
	      }
	      
	    }catch (Exception e) {
	      System.out.println("목록 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return list;
	  }//list() end
	
	public QboardDTO read(int boardno) {
		QboardDTO dto = null;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" SELECT boardno, title, content, id, readcnt, good, filename, filesize ");
	      sql.append(" FROM board ");
	      sql.append(" WHERE boardno = ? "); 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, boardno);
	      rs = pstmt.executeQuery();
	      if(rs.next()) {
	        dto = new QboardDTO();
	        dto.setBoardno(rs.getInt("boardno"));
	        dto.setTitle(rs.getString("title"));
	        dto.setContent(rs.getString("content"));
	        dto.setId(rs.getString("id"));
	        dto.setReadcnt(rs.getInt("readcnt"));
	        dto.setGood(rs.getInt("good"));
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
	
	public int delete(int boardno) {
	    int cnt = 0;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" DELETE FROM board ");
	      sql.append(" WHERE boardno=? ");  
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, boardno);
	      cnt = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbclose.close(con, pstmt);
	    }
	    return cnt;
	  }//delete() end
	
	public int update(QboardDTO dto){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE board ");
	      sql.append(" SET title=?, content=?, filename=?, filesize=? ");
	      sql.append(" WHERE boardno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getTitle());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setString(3, dto.getFilename());
	      pstmt.setLong(4, dto.getFilesize());
	      pstmt.setInt(5, dto.getBoardno());
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("삭제실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//update() end
	
	public int recommend(int boardno){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE board ");
	      sql.append(" SET good = good+1 ");
	      sql.append(" WHERE boardno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, boardno);
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("추천 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//update() end
	
	public ArrayList<ReplyDTO> reply(int boardno) {
	    ArrayList<ReplyDTO> reply=null;
	    ReplyDTO dto=null;
	    try {
	      con=dbopen.getConnection();
	      
	      sql=new StringBuffer();
	      sql.append(" SELECT rno, id, content, date, boardno");
	      sql.append(" FROM reply ");
	      sql.append(" WHERE boardno = ? ");
	      sql.append(" ORDER BY rno ASC ");
	      
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, boardno);
	      
	      rs=pstmt.executeQuery();
	      if(rs.next()) {
	        reply=new ArrayList<ReplyDTO>();
	        do {
	          dto=new ReplyDTO();
	          dto.setRno(rs.getInt("rno"));
	          dto.setId(rs.getString("id"));
	          dto.setContent(rs.getString("content"));
	          dto.setDate(rs.getString("date"));
	          dto.setBoardno(rs.getInt("boardno"));
	          
	          reply.add(dto);
	        }while(rs.next());
	      }
	      
	    }catch (Exception e) {
	      System.out.println("紐⑸��ㅽ�� : " + e);
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
	      sql.append(" INSERT INTO reply(rno, id, content, boardno) ");
	      sql.append(" VALUES ((SELECT IFNULL(MAX(rno),0)+1 FROM reply as ICR),?, ?, ?) ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getId());
	      pstmt.setString(2, dto.getContent());
	      pstmt.setInt(3, dto.getBoardno());
	     
	      res=pstmt.executeUpdate();
	      
	      } catch(Exception e) {
	        System.out.println("댓글 작성 실패:" + e);
	      } finally {
	      dbclose.close(con, pstmt);
	      }
	      return res;
	  }//replycreate() end
	
	public int replydelete(int rno) {
	    int cnt = 0;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" DELETE FROM reply ");
	      sql.append(" WHERE rno=? ");  
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, rno);
	      cnt = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        dbclose.close(con, pstmt);
	    }
	    return cnt;
	  }//replydelete() end
	
	public ReplyDTO replyread(int rno) {
		ReplyDTO dto = null;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" SELECT rno, id, content, date, boardno ");
	      sql.append(" FROM reply ");
	      sql.append(" WHERE rno = ? "); 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, rno);
	      rs = pstmt.executeQuery();
	      if(rs.next()) {
	        dto = new ReplyDTO();
	        dto.setRno(rs.getInt("rno"));
	        dto.setId(rs.getString("id"));
	        dto.setContent(rs.getString("content"));
	        dto.setDate(rs.getString("date"));
	        dto.setBoardno(rs.getInt("boardno"));
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
	      sql.append(" UPDATE reply ");
	      sql.append(" SET content = ? ");
	      sql.append(" WHERE rno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getContent());
	      pstmt.setInt(2, dto.getRno());
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("댓글 수정 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//replyupdate() end
	
	public int readcntup(int boardno){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE board ");
	      sql.append(" SET readcnt = readcnt+1 ");
	      sql.append(" WHERE boardno=? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, boardno);
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("조회수 증가 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//readcntup() end
	
	//湲�媛��� 援ы��湲�
	  public int getArticleCount() throws Exception{
	      int res=0;
	      try{
	          con=dbopen.getConnection();
	          pstmt=con.prepareStatement("SELECT count(*) FROM board WHERE code='Q'");
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
	  
	  public int replycntup(int boardno){
		    int res=0;
		    try {    
		      con=dbopen.getConnection();
		      sql=new StringBuffer();
		      sql.append(" UPDATE board ");
		      sql.append(" SET replycnt = replycnt+1 ");
		      sql.append(" WHERE boardno=? ");
		      pstmt=con.prepareStatement(sql.toString());
		      
		      pstmt.setInt(1, boardno);
		      
		      res=pstmt.executeUpdate();

		    }catch(Exception e) {
		      System.out.println("댓글수 증가 실패 : " + e);
		    }finally {
		      dbclose.close(con, pstmt);
		    }    
		    return res;
		  }//replycntup() end
	  
	  public int replycntdown(int boardno){
		    int res=0;
		    try {    
		      con=dbopen.getConnection();
		      sql=new StringBuffer();
		      sql.append(" UPDATE board ");
		      sql.append(" SET replycnt = replycnt-1 ");
		      sql.append(" WHERE boardno=? ");
		      pstmt=con.prepareStatement(sql.toString());
		      
		      pstmt.setInt(1, boardno);
		      
		      res=pstmt.executeUpdate();

		    }catch(Exception e) {
		      System.out.println("댓글 수 감소 실패 : " + e);
		    }finally {
		      dbclose.close(con, pstmt);
		    }    
		    return res;
		  }//replycntdown() end

} // class end
