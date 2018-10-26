package kr.co.hotsource.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.hotsource.issue.ReplyDTO;
import kr.co.hotsource.member.MemberDTO;
import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class MasterDAO {
	@Autowired
	private DBOpen dbopen;
	
	@Autowired
	private DBClose dbclose;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<MemberDTO> list = null;
	
	public MasterDAO() {
		System.out.println("----MasterDAO() 객체 생성");
	}
	
	public List list(int start, int end) throws Exception{
	    List list=null;
	    sql = new StringBuffer();
	    try {
	      con=dbopen.getConnection();
	      
	      sql.append(" SELECT a.* ");
	      sql.append(" FROM ( ");
	      sql.append("       SELECT @rownum:=@rownum+1 as RNUM, b.* ");
	      sql.append("       FROM ( ");
	      sql.append("             SELECT id, name, ph_no, email, birth, joindate, rating ");
	      sql.append("             FROM members, (SELECT @rownum:=0)R ");
	      sql.append("            ) b ");
	      sql.append("       ) a ");
	      sql.append(" WHERE a.RNUM >=? AND a.RNUM<=? ");
	      sql.append(" ORDER BY RNUM ASC ");
	      
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, start);
          pstmt.setInt(2, end);
	      rs=pstmt.executeQuery();
	      
	      if(rs.next()) {
	        list=new ArrayList(end);
	        do {
	          MemberDTO dto=new MemberDTO();
	          dto.setId(rs.getString("id"));
	          dto.setName(rs.getString("name"));
	          dto.setPh_no(rs.getString("ph_no"));
	          dto.setEmail(rs.getString("email"));
	          dto.setBirth(rs.getString("birth"));
	          dto.setJoindate(rs.getString("joindate"));
	          dto.setRating(rs.getString("rating"));
	        
	          
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
	
		
	public int getArticleCount() throws Exception{
	      int res=0;
	      try{
	          con=dbopen.getConnection();
	          pstmt=con.prepareStatement("SELECT count(*) FROM members ");
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
	
	public String ratingread(String id) throws Exception{
	      String res="";
	      sql = new StringBuffer();
	      try{
	          con=dbopen.getConnection();
	          
	          sql.append("SELECT rating FROM members WHERE id = ? ");
	          pstmt=con.prepareStatement(sql.toString());
		      pstmt.setString(1, id);
	          rs=pstmt.executeQuery();
	          if(rs.next()){
	              res=rs.getString(1);             
	          }
	      }catch(Exception e){
	          e.printStackTrace();
	      }finally{
	          if(rs!=null) try { rs.close(); } catch(SQLException e){}
	          if(pstmt!=null) try { pstmt.close(); } catch(SQLException e){}
	          if(con!=null) try { con.close(); } catch(SQLException e){}
	      }
	      
	      return res;
	      
	  }//ratingread() end
	
	public int ratingupdate(MemberDTO dto){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" UPDATE members ");
	      sql.append(" SET rating = ? ");
	      sql.append(" WHERE id= ? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, dto.getRating());
	      pstmt.setString(2, dto.getId());
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("변경 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//ratingupdate() end
	
	public int memberdelete(String id){
	    int res=0;
	    try {    
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" DELETE FROM MEMBERS ");
	      sql.append(" WHERE id= ? ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setString(1, id);
	      
	      res=pstmt.executeUpdate();

	    }catch(Exception e) {
	      System.out.println("강퇴 실패 : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//memberdelete() end
	
}
