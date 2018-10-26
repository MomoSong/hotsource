package kr.co.hotsource.pjt;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class printFileSystem {
	String returnstr = "파일을 열수 없습니다.";
	
	public printFileSystem() {
		System.out.println("-----printFileSystem객체 생성");
	}
	public String printFileSystem2(String path, String filename) {
		
		//1. File 객체생성 path정보를 가지는 파일 만들기
		File directory = new File(path);
		//1.1 directory 안의 내용을 탐색
		String[] contents = directory.list();
		File file = null;
		for(String content:contents) {
			file = new File(directory.getAbsolutePath()+File.separator+content);
			//2.directory 객체의 내용이 폴더인지 파일인지 구분
			if(file.isDirectory()) {
				//2.2 폴더일 경우, 폴더 내부를 탐색
				returnstr = printFileSystem2(file.getAbsolutePath(), filename);
			}else {
				String[] strsplit = null;
				strsplit = file.getAbsolutePath().split("\\\\");
				if(strsplit[strsplit.length-1].equals(filename)) {
					returnstr = (file.getAbsolutePath());
				}
			}
		}
		System.out.println(returnstr);
		return returnstr;
	}
//	public static void main(String[] args) {
//		printFileSystem PFS = new printFileSystem();
//		System.out.println(PFS.printFileSystem2("C:\\Users\\Wansik\\Desktop\\entry","1.txt"));
//		FileRead fr = new FileRead();
//		System.out.println(fr.FR(PFS.printFileSystem2("C:\\Users\\Wansik\\Desktop\\entry","1.txt")));
//	}
}