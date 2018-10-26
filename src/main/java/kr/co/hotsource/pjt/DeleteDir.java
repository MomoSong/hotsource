package kr.co.hotsource.pjt;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
class DeleteDir {
//	public static void main(String args[]) {
//		deleteDirectory(new File(args[0]));
//	}

	public static boolean deleteDirectory(File path) {
		if (!path.exists()) {
			return false;
		}
		File[] files = path.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				deleteDirectory(file);
			} else {
				file.delete();
			}
		}
		return path.delete();
	}
}
