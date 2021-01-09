package kr.or.ddit.springmvc.file;

import org.apache.commons.io.FilenameUtils;

public class FileNameTest {
	public static void main(String[] args) {
		String filePath = "c:/temp/sally.png";
		
		System.out.println(FilenameUtils.getBaseName(filePath));
		System.out.println(FilenameUtils.getPath(filePath));
		System.out.println(FilenameUtils.getName(filePath));
		System.out.println(FilenameUtils.getExtension(filePath));
		System.out.println(FilenameUtils.getFullPath(filePath));
		
		String encFileName = FilenameUtils.getFullPath(filePath)
				+ FilenameUtils.getBaseName(filePath)
				+"_enc."
				+FilenameUtils.getExtension(filePath);
		
		String decFileName = FilenameUtils.getFullPath(filePath)
				+ FilenameUtils.getBaseName(filePath)
				+"_dec."
				+FilenameUtils.getExtension(filePath);
		
		System.out.println("encFileName : "+encFileName);
		System.out.println("decFileName : "+decFileName);
	}
}
