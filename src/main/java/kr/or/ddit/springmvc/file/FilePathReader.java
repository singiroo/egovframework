package kr.or.ddit.springmvc.file;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class FilePathReader {
	public static void main(String[] args) {
		String path = "D:\\A_TeachingMaterial\\9.FinalProject\\eGovFrameDev-3.8.0-64bit\\workspace\\springMVC\\src\\main\\java";
		File dir = new File(path);
		String[] extensions = {"java"};
		Iterator<File> fileIterator = FileUtils.iterateFiles(dir, extensions, true);
		while(fileIterator.hasNext()) {
			File file = fileIterator.next();
			System.out.println(file.getPath().substring(path.length()+1).replaceAll("\\\\", "."));
			System.out.println(file.getName());
		}
	}
}
