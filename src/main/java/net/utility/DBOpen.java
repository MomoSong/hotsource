package net.utility;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Component;

@Component
public class DBOpen {
  //데이터베이스 연결
	
	public DBOpen() {
		System.out.println("-----DBOpen() 객체 생성");
	}
	
	
  public Connection getConnection() {
    //1) 오라클 DB 정보-------------------------------------
	/*
    String url      = "jdbc:oracle:thin:@localhost:1521:xe";
    String user     = "java0403";
    String password = "1234";
    String driver   = "oracle.jdbc.driver.OracleDriver";     
    */
	  
    //2) My-SQL DB 정보-------------------------------------
    
    String url      = "jdbc:mysql://localhost:3306/myjava?useUnicode=true&characterEncoding=euckr";
    String user     = "root";
    String password = "1234";
    String driver   = "org.gjt.mm.mysql.Driver";    
    
    
    Connection con = null;
    
    try {
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, password);
    }catch (Exception e) {
        System.out.println("DB연결 실패 : " + e);
    }
    
    return con;    
    
  }//end
  
}//class end
