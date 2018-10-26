package kr.co.hotsource.follow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.hotsource.commit.CommitDTO;
import kr.co.hotsource.pjt.PjtDTO;
import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class FollowDAO {
  @Autowired
  private DBOpen dbopen;
  
  @Autowired
  private DBClose dbclose;
  
  Connection con = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  StringBuffer sql = null;
  ArrayList<FollowDTO> list = null;
  
  public FollowDAO(){
    System.out.println("----FollowDTO() 객체 생성");
  }
  
  public int follow(FollowDTO dto) {
   int res=0;
   try {
     con=dbopen.getConnection();
     sql=new StringBuffer();
     sql.append(" INSERT INTO follow (flwno, flw, flwr) "); 
     sql.append(" VALUES ((SELECT ifnull(MAX(flwno),0)+1 FROM follow as TB), ?, ?) "); 
     pstmt=con.prepareStatement(sql.toString());
     pstmt.setString(1, dto.getFlw());
     pstmt.setString(2, dto.getFlwr());
     
     res=pstmt.executeUpdate();
     
  } catch (Exception e) {
    System.out.println("추가실패 :" + e);
  }finally {
    dbclose.close(con,pstmt);
  }
   return res;
  }//create() end
  
  public List myfollow(String flwr) {
    FollowDTO dto = null;
    System.out.println(flwr);
    try {
      con = dbopen.getConnection();
      sql = new StringBuffer();
      sql.append(" SELECT flw ");
      sql.append(" FROM follow ");
      sql.append(" WHERE flwr = ? ");
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, flwr);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        list = new ArrayList<>();
        do {
        dto=new FollowDTO();
        dto.setFlw(rs.getString("flw"));
        
        list.add(dto);
      }while(rs.next());
      }
    }catch (Exception e) {
      System.out.println(e.toString());
    }finally {
      dbclose.close(con, pstmt, rs);
    }
    return list;
  }//myfollow end

  public List list(String flw) throws Exception {
    List list = null;
    sql = new StringBuffer();
      try {
        con=dbopen.getConnection();
        sql.append("             SELECT Prono,Ptitle,Pname,Pexplain,Pcourse,Pversion,Filesize,Id,Good,Date,Planguage ");
        sql.append("             FROM Pjt, (SELECT @rownum:=0)R ");
        sql.append("             WHERE id=? ");
         
        pstmt=con.prepareStatement(sql.toString());
        System.out.println(flw);
        pstmt.setString(1, flw);     
     
        rs = pstmt.executeQuery();
      if (rs.next()) {
        list = new ArrayList<>();
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
        } while (rs.next());
      }

    } catch (Exception e) {
      System.out.println("목록실패 : " + e);
    } finally {
      dbclose.close(con, pstmt, rs);
    }
    return list;
  }// list() end
  
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
