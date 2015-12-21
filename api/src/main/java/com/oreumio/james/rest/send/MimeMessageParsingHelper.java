package com.oreumio.james.rest.send;

//import com.nhncorp.lucy.security.xss.XssSaxFilter;
import com.oreumio.james.mime.MimeMessageParsingProxy;
import com.oreumio.james.rest.file.EmlFile;
import com.oreumio.james.rest.util.EmlMailUtil;
import com.oreumio.james.rest.util.FileUtil;
import com.oreumio.james.rest.util.IdGenerator;
import com.oreumio.james.rest.util.StringUtil;
import com.sun.mail.util.BASE64DecoderStream;
import net.freeutils.tnef.mime.TNEFMime;
import net.freeutils.tnef.mime.TNEFMimeMessage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * mime 파싱 유틸
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class MimeMessageParsingHelper extends MimeMessageParsingProxy {

	private static final String TEMP_DIR = EmlMailUtil.getProperty("http.properties", "upload.tempDir");
    private List<EmlFile> fileList;
    private Map<String, String> imgList;
    private int fileIndex = 0;
    private int untitled = 0;

    /**
	 * Constructor.
	 * @param source mime
	 * @param charset 인코딩 타입
	 * @throws javax.mail.MessagingException e
	 */
	protected MimeMessageParsingHelper(MimeMessage source, String charset)
			throws MessagingException {
		super(source, charset);
	}

	/**
	 * 메일 조회할 경우에만 이 생성자에 값 대입할 것(아이폰 메일 대응)
	 * Constructor.
	 * @param source mime
	 * @param charset 인코딩 타입
	 * @param contextPath 컨텍스트 패스
	 * @throws javax.mail.MessagingException e
	 */
	protected MimeMessageParsingHelper(MimeMessage source, String charset, String contextPath)
			throws MessagingException {
		super(source, charset, contextPath, TEMP_DIR);
	}

	/**
	 *
	 * @param message 메일 mime
	 * @param charset 인코딩 타입
	 * @return 메일 객체
	 * @throws javax.mail.MessagingException e
	 */
	public static MimeMessageParsingHelper getMimeMessageParsingProxy(MimeMessage message, String charset)
			throws MessagingException {
        return new MimeMessageParsingHelper(message, charset);
    }

	/**
	 *
	 * @param message 메일 mime
	 * @param charset 인코딩 타입
	 * @param contextPath 컨텍스트 패스(조회시에만 사용)
	 * @return 메일 객체
	 * @throws javax.mail.MessagingException e
	 */
	public static MimeMessageParsingHelper getMimeMessageParsingProxy(MimeMessage message, String charset,
			String contextPath) throws MessagingException {
        return new MimeMessageParsingHelper(message, charset, contextPath);
    }

	/**
	 * 메일 받는 사람 수 얻기
	 * @return 메일 받는 사람 수 - 1
	 * @throws javax.mail.MessagingException e
	 */
	public short getMailToCnt() throws MessagingException {
		short cnt = 0;
        InternetAddress[] toAddresses = getAddressHeader("To");
        InternetAddress[] ccAddresses = getAddressHeader("Cc");
        if (toAddresses != null) {
        	for (InternetAddress address : toAddresses) {
            	if (address.isGroup()) {
            		cnt += address.getGroup(false).length;
            	} else {
            		cnt++;
            	}
            }
        }

        if (ccAddresses != null) {
        	for (InternetAddress address : ccAddresses) {
            	if (address.isGroup()) {
            		cnt += address.getGroup(false).length;
            	} else {
            		cnt++;
            	}
            }
        }

        if (cnt > 0) {
        	cnt--;
        }

        return cnt;
    }

	/**
     * 주소 헤더값 파싱.
     *
     * @param name 헤더명
     * @return 주소 목록
     * @throws javax.mail.MessagingException
     */
    public List<String> getAddresses(String name) throws MessagingException {

        InternetAddress[] addresses = getAddressHeader(name);

        if (addresses == null || addresses.length == 0) {
        	return null;
        }

        List<String> parsedAddresses = new ArrayList<String>();

        for (InternetAddress address : addresses) {
            if (address.isGroup()) {
                parsedAddresses.addAll(getAddressesFromGroup(address));
            } else {
                parsedAddresses.add(EmlMailUtil.removeSpecialChar(decode(address.toString())));
            }
        }

        return parsedAddresses;
    }

    /* (non-Javadoc)
     * @see solutions.oreum.james.core.MimeMessageParsingProxy#getAddressesFromGroup(javax.mail.internet.InternetAddress)
     */
    protected List<String> getAddressesFromGroup(InternetAddress group) throws MessagingException {
        List<String> parsedAddresses = new ArrayList<String>();

        for (InternetAddress member : group.getGroup(true)) {
            if (member.isGroup()) {
                parsedAddresses.addAll(getAddressesFromGroup(member));
            } else {
                parsedAddresses.add(EmlMailUtil.removeSpecialChar(decode(member.toString())));
            }
        }

        return parsedAddresses;
    }

	/**
     * 주소 헤더 값을 구합니다.
     *
     * @param name 헤더명
     * @return address
     * @throws javax.mail.MessagingException
     */
    protected InternetAddress[] getAddressHeader(String name) throws MessagingException {
        String s = getHeader(name, ",");
        return s == null ? null : InternetAddress.parseHeader(s, false);
    }

	/**
	 * @param contextPath web url root
	 * @param xssFilterYn 본문 내용 xss 필터 적용 여부
	 * @return 본문 내용안에 이미지 생성하고 이미지 경로 변경후 리턴
	 */
	public String getImgHtml(String contextPath, boolean xssFilterYn) {
		String content = "";
		try {
			String textType = getTextType();
			content = getText();
			if ("plain".equals(textType)) {
				content = content.replace("<", "&lt;");
				content = content.replace(">", "&gt;");
				content = content.replace("\n", "<br/>");
			} else {
/*
				if (xssFilterYn) {
					XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml", true);
					content = filter.doFilter(content);
					content = StringUtil.removeStyle(content);
				}
*/
				//이미지 cid 경로를 변경
				Map<String, String> imgMap = getImgList(this);
				for (Map.Entry<String, String> entry : imgMap.entrySet()) {
					String imgUrl = contextPath + "/viewImage.do?filePath=" + entry.getValue();
					content = StringUtils.replace(content, "cid:" + entry.getKey(), imgUrl);
				}
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 예약 메일 타임존 커스텀 헤더값을 구한다.
	 * @return 예약 메일 타임존 커스텀 헤더값
	 * @throws javax.mail.MessagingException e
	 */
	public String getTimeZoneId() throws MessagingException {
		String timeZoneId = "";
		if (getHeader("X-TimeZone") != null) {
			timeZoneId = getHeader("X-TimeZone")[0];
		}
		return timeZoneId;
	}

	/**
	 * 임시저장된 메일의 비밀번호 헤더값을 구한다.
	 * @return 임시저장된 메일의 비밀번호
	 * @throws javax.mail.MessagingException e
	 */
	public String getPassword() throws MessagingException {
		String password = "";
		if (getHeader("X-Password") != null) {
			password = getHeader("X-Password")[0];
		}
		return password;
	}

	/**
     * 보낸사람 표시명.
     * 비표준 인코딩 대응.
     *
     * @return
     */
    public String getSenderDisp() throws MessagingException {
        String senderDisp = getAddress("From");
        return EmlMailUtil.removeSpecialChar(senderDisp);
    }

	/**
     * 보낸사람 표시명(조직도).
     * 비표준 인코딩 대응.
     * @return 보낸사람 표시명(조직도)
     * @throws javax.mail.MessagingException e
     */
    public String getSenderOrg() throws MessagingException {
    	String senderOrg = getAddress("X-From") != null ? getAddress("X-From") : getAddress("From");
        return EmlMailUtil.removeSpecialChar(senderOrg);
    }

    /**
     * 메일의 첨부된 파일 목록 리턴(실제 파일 생성은 안함)
 	 * @param p 메일 part
 	 * @return 첨부된 파일 목록
 	 * @throws javax.mail.MessagingException e1
 	 * @throws java.io.IOException e2
 	 */
 	public List<EmlFile> getCmmFileList(Part p) throws MessagingException, IOException {
 		return getCmmFileList(p, null);
 	}

	/**
	 * 메일의 첨부된 파일 목록 리턴
	 * @param p 메일 part
	 * @param indexList 추출할 파일 순번 (-1 값이 포함되어 있으면 모든 첨부파일 생성)
	 * @return 첨부된 파일 목록
	 * @throws javax.mail.MessagingException e1
	 * @throws java.io.IOException e2
	 */
	public List<EmlFile> getCmmFileList(Part p, List<Integer> indexList) throws MessagingException, IOException {
		if (p instanceof Message && !(p instanceof TNEFMimeMessage)) {
			fileList = new ArrayList<EmlFile>();
			fileIndex = 0; //첨부 파일 순번
			untitled = 0; //이름이 존재하지 않는 첨부파일 순번
		}

		ContentType contentType = new ContentType(p.getContentType());
        String subType = contentType.getSubType();

		String filePath = FileUtil.createTempSaveDir(TEMP_DIR, "eml");

		if (p.isMimeType("application/ms-tnef")) { //익스체인지 메일 서버 메일
			getCmmFileList(TNEFMime.convert(session, p), indexList);
		} else if (p.isMimeType("multipart/*")) {
	        // content may contain attachments
	        Multipart multiPart = (Multipart) p.getContent();
	        int numberOfParts = multiPart.getCount();
	        if ("alternative".equalsIgnoreCase(subType)) {
                for (int i = 0; i < multiPart.getCount(); i++) {
                    MimeBodyPart mimeBodyPart = (MimeBodyPart) multiPart.getBodyPart(i);
                    getCmmFileList(mimeBodyPart, indexList);
                }
            } else if ("mixed".equalsIgnoreCase(subType) || "related".equalsIgnoreCase(subType)
            		|| "report".equalsIgnoreCase(subType)) {

            	int startCnt = 1;
            	MimeBodyPart firstBodyPart = (MimeBodyPart) multiPart.getBodyPart(0);
                ContentType firstContentType = new ContentType(firstBodyPart.getContentType());
                String firstType = firstContentType.getBaseType();

                if ("text/rtf".equalsIgnoreCase(firstType)) {
                	startCnt = 0; //rtf 일 경우 첨부파일 처리
                }
		        for (int partCount = startCnt; partCount < numberOfParts; partCount++) {
		        	MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
		            ContentType subContentType = new ContentType(part.getContentType());
		    		String fileName = IdGenerator.generateUniqueId();
		            String originFile = StringUtils.defaultString(decode(part.getFileName()));
		            String ext = FileUtil.getFileExtn(originFile);
	            	if (StringUtils.isNotEmpty(ext)) {
	            		ext = "." + ext;
	            	}
		            EmlFile cmmFile = new EmlFile();
		            cmmFile.setModule("eml");
		            if (!"application/ms-tnef".equals(subContentType.getBaseType())
		            		&& !"inline".equalsIgnoreCase(part.getDisposition())) {
		    			if (StringUtils.isEmpty(originFile)) {
		            		originFile = "untitled_" + (++untitled) + "."
		            				+ getFileExtensionForMimeType(subContentType.getBaseType());
                        }
                        cmmFile.setFileType(subContentType.getBaseType());
		                cmmFile.setFileName(originFile);

		                if (part.getSize() == -1) {
		                	cmmFile.setFileSize(Long.parseLong(String.valueOf(
		                			Math.round(IOUtils.toByteArray(part.getInputStream()).length))));
		        		} else {
		        			cmmFile.setFileSize(Long.parseLong(String.valueOf(Math.round(part.getSize() * 0.7315))));
		        		}

		                if (indexList == null) {
		                	fileList.add(cmmFile);
		                } else if (indexList.contains(-1) || indexList.contains(fileIndex)) { //다운로드할 파일 순번 파라미터가 있으면
		                	fileList.add(cmmFile);
		                	// this part is attachment
			                File f = new File(filePath , fileName + ext);
			                cmmFile.setSaveFileName(fileName + ext);
			                cmmFile.setFilePath(FileUtil.relative(new File(TEMP_DIR), new File(filePath)));
			                part.saveFile(f);   //To save the attachment file at specific location.
			                fileIndex++;
		            	} else {
		            		fileIndex++;
		            	}
		            }
		            getCmmFileList(part, indexList);
		        }
            }
	    }

		if (!p.isMimeType("multipart/*") && !p.isMimeType("message/rfc822")) {
			//본문에 삽입된 이미지가 아닐 경우에만
			if ("inline".equalsIgnoreCase(p.getDisposition()) && p.getHeader("Content-ID") == null
					&& StringUtils.isNotEmpty(p.getFileName())) {
				 ContentType subContentType = new ContentType(p.getContentType());
		    		String fileName = IdGenerator.generateUniqueId();
		            String originFile = StringUtils.defaultString(decode(p.getFileName()));
		            String ext = FileUtil.getFileExtn(originFile);
	            	if (StringUtils.isNotEmpty(ext)) {
	            		ext = "." + ext;
	            	}
		            EmlFile cmmFile = new EmlFile();
		            cmmFile.setModule("eml");
		            cmmFile.setFileType(subContentType.getBaseType());
	                cmmFile.setFileName(originFile);

	                if (p.getSize() == -1) {
	                	cmmFile.setFileSize(Long.parseLong(String.valueOf(
	                			Math.round(IOUtils.toByteArray(p.getInputStream()).length))));
	        		} else {
	        			cmmFile.setFileSize(Long.parseLong(String.valueOf(Math.round(p.getSize() * 0.7315))));
	        		}

	                if (indexList == null) {
	                	fileList.add(cmmFile);
	                } else if (indexList.contains(-1) || indexList.contains(fileIndex)) { //다운로드할 파일 순번 파라미터가 있으면
	                	fileList.add(cmmFile);
	                	// this part is attachment
		                File f = new File(filePath , fileName + ext);
		                cmmFile.setSaveFileName(fileName + ext);
		                cmmFile.setFilePath(FileUtil.relative(new File(TEMP_DIR), new File(filePath)));
		                ((MimeBodyPart) p).saveFile(f);   //To save the attachment file at specific location.
		                fileIndex++;
	            	} else {
	            		fileIndex++;
	            	}
			}
		}

	    return fileList;
	}

	/**
	 * 본문안에 이미지 목록을 리턴
	 * @param p 메일 part
	 * @return 본문안에 이미지 목록
	 * @throws javax.mail.MessagingException e1
	 * @throws java.io.IOException e2
	 */
	public Map<String, String> getImgList(Part p) throws MessagingException, IOException {
		if (p instanceof Message && !(p instanceof TNEFMimeMessage)) {
			imgList = new HashMap<String, String>();
		}

		String filePath = FileUtil.createTempSaveDir(TEMP_DIR, "eml");

		if (p.isMimeType("application/ms-tnef")) { //익스체인지 메일 서버 메일
			getImgList(TNEFMime.convert(session, p));
		} else if (p.isMimeType("multipart/*")) {
	        // content may contain attachments
	        Multipart multiPart = (Multipart) p.getContent();
	        int numberOfParts = multiPart.getCount();
	        for (int partCount = 0; partCount < numberOfParts; partCount++) {
	            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
	            String[] cids = part.getHeader("Content-ID");
	            if (part.getContent() instanceof BASE64DecoderStream
		                && cids != null) {
	            	String cid = decode(cids[0]);
	            	cid = StringUtils.removeStart(cid, "<");
	            	cid = StringUtils.removeEnd(cid, ">");
	            	String ext = FileUtil.getFileExtn(part.getFileName());
	            	String fileName = IdGenerator.generateUniqueId();
	            	if (StringUtils.isNotEmpty(ext)) {
	            		fileName += "." + ext;
	            	}
	        		//System.out.println("content type" + p.getContentType());
	           		File f = new File(filePath, fileName);
	           		DataOutputStream output = null;
	           		BASE64DecoderStream img = null;
	           		try {
	           			output = new DataOutputStream(
	     	        		new BufferedOutputStream(new FileOutputStream(f)));
	     	           		img = (BASE64DecoderStream) part.getContent();
	     	              	byte[] buffer = new byte[1024];
	     	           		int bytesRead;
	     	           		while ((bytesRead = img.read(buffer)) != -1) {
	     	        	   		output.write(buffer, 0, bytesRead);
	     	           		}
	           		} finally {
	           			if (img != null) {
	           				img.close();
	           			}
	           			if (output != null) {
	           				output.close();
	           			}
	           		}
	           		imgList.put(cid, FileUtil.relative(new File(TEMP_DIR), f));
	        	}
	            getImgList(part);
	        }
	    }

	    return imgList;
	}
}
