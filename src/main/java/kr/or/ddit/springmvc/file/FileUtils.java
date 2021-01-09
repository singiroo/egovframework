package kr.or.ddit.springmvc.file;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.GenericValidator;

public class FileUtils {
	public static String makeFileName(String filePath, String fileType) {
		String result = "";
		if(GenericValidator.isBlankOrNull(filePath)) {
			
		}else {
			result = FilenameUtils.getFullPath(filePath)
					+ FilenameUtils.getBaseName(filePath)
					+"_"+fileType+"."
					+FilenameUtils.getExtension(filePath);
		}
		
		return result;
	}

}
