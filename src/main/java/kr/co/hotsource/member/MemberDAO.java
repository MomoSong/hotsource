package kr.co.hotsource.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hotsource.board.boardDTO;
import kr.co.hotsource.member.MemberDTO;
import kr.co.hotsource.pjt.PjtDTO;
import net.utility.Utility;
import net.utility.DBClose;
import net.utility.DBOpen;

  @Component
  public class MemberDAO {
      
      @Autowired
      DBOpen  dbopen;
      
      @Autowired
      DBClose dbclose;
     
        
      Connection con=null;
      PreparedStatement pstmt=null;
      ResultSet rs=null;
      StringBuffer sql=null;
      ArrayList<boardDTO> list = null;
      
      public MemberDAO() {
          System.out.println("----- MemberDAO 객체 생성됨...");  
      }
      
      public int insert(MemberDTO dto) {
        int res=0;
          
        try{
              con = dbopen.getConnection();
              sql = new StringBuffer();  
              sql.append(" INSERT INTO members(id, passwd, name, birth, ph_no, email, rating )");
              sql.append(" VALUES( ?, ?, ?, ?, ?, ?, 'B' )");
              pstmt=con.prepareStatement(sql.toString());
              pstmt.setString(1, dto.getId()); 
              pstmt.setString(2, dto.getPasswd());
              pstmt.setString(3, dto.getName());
              pstmt.setString(4, dto.getBirth());
              pstmt.setString(5, dto.getPh_no());
              pstmt.setString(6, dto.getEmail());
              
              res=pstmt.executeUpdate();
              
            }catch(Exception e) {
                System.out.println("회원가입 실패 : "+e);
              }finally{
                dbclose.close(con, pstmt, rs);
              }    
           return res;
         } //end
      
      
      public int duplicateID(String id) {
        int cnt = 0;
        try {
            con=dbopen.getConnection();
            sql=new StringBuffer();
            
            sql.append(" SELECT COUNT(id) as cnt");
            sql.append(" FROM members");
            sql.append(" WHERE id=?");      
            pstmt=con.prepareStatement(sql.toString());
            pstmt.setString(1, id);
            
            rs=pstmt.executeQuery();      
            
            if(rs.next()) {
                cnt=rs.getInt("cnt");
            }
            
        }catch (Exception e) {
            System.out.println("아이디 중복 확인 실패 : " + e);
        }finally {
            dbclose.close(con, pstmt, rs);
        }
        return cnt;
        } //duplecateID() end
      
  //이메일 중복확인
  public int duplecateEmail(String email) {
    int cnt=0;
    try {
      con=dbopen.getConnection();
      
      sql=new StringBuffer();
      sql.append(" SELECT COUNT(email) as cnt");
      sql.append(" FROM members");
      sql.append(" WHERE email=?");
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setString(1, email);
      rs=pstmt.executeQuery();
      if(rs.next()) {
        cnt=rs.getInt("cnt");
      }
    }catch (Exception e) {
      System.out.println("이메일 중복 확인 실패 : " + e);
    }finally {
      dbclose.close(con, pstmt, rs);
    }
    return cnt;
  }//duplecateEmail() end
  
  public String loginProc(String id, String passwd) {
    String rating = "";
    try {
      con = dbopen.getConnection();
      sql = new StringBuffer();
      sql.append(" SELECT rating FROM members ");
      sql.append(" WHERE id=? AND passwd=? ");
      sql.append(" AND rating IN ('B', 'S', 'G', 'D', 'M', 'X') ");
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, id);
      pstmt.setString(2, passwd);
      rs = pstmt.executeQuery();
      if(rs.next()==true) {
        rating=rs.getString("rating");          
      }else {
        rating=null;
      }
    }catch (Exception e) {
      System.out.println("login() 실패!!!!"+e);
    }finally {
      dbclose.close(con,pstmt,rs);
    }     
    return rating;
  } //loginProc() end

  public MemberDTO select(MemberDTO dto){
    try {
      con=dbopen.getConnection();
          sql=new StringBuffer();
          sql.append(" SELECT name, birth, ph_no, email ");
          sql.append(" FROM members WHERE id=? AND passwd=?");

          pstmt=con.prepareStatement(sql.toString());
          pstmt.setString(1, dto.getId());
          pstmt.setString(2, dto.getPasswd());
          
          rs=pstmt.executeQuery();
          
          if(rs.next()){
            dto.setName(rs.getString("name"));
            dto.setBirth(rs.getString("birth"));
            dto.setPh_no(rs.getString("ph_no"));
          dto.setEmail(rs.getString("email"));

          }else{
              dto=null;
          }
      }catch(Exception e) {
        System.out.println("회원정보 불러오기 실패 : " + e);
      }finally {
        dbclose.close(con, pstmt, rs);
      }
    //System.out.println(dto);
      return dto;
    } //select() end
  
  public MemberDTO read(String id) {
    MemberDTO dto = null;
    try{
        con = dbopen.getConnection();
        sql = new StringBuffer();
        sql.append(" SELECT passwd, name, birth, ph_no, email, rating");
        sql.append(" FROM members");
        sql.append(" WHERE id = ?");
        pstmt = con.prepareStatement(sql.toString());
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        if(rs.next()){
          dto = new MemberDTO();
          dto.setPasswd(rs.getString("passwd"));
            dto.setName(rs.getString("name"));
            dto.setBirth(rs.getString("birth"));
            dto.setPh_no(rs.getString("ph_no"));
            dto.setEmail(rs.getString("email"));
            dto.setRating(rs.getString("rating"));             
        }
    }catch(Exception e){
      System.out.println(e.toString());
    }finally{
        dbclose.close(con, pstmt, rs);
    }
    return dto;
  } //read() end
  
  public int update(MemberDTO dto) {
    int cnt = 0;
    try {
        con = dbopen.getConnection();
        sql = new StringBuffer();
        sql.append(" UPDATE members");
        sql.append(" SET name=?, passwd=?, birth=?, ph_no=?, email=? ");
        sql.append(" WHERE id = ?"); 
        pstmt = con.prepareStatement(sql.toString());
        pstmt.setString(1, dto.getName());
        pstmt.setString(2, dto.getPasswd());
        pstmt.setString(3, dto.getBirth());
        pstmt.setString(4, dto.getPh_no());
        pstmt.setString(5, dto.getEmail());
        pstmt.setString(6, dto.getId());
        cnt = pstmt.executeUpdate();

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        dbclose.close(con, pstmt, rs);
      }
      return cnt;
  } //update() end
  
  public int withdraw(MemberDTO dto) {
    int cnt = 0;
    try {
        con = dbopen.getConnection();
        sql = new StringBuffer();
        sql.append(" UPDATE members");
        sql.append(" SET rating='O'");
        sql.append(" WHERE id = ?"); 
        
        pstmt = con.prepareStatement(sql.toString());         
        pstmt.setString(1, dto.getId());
        cnt = pstmt.executeUpdate();

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        dbclose.close(con, pstmt, rs);
      }
      return cnt;
  } //update() end
  
  public String findIdpw(MemberDTO dto) {
    String id = null;
       try {
             //DB연결
             con=dbopen.getConnection();   //DB연결
             sql=new StringBuffer();
             sql.append(" SELECT id");
       sql.append(" FROM members WHERE name=? AND birth=?");

             
             pstmt=con.prepareStatement(sql.toString()); //sql 문 변환
             pstmt.setString(1, dto.getName());
             pstmt.setString(2, dto.getBirth());
             
         rs=pstmt.executeQuery();    
         
         if(rs.next()) {
               id = rs.getString("id");      
         }else {
           id=null;
         }
           } catch (Exception e) {
                   System.out.println("실패 : " + e);
           }finally {               //자원반납 할때 순서 거꾸로 닫아야함
                   dbclose.close(con, pstmt, rs);
           }       
               return id;

       }// end

public int pwReset(MemberDTO dto) {
  int cnt = 0;
    try {
    con=dbopen.getConnection();  //DB연결
    sql=new StringBuffer();
    
    sql.append(" UPDATE members SET passwd=? WHERE id=? ");

    pstmt=con.prepareStatement(sql.toString());
    pstmt.setString(1, dto.getPasswd());
    pstmt.setString(2, dto.getId()); 

    cnt = pstmt.executeUpdate();

    } catch (Exception e) {
       System.out.println("실패 : " + e);
    }finally { 
       dbclose.close(con,pstmt);
    }//end  
    return cnt;

 }//pwReset end



public List list(int start, int end, String strSearchtype, String strSearch, String s_id) throws Exception{
    List list=null;
    sql = new StringBuffer();
    try {
      con=dbopen.getConnection();
      
      sql.append(" SELECT a.* ");
      sql.append(" FROM ( ");
      sql.append("       SELECT @rownum:=@rownum+1 as RNUM, b.* ");
      sql.append("       FROM ( ");
      sql.append("             SELECT Prono,Ptitle,Pname,Pexplain,Pcourse,Pversion,Filesize,Id,Good,Date,Planguage ");
      sql.append("             FROM Pjt, (SELECT @rownum:=0)R ");
      sql.append("             WHERE id=? ORDER BY Prono ASC ");
      sql.append("            ) b ");
      sql.append("       ) a ");
      sql.append(" WHERE 1=1 ");
      sql.append(" AND a.RNUM >=? AND a.RNUM<=? ");
      if("1".equals(strSearchtype)) { //자바에서 String은 부등호( == , != 같은)를 사용하지 않고 .equals를 사용한다.
    	  sql.append(" AND a.Ptitle like concat('%', ?, '%')");
      }
      else if("2".equals(strSearchtype)) {
    	  sql.append(" AND a.Pname like concat('%', ?, '%')");
      }
      else if("3".equals(strSearchtype)) {
    	  sql.append(" AND a.Ptitle like concat('%', ?, '%') OR a.Pname like concat('%', ?, '%')");
      }
      sql.append(" GROUP BY a.Prono ");
      sql.append(" ORDER BY RNUM DESC ");
      
      pstmt=con.prepareStatement(sql.toString());
      System.out.println("s_id"+s_id);
      pstmt.setString(1, s_id);
      pstmt.setInt(2, start);
      pstmt.setInt(3, end);
      if("2".equals(strSearchtype) || "3".equals(strSearchtype)) {
    	  pstmt.setString(4, strSearch);
      }
      else if("4".equals(strSearchtype)) {
    	  pstmt.setString(4, strSearch);
    	  pstmt.setString(5, strSearch);
      }	  
      rs=pstmt.executeQuery();
      
      System.out.println(sql.toString());
      
      if(rs.next()) {
        list=new ArrayList(end);
        do {
        	PjtDTO dto = new PjtDTO();
			dto.setProno(rs.getInt("Prono"));
			dto.setPtitle(rs.getString("Ptitle"));
			dto.setPname(rs.getString("Pname"));
			dto.setPexplain(rs.getString("Pexplain"));
			dto.setPcourse(rs.getString("Pcourse"));
			dto.setPversion(rs.getString("Pversion"));
			dto.setFilesize(rs.getLong("Filesize"));
			dto.setId(rs.getString("Id"));
			dto.setGood(rs.getInt("Good"));
			dto.setDate(rs.getString("Date"));
			dto.setPlanguage(rs.getString("Planguage"));

          list.add(dto);
          
          System.out.println(list.get(0));
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
        pstmt=con.prepareStatement("SELECT count(*) FROM Pjt ");
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

public PjtDTO read(int prono) {
	PjtDTO dto = null;
	try {
		con = dbopen.getConnection();
		sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM Pjt ");
		sql.append(" WHERE prono = ? ");
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setInt(1, prono);

		rs = pstmt.executeQuery();
		if (rs.next()) {
			dto = new PjtDTO();
			dto.setProno(rs.getInt("Prono"));
			dto.setPtitle(rs.getString("Ptitle"));
			dto.setPname(rs.getString("Pname"));
			dto.setPexplain(rs.getString("Pexplain"));
			dto.setPcourse(rs.getString("Pcourse"));
			dto.setPversion(rs.getString("Pversion"));
			dto.setFilesize(rs.getLong("Filesize"));
			dto.setId(rs.getString("Id"));
			dto.setGood(rs.getInt("Good"));
			dto.setPjtcnt(rs.getInt("pjtcnt"));
			dto.setDate(rs.getString("Date"));
			dto.setPlanguage(rs.getString("Planguage"));
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbclose.close(con, pstmt, rs);
	}
	return dto;
}// read() end



}//class end
