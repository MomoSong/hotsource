package net.utility;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Component;

@Component
public class DBOpen {
  //�����ͺ��̽� ����
	
	public DBOpen() {
		System.out.println("-----DBOpen() ��ü ����");
	}
	
	
  public Connection getConnection() {
    //1) ����Ŭ DB ����-------------------------------------
	/*
    String url      = "jdbc:oracle:thin:@localhost:1521:xe";
    String user     = "java0403";
    String password = "1234";
    String driver   = "oracle.jdbc.driver.OracleDriver";     
    */
	  
    //2) My-SQL DB ����-------------------------------------
    
    String url      = "jdbc:mysql://localhost:3306/myjava?useUnicode=true&characterEncoding=euckr";
    String user     = "root";
    String password = "1234";
    String driver   = "org.gjt.mm.mysql.Driver";    
    
    
    Connection con = null;
    
    try {
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, password);
    }catch (Exception e) {
        System.out.println("DB���� ���� : " + e);
    }
    
    return con;    
    
  }//end
  
}//class end
