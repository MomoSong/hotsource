package kr.co.hotsource.pjt;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class printFileSystem {
	String returnstr = "������ ���� �����ϴ�.";
	
	public printFileSystem() {
		System.out.println("-----printFileSystem��ü ����");
	}
	public String printFileSystem2(String path, String filename) {
		
		//1. File ��ü���� path������ ������ ���� �����
		File directory = new File(path);
		//1.1 directory ���� ������ Ž��
		String[] contents = directory.list();
		File file = null;
		for(String content:contents) {
			file = new File(directory.getAbsolutePath()+File.separator+content);
			//2.directory ��ü�� ������ �������� �������� ����
			if(file.isDirectory()) {
				//2.2 ������ ���, ���� ���θ� Ž��
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