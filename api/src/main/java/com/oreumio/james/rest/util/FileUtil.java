package com.oreumio.james.rest.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author panasoon
 *
 */
public final class FileUtil {
	/**
	 * Constructor.
	 */
	private FileUtil() { }

	/**
	 * 파일 확장자 리턴
	 * @param fileName 파일명
	 * @return 파일 확장자
	 */
	public static String getFileExtn(String fileName) {
		String fileExtn = null;
		if (fileName != null) {
			int offset = fileName.lastIndexOf(".");
			if ((offset != -1) && (offset != fileName.length())) {
				fileExtn = fileName.substring(offset + 1);
			} else {
				fileExtn = "";
			}
		}
		return fileExtn;
	}

	/**
	 * 파일 확장자로 이미지 아이콘 좌표 픽셀을 얻는다.
	 * @param fileName 파일명
	 * @return  아이콘 좌표 픽셀
	 */
	public static String getImageIcon(String fileName) {
		String ext = getFileExtn(StringUtils.defaultString(fileName)).toLowerCase();
		String img = "jpg,png,gif,bmp", zip = "zip,alz,tgz,tar,rar", doc = "doc,docx",
				ppt = "ppt,pptx", xls = "xls,xlsx", mov = "avi,mov,mp4,wmv,mkv";
		if (img.indexOf(ext) > -1) {
			return "0";
		} else if (ext.equals("txt")) {
			return "-16";
		} else if (doc.indexOf(ext) > -1) {
			return "-64";
		} else if (ext.equals("pdf")) {
			return "-32";
		} else if (ppt.indexOf(ext) > -1) {
			return "-96";
		} else if (ext.equals("hwp")) {
			return "-48";
		} else if (xls.indexOf(ext) > -1) {
			return "-80";
		} else if (mov.indexOf(ext) > -1) {
			return "-128";
		} else if (ext.equals("mp3")) {
			return "-144";
		} else if (zip.indexOf(ext) > -1) {
			return "-160";
		} else {
			return "-112";
		}
	}

	/**
	 * 파일 이름 리턴
	 * @return 파일 이름
	 */
	public static String makeFileName() {
		return IdGenerator.generateUniqueId() + "";
	}

	/**
	 * 파일 경로 문자열 리턴
	 * @param baseDir 상위 경로
	 * @param mode 모듈명
	 * @return 파일 경로 문자열
	 */
	public static String makeFilePath(String baseDir, String mode) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);

		StringBuffer sb = new StringBuffer();
		sb.append(baseDir).append(File.separator);
		sb.append(mode).append(File.separator);
		sb.append(year).append(File.separator);
		sb.append(month).append(File.separator);
		sb.append(date).append(File.separator);

		return sb.toString();
	}

	/**
	 * zip 임시파일 저장 디렉토리 생성
	 * @param baseDir 상위 경로
	 * @param mode 모듈명
	 * @param zipDir zip 파일 경로
	 * @return zip 임시파일 저장 디렉토리
	 */
	public static String createZipTempSaveDir(String baseDir, String mode, String zipDir) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);

		StringBuffer sb = new StringBuffer();
		sb.append(baseDir).append(File.separator);
		sb.append(mode).append(File.separator);
		sb.append(year).append(File.separator);
		sb.append(month).append(File.separator);
		sb.append(date).append(File.separator);
		sb.append(zipDir).append(File.separator);

		makeFolder(sb.toString());

		return sb.toString();
	}

	/**
	 * 디렉토리 생성
	 * @param dirPath 경로
	 * @return 경로
	 */
	public static String makeFolder(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return dir.getAbsolutePath();
	}

	/**
	 * 임시파일 저장 디렉토리 생성
	 * 
	 * @param baseDir 첨부파일 최상위 경로
	 * @param mode 모듈명
	 * @return mode 첨부파일 구분을 위한 중간 경로
	 */
	public static String createTempSaveDir(String baseDir, String mode) {

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);

		StringBuffer sb = new StringBuffer();
		sb.append(baseDir).append(File.separator);
		sb.append(mode).append(File.separator);
		sb.append(year).append(File.separator);
		sb.append(month).append(File.separator);
		sb.append(date).append(File.separator);

		makeFolder(sb.toString());

		return sb.toString();
	}

	/**
	 * 파일을 디렉토리로 이동
	 * @param srcFilePath 원래 파일 경로
	 * @param destDirPath 이동할 파일 경로
	 * @throws java.io.IOException e
	 */
	public static void moveFileToDirectory(String srcFilePath, String destDirPath) throws IOException {
        File srcFile = new File(srcFilePath);
        File destDir = new File(destDirPath);
        FileUtils.moveFileToDirectory(srcFile, destDir, true);
	}

	/**
	 * 파일 복사
	 * @param srcFile 원래 파일
	 * @param destFile 대상 파일
	 * @throws java.io.IOException e
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		FileUtils.copyFile(srcFile, destFile);
	}

	/**
	 * 파일을 다른 경로로 복사한다.
	 * @param srcDir 원래 경로
	 * @param destDir 대상 경로
	 * @throws java.io.IOException e
	 */
	public static void copyDirectory(File srcDir, File destDir) throws IOException {
		FileUtils.copyDirectory(srcDir, destDir);
	}

	/**
	 * 파일 내용을 조회
	 * @param file 읽을 파일
	 * @param encoding 인코딩
	 * @return 파일 내용
	 * @throws java.io.IOException e
	 */
	public static String readFileToString(File file, String encoding) throws IOException {
		return FileUtils.readFileToString(file, encoding);
	}

	/**
	 * 입력받은 내용으로 파일 생성한다.
	 * @param file 저장될 파일
	 * @param content 내용
	 * @param encoding 인코딩
	 * @throws java.io.IOException e
	 */
	public static void writeStringToFile(File file, String content, String encoding) throws IOException {
		FileUtils.writeStringToFile(file, content, encoding);
	}

	/**
	 * 파일 삭제
	 * @param delFilePath 경로
	 * @throws java.io.IOException e
	 */
	public static void deleteFile(String delFilePath) throws IOException {
		File delFile = new File(delFilePath);
		delFile.delete();
	}

	/**
	 * 유효한 파일명을 리턴한다.
	 *
	 * @param fileName String
	 * @return the valid file name
	 */
	public static String getValidFileName(String fileName) {
		return removeDoublePeriod(StringUtils.replace(
				fileName.trim().replaceAll("[/:*?;<>\"|]", "_"), "\\", "_"));
	}

	/**
	 * 특정길이의 유효한 파일명을 리턴한다.
	 *
	 * @param fileName String
	 * @param maxLength int
	 * @return the valid file name
	 */
	public static String getValidFileName(String fileName, int maxLength) {
		String validFileName = getValidFileName(fileName);
		if (validFileName.length() > maxLength) {
			return validFileName.substring(0, maxLength);
		}
		return validFileName;
	}

	/**
	 * 확장자를 제외한 파일명을 리턴한다.
	 *
	 * @param fileName String
	 * @return 확장자를 제외한 파일명
	 */
	public static String getFileNameExcludeExt(String fileName) {
		String result = "";
		if (fileName == null) {
			return "";
		}

		String ext = FileUtil.getFileExtn(fileName);
		if (StringUtils.isNotEmpty(ext)) {
			result = StringUtils.removeEnd(fileName, "." + ext);
		} else {
			result = fileName;
		}
		return result;
	}

	/**
	 * 중복 dot(마침표) 제거
	 *
	 * @param srcStr String
	 * @return 문자열
	 */
	public static String removeDoublePeriod(String srcStr) {
		while (srcStr.indexOf("..") > -1) {
			srcStr = srcStr.replaceAll("\\.\\.", "\\.");
		}
		return srcStr;
	}

	/**
	 * root 경로를 제외한 경로 리턴
	 * @param base 최상위 경로
	 * @param file 전체 경로
	 * @return root 경로를 제외한 경로
	 * @throws java.io.IOException
	 */
	public static String relative(File base, File file) throws IOException {
	    int rootLength = base.getCanonicalPath().length();
	    String absFileName = file.getCanonicalPath();
	    String relFileName = absFileName.substring(rootLength);
	    if (file.isDirectory()) {
	    	relFileName += File.separator;
	    }
	    return relFileName;
	}

	/**
	 * 특정 경로안에 파일 경로 목록 리턴
	 * @param path 경로
	 * @return 파일 경로 목록
	 */
	public static List<String> getFileList(String path) {
		List<String> results = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null.

		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getAbsolutePath());
		    }
		}
		return results;
	}

	/**
	 * 특정 경로안에 파일 경로 목록 리턴
	 * @param path 경로
	 * @param fileList 경로안에 파일 목록이 저장될 목록
	 * @return 파일 목록
	 */
	public static List<File> getFileList(String path, List<File> fileList) {
		File[] files = new File(path).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	fileList.add(file);
		    } else {
		    	getFileList(file.getAbsolutePath(), fileList);
		    }
		}
		return fileList;
	}

	/**
	 * 브라우저 종류를 리턴한다.
	 * @param request r
	 * @return 브라우저 종류
	 */
	public static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		 if (header.indexOf("MSIE") > -1) {
			 return "MSIE";
		 } else if (header.indexOf("rv:11.0") > -1) {
			 /*
			  * IE 10 버전 이하에서는 'MSIE' 문자열을 체크하여 브라우저가 IE인지 체크하였다.
			  * 하지만 IE 11 버전부터는 브라우저 정보에 MSIE라는 문자열이 들어가지 않는다
			  * 기존에 IE를 체크하던 로직은 IE 11을 나타내는 'rv:11' 문자열을 함께 체크하도록 해야 한다.
			  * */
			 return "ie11";
		 } else if (header.indexOf("Chrome") > -1) {
			 return "Chrome";
		 } else if (header.indexOf("Opera") > -1) {
			 return "Opera";
		 }
		 return "Firefox";
	}

	/**
	 * 인코딩된 파일명을 리턴한다.
	 * @param fileName 파일명
	 * @return 인코딩된 파일명
	 * @throws java.io.UnsupportedEncodingException e
	 */
	public static String getEncodFileName(String fileName) throws UnsupportedEncodingException {
		HttpServletRequest request =
				((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		String header = getBrowser(request);

		String encodeFileName = null;
	    if (header.contains("MSIE") || header.contains("ie11")) {
	    	encodeFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
	    } else if (header.contains("Chrome")) {
	    	StringBuilder sb = new StringBuilder();
	    	for (int i = 0; i < fileName.length(); i++) {
	    		char c = fileName.charAt(i);
	    		if (c > '~') {
	    			sb.append(URLEncoder.encode("" + c, "UTF-8"));
	    		} else {
	    			sb.append(c);
	    		}
	    	}
	    	encodeFileName = sb.toString();
	    } else if (header.contains("Firefox")) {
	    	encodeFileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (header.contains("Opera")) {
        	encodeFileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
	    } else {
	    	encodeFileName = fileName;
	    }
		return encodeFileName;
	}

	/**
	 * 숫자를 단위로(Byte,KB,MB,GB).
	 *
	 * @param num long 데이터 단위
	 * @return the string
	 */
	public static String strNumToFileSize(long num) {
		String ret = "";
		long bias = 1024L;

		if (num < bias) {
			ret = num + "Byte";
		} else if (num >= bias && num < (bias * bias)) {
			ret = num / bias + "KB";
		} else if (num >= (bias * bias) && num < (bias * bias * bias)) {
			ret = num / (bias * bias) + "MB";
		} else if (num >= (bias * bias * bias) && num < (bias * bias * bias * bias)) {
			ret = num / (bias * bias * bias) + "GB";
		}
		return ret;
	}

	/**
	 * 지정된 폴더의 전체 용량을 조회한다.
	 * @param dir 폴더경로
	 * @return 폴더용량(byte)
	 */
	public static long getFolderSize(File dir) {
	    long size = 0;
	    for (File file : dir.listFiles()) {
	        if (file.isFile()) {
	            size += file.length();
	        } else {
	        	size += getFolderSize(file);
	        }
	    }
	    return size;
	}

	/**
	 * 중복되지 않는 폴더 생성.
	 *
	 * @param upPath 상위 경로
	 * @param folderName 폴더명
	 * @return 변경된 폴더명
	 */
	public static String createFolderName(String upPath, String folderName) {
		File file = new File(upPath, folderName);
		if (file.exists()) {
			return createFolderName(upPath, folderName, 0);
		}
		return folderName;
	}

	/**
	 * 중복되지 않는 폴더 생성.
	 *
	 * @param upPath 상위 경로
	 * @param folderName 폴더명
	 * @param order 순번
	 * @return 변경된 폴더명
	 */
	public static String createFolderName(String upPath, String folderName, int order) {
		File file = new File(upPath, folderName);
		if (file.exists()) {
			if (order == 0) {
				folderName = folderName + "(" + order + ")";
			} else {
				int pos = folderName.lastIndexOf("(");
				if (pos == -1) {
					pos = folderName.length();
				}
				folderName = folderName.substring(0, pos) + "(" + order + ")";
			}
			order++;
			createFolderName(upPath, folderName, order);
		}
		return folderName;
	}
}
