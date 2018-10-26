package kr.co.hotsource.commit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.hotsource.commit.CommitDTO;
import kr.co.hotsource.issue.IssueDTO;
import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class CommitDAO {
	@Autowired
	private DBOpen dbopen;
	
	@Autowired
	private DBClose dbclose;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<CommitDTO> list = null;
	
	public CommitDAO() {
		System.out.println("----CommitDAO() ��ü ����");
	}
	
	//�����Ͻ� ���� �ۼ� 
	public int create(CommitDTO dto){
		   
	    int res=0;
	    try {
	      con=dbopen.getConnection();
	      sql=new StringBuffer();
	      sql.append(" INSERT INTO icboard(icno, prono, title, content, id, c_filename, c_filesize, code) ");
	      sql.append(" VALUES ((SELECT IFNULL(MAX(icno),0)+1 FROM icboard as ICB),?, ?, ?, ?, ?, ?, 'C') ");
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, dto.getProno());
	      pstmt.setString(2, dto.getTitle());
	      pstmt.setString(3, dto.getContent());
	      pstmt.setString(4, dto.getId());
	      pstmt.setString(5, dto.getC_filename());
	      pstmt.setLong(6, dto.getC_filesize());
	      
	      res=pstmt.executeUpdate();
	      
	      } catch(Exception e) {
	        System.out.println("�߰�����:" + e);
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
	      sql.append("             SELECT icno, title, id, date, readcnt, conditions ");
	      sql.append("             FROM icboard, (SELECT @rownum:=0)R ");
	      sql.append("             WHERE code='C' and prono = ? ORDER BY icno ASC ");
	      sql.append("            ) b ");
	      sql.append("       ) a ");
	      sql.append(" WHERE a.RNUM >=? AND a.RNUM<=? ");
	      if("1".equals(strSearchtype)) { //�ڹٿ��� String�� �ε�ȣ( == , != ����)�� ������� �ʰ� .equals�� ����Ѵ�.
	    	  sql.append(" AND a.title like concat('%', ?, '%')");
	      }
	      else if("2".equals(strSearchtype)) {
	    	  sql.append(" AND a.content like concat('%', ?, '%')");
	      }
	      else if("3".equals(strSearchtype)) {
	    	  sql.append(" AND a.title like concat('%', ?, '%') OR a.content like concat('%', ?, '%')");
	      }
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
	          dto.setDate(rs.getString("date"));
	          dto.setReadcnt(rs.getInt("readcnt"));
	          dto.setConditions(rs.getString("conditions"));
	          
	          list.add(dto);
	        }while(rs.next());
	      }
	      
	    }catch (Exception e) {
	      System.out.println("��Ͻ��� : " + e);
	    }finally {
	      dbclose.close(con, pstmt, rs);
	    }
	    return list;
	  }//list() end
	
	public CommitDTO read(int icno) {
	    CommitDTO dto = null;
	    try {
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" SELECT icno, title, prono, content, id, date, conditions, c_filename, c_filesize ");
	      sql.append(" FROM icboard ");
	      sql.append(" WHERE icno = ? "); 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, icno);
	      rs = pstmt.executeQuery();
	      if(rs.next()) {
	        dto = new CommitDTO();
	        dto.setIcno(rs.getInt("icno"));
	        dto.setTitle(rs.getString("title"));
	        dto.setContent(rs.getString("content"));
	        dto.setProno(rs.getInt("prono"));
	        dto.setId(rs.getString("id"));
	        dto.setDate(rs.getString("date"));
	        dto.setConditions(rs.getString("conditions"));
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
	
	public int update(CommitDTO dto){
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
	      System.out.println("���� ���� : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//update() end
	
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
	      System.out.println("��ȸ�� ���� ���� : " + e);
	    }finally {
	      dbclose.close(con, pstmt);
	    }    
	    return res;
	  }//readcntup() end
	
	//�۰��� ���ϱ�
	  public int getArticleCount() throws Exception{
	      int res=0;
	      try{
	          con=dbopen.getConnection();
	          pstmt=con.prepareStatement("SELECT count(*) FROM icboard WHERE code='C'");
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

} // class end
