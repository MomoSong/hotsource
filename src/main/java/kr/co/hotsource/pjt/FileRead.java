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
		System.out.println("파일읽기전 path : " + path);
	    try{
	      if(checkBeforeFile(file)){
	    	  System.out.println("파일읽기 시작");
	        //FileReader를 인자로 하는 BufferedReader 객체 생성
	        BufferedReader br = new BufferedReader(new FileReader(file));

	        //파일을 한 문장씩 읽어온다.
	        String str = br.readLine();
	        System.out.println("읽은 문자열 " + str);
	        //EOF는 null문자를 포함하고 있다.
	        while(str != null){
	          //읽은 문자열을 출력한다.
	        	returnstr += str+"\n";
	          //다음 문자열을 가르켜준다.
	          str = br.readLine();
	        }
			//FileReader와는 다르게 사용 후 꼭 닫아주어야 한다.
	        br.close();
	        return returnstr;
	      }else{
	        System.out.println("파일에 접근할 수 없습니다.");
	        return "파일읽기 실패";
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
	    	System.out.println("파일이 존재함");
	      if(file.isFile() && file.canRead()){
	    	  System.out.println("파일이 존재해서 읽을수 있음");
	        return true;
	      }
	    }
	    return false;
	  }
}

