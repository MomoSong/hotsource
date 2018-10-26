package net.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

@Component
public class DBClose {
	
	public DBClose() { //DB에 관련된 자원 반납
		System.out.println("-----DBClose() 객체 생성");
	}
	
  public void close(Connection con) {
    try {
      if(con!=null) {
        con.close();
      }
    }catch (Exception e) {}
  }//end
  
  public void close(Connection con, PreparedStatement pstmt) {
    try {
      if(pstmt!=null) {
        pstmt.close();
      }
    }catch (Exception e) {}

    try {
      if(con!=null) {
        con.close();
      }
    }catch (Exception e) {}
  }//end
  
  public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
    try {
      if(rs!=null) {
        rs.close();
      }
    }catch (Exception e) {}
  
    try {
      if(pstmt!=null) {
        pstmt.close();
      }
    }catch (Exception e) {}

    try {
      if(con!=null) {
        con.close();
      }
    }catch (Exception e) {}
  }
}//class end




