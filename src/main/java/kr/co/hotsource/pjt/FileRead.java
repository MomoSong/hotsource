package kr.co.hotsource.pjt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class FileRead {
	public String FR(String path) {
		System.out.println("file read ------" + path);
		String returnstr = "";
		File file = new File(path);
		System.out.println("�����б��� path : " + path);
	    try{
	      if(checkBeforeFile(file)){
	    	  System.out.println("�����б� ����");
	        //FileReader�� ���ڷ� �ϴ� BufferedReader ��ü ����
	        BufferedReader br = new BufferedReader(new FileReader(file));

	        //������ �� ���徿 �о�´�.
	        String str = br.readLine();
	        System.out.println("���� ���ڿ� " + str);
	        //EOF�� null���ڸ� �����ϰ� �ִ�.
	        while(str != null){
	          //���� ���ڿ��� ����Ѵ�.
	        	returnstr += str+"\n";
	          //���� ���ڿ��� �������ش�.
	          str = br.readLine();
	        }
			//FileReader�ʹ� �ٸ��� ��� �� �� �ݾ��־�� �Ѵ�.
	        br.close();
	        return returnstr;
	      }else{
	        System.out.println("���Ͽ� ������ �� �����ϴ�.");
	        return "�����б� ����";
	      }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		return "";
	  }
	  static boolean checkBeforeFile(File file){
	    if(file.exists()){
	    	System.out.println("������ ������");
	      if(file.isFile() && file.canRead()){
	    	  System.out.println("������ �����ؼ� ������ ����");
	        return true;
	      }
	    }
	    return false;
	  }
}

