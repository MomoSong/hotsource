package kr.co.hotsource.pjt;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zipentry {
	public ArrayList<String> entry(String file) throws Exception {
		File file2 = new File(file);
		ZipInputStream in = new ZipInputStream(new FileInputStream(file2));
		ZipEntry entry = null;
		ArrayList<String> list = new ArrayList<String>();
		System.out.println("entry 가져오기");
		while ((entry = in.getNextEntry()) != null) {
			//System.out.println(entry.getName());
			list.add(entry.getName());
		}
		in.close();
		return list;
	}
}
