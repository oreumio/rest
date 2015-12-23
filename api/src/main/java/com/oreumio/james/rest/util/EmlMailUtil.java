package com.oreumio.james.rest.util;

import com.oreumio.james.rest.AppException;
import com.oreumio.james.rest.form.EmlAddressVo;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <pre>
 * 메일 관련 유틸
 * </pre>
 * 
 * @author  : doyoung Choi
 * @version : 1.0.0
 */
public final class EmlMailUtil {
	/**
	 * Constructor.
	 */
	private EmlMailUtil() { }

	/**
	 * 보안 메일 html을 생성한다.
	 * @param url 메일서버 URL
	 * @param messageSource 다국어
	 * @return 설정 값
	 */
	public static String getSecHtml(String url, MessageSource messageSource) {
        String html = "<div style=\"margin: 0px 0 0\">"
				+ "<p style=\"color: #333;\">"
				+ messageSource.getMessage("mail.desc.clickToShowMailPlz",
								null, "", LocaleContextHolder.getLocale()) /*보안 메일입니다. 아래 [메일 보기] 를 클릭하세요.*/
				+ "</p>"
				+ "<a href=\"" + url + "/sub/emlMail/emlSecurity.do?secMailId=%s\" target=\"blank\" title=\""
				+ messageSource.getMessage("common.tooltip.newWindow",
								null, "", LocaleContextHolder.getLocale()) /*새창*/
				+ "\" style=\"text-decoration:none; display: inline-block; min-width: 27px; height: 25px;"
				+ " line-height: 26px;padding: 0 10px 0 10px; margin: 15px 0 0; border-style: solid;"
				+ " border-color: #ccc;"
				+ " border-width: 1px; border-radius:3px; -webkit-border-radius:3px; background-color: #fff;"
				+ " color: #333;\">"
				+ messageSource.getMessage("mail.label.showMail",
						null, "", LocaleContextHolder.getLocale()) /*메일 보기*/
				+ "</a>"
				+ "</div>";
		return html;
	}

	/**
	 * 프로퍼티 파일 읽기
	 * @param fileName 설정 파일명
	 * @param key 키값
	 * @return 설정값
	 */
	public static String getProperty(String fileName, String key) {
		PropertiesConfiguration configuration;
		try {
			configuration = new PropertiesConfiguration(fileName);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		return configuration.getString(key);
	}

	/**
	 * 메일함 코드 알아내기
	 * @param mailboxKind 메일함 종류
	 * @param mailboxPath 메일함 물리적 경로
	 * @return 메일함 코드
	 */
	public static String getMailBoxCode(String mailboxKind, String mailboxPath) {
		//메일함 코드 지정
		if ("U".equals(mailboxKind)) { //개인 메일함인지 확인
			return "personal";
		} else if (mailboxPath.equals(EmlCommonConstants.RECV_BOX)) {
			return "recv";
		} else if (mailboxPath.equals(EmlCommonConstants.DRAFT_BOX)) {
			return "temp";
		} else if (mailboxPath.equals(EmlCommonConstants.RESERVED_BOX)) {
			return "reserv";
		} else if (mailboxPath.equals(EmlCommonConstants.SENT_BOX)) {
			return "sent";
		} else if (mailboxPath.equals(EmlCommonConstants.SPAM_BOX)) {
			return "spam";
		} else if (mailboxPath.equals(EmlCommonConstants.TRASH_BOX)) {
			return "del";
		}
		return "";
	}

	/**
	 * 기본 메일함 경로로 다국어 메일함명을 얻는다.
	 * @param mailboxPath 메일함 경로
	 * @param messageSource 다국어
	 * @return 다국어 메일함명
	 */
	public static String getMailboxName(String mailboxPath, MessageSource messageSource) {
		if (EmlCommonConstants.RECV_BOX.equals(mailboxPath)) {
            /*"받은메일"*/
            mailboxPath = messageSource.getMessage("mail.label.receivedMail", null, "",
            		LocaleContextHolder.getLocale());
		} else if (EmlCommonConstants.SENT_BOX.equals(mailboxPath)) {
            /*"보낸메일"*/
            mailboxPath = messageSource.getMessage("mail.label.sentMail", null, "", LocaleContextHolder.getLocale());
        } else if (EmlCommonConstants.SPAM_BOX.equals(mailboxPath)) {
            /*"스팸메일"*/
            mailboxPath = messageSource.getMessage("mail.label.spamMail", null, "", LocaleContextHolder.getLocale());
        } else if (EmlCommonConstants.TRASH_BOX.equals(mailboxPath)) {
            /*"삭제메일"*/
			mailboxPath = messageSource.getMessage("mail.label.deletedMail", null, "", LocaleContextHolder.getLocale());
        } else if (EmlCommonConstants.DRAFT_BOX.equals(mailboxPath)) {
            /*"임시보관"*/
			mailboxPath = messageSource.getMessage("mail.label.tempStorage", null, "", LocaleContextHolder.getLocale());
		} else if (EmlCommonConstants.RESERVED_BOX.equals(mailboxPath)) {
            /*"예약메일"*/
			mailboxPath = messageSource.getMessage("mail.label.reservationMail", null, "",
					LocaleContextHolder.getLocale());
        } else {
			String[] mailboxPathArr = StringUtils.split(mailboxPath, ".");
			mailboxPath = mailboxPathArr[mailboxPathArr.length - 1];
		}

		return mailboxPath;
	}

	/**
	 * 메일 주소 목록을 인코딩해서 리턴
	 * @param addressList 이메일 주소 목록
	 * @param charset 인코딩 타입
	 * @param messageSource 다국어
	 * @return 이메일 주소 목록
	 */
	public static InternetAddress[] getInternetAddress(InternetAddress[] addressList, String charset,
			MessageSource messageSource) {
		for (InternetAddress addr : addressList) {
			try {
				String name = addr.getPersonal();
				if (StringUtils.isNotEmpty(name)) {
    				addr.setPersonal(name, charset);
    			}
				addr.validate();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (AddressException e) {
				e.printStackTrace();
				throw new AppException(messageSource.getMessage("mail.desc.incorrectEmailAddressType",
						null, "", LocaleContextHolder.getLocale()));
			}
		}
		return addressList;
	}

	/**
	 * 메일 주소 문자열을 InternetAddress 객체 목록으로 리턴
	 * @param mailTos 이메일 주소 목록
	 * @return InternetAddress 객체 목록 목록
	 */
	public static List<InternetAddress> getInternetAddressList(String mailTos) {
		List<InternetAddress> addrList = new ArrayList<InternetAddress>();
		String[] addressList =  StringUtils.split(mailTos, ",");
		if (addressList == null) {
			return addrList;
		}
		for (String addr : addressList) {
			InternetAddress internetAddress;
			try {
				internetAddress = new InternetAddress(addr);
			} catch (AddressException e) {
				continue;
			}
			addrList.add(internetAddress);
		}
		return addrList;
	}

    /**
     * 자바스크립트 제거
     * @param data html
     * @return 자바스크립트 제거된 html
     */
    public static String removeScriptTag(String data) {
    	StringBuilder regex = new StringBuilder("<script[^>]*>(.*?)</script>");
    	int flags = Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
    	Pattern pattern = Pattern.compile(regex.toString(), flags);
    	Matcher matcher = pattern.matcher(StringUtils.defaultString(data));
    	return matcher.replaceAll("");
    }

    /**
     * 수신확인 웹버그 제거
     * @param data html
     * @return 웹버그 제거된 html
     */
    public static String removeWebBug(String data) {
    	StringBuilder regex = new StringBuilder("<!--_webbug_:S-->(.*?)<!--_webbug_:E-->");
    	int flags = Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
    	Pattern pattern = Pattern.compile(regex.toString(), flags);
    	Matcher matcher = pattern.matcher(StringUtils.defaultString(data));
    	return matcher.replaceAll("");
    }

    public static InternetAddress[] getInternetAddress(List<? extends EmlAddressVo> emlAddressVos) throws UnsupportedEncodingException {
        InternetAddress[] internetAddresses = new InternetAddress[emlAddressVos.size()];

        for (int i = 0; i < emlAddressVos.size(); i++) {
            internetAddresses[i] = new InternetAddress(emlAddressVos.get(i).getAddress(), emlAddressVos.get(i).getPersonal(), "UTF-8");
        }

        return internetAddresses;
    }

    /**
     * 메일 주소의 이름 영역을 "로 감싼다.
     * @param mailTo 수신자 이메일 주소
     * @return 치환된 이메일 주소
     */
    public static String putDoubleQuotesInAddr(String mailTo) {
    	String[] mailToArr = mailTo.split(",");
    	int size = mailToArr.length;
    	List<String> toList = new ArrayList<String>();

    	for (int i = 0; i < size; i++) {
    		String addr = mailToArr[i].trim();
    		if (addr.endsWith(">")) {
    			int pos = addr.lastIndexOf("<");
    			String name = addr.substring(0, pos).trim();
    			String email = addr.substring(pos, addr.length());
    			if (StringUtils.isNotEmpty(name) && !(name.startsWith("\"") && name.endsWith("\""))) {
    				name = "\"" + name + "\"";
    			}
    			mailToArr[i] = name + email;
    		}
    	}

    	String prevTo = "";
    	for (int i = 0; i < size; i++) {
    		try {
    			String addr = prevTo + mailToArr[i];
    			InternetAddress address = new InternetAddress(addr);
    			address.validate();
    			toList.add(addr);
    			prevTo = "";
    		} catch (AddressException ex) {
    			prevTo += mailToArr[i]; // "테스트,테스트2"<test@test.com> 인경우 예외 처리
    			if (i == (size - 1)) { //마지막에서 오류가 났더라도 일단 넣는다. 유효성 체크는 다음 로직에서 처리하기 위해서
    				toList.add(prevTo);
    			}
    			continue;
    		}
    	}
    	return StringUtils.join(toList, ",");
    }

    /**
     * URL에서 파라미터 정보를 추출한다.
     * @param url URL
     * @return 파라미터 맵
     */
    public static Map<String, String> splitQuery(String url) {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		try {
			String[] urlParts = url.split("\\?");
	        if (urlParts == null || urlParts.length < 2) {
	        	return query_pairs;
	        }
	        String[] pairs = StringUtils.replace(urlParts[1], "&amp;", "&").split("&");
	        for (String pair : pairs) {
	            int idx = pair.indexOf("=");
	            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
	            		URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return query_pairs;
    }

    /**
     * 이름이 포함된 이메일 주소를 특정 형식에 맞게 변경한다.
     * @param email 이메일 주소
     * @return 형식이 변경된 이메일 주소
     */
    public static String removeSpecialChar(String email) {
    	String tempEmail = StringUtils.replaceOnce(StringUtils.defaultString(email), " <", "<");
    	tempEmail = StringUtils.replace(StringUtils.defaultString(tempEmail), ",", "，");
		return StringUtils.replace(StringUtils.defaultString(tempEmail), "\"", "");
    }

    /**
     * 메일 세션 리턴
     * @return 메일 세션
     */
    public static Session getMailSession() {
    	Properties props = System.getProperties();
		props.put("mail.mime.base64.ignoreerrors", "true");
		props.put("mail.mime.decodetext.strict", "false");
		props.put("mail.mime.address.strict", "false");
		props.put("mail.mime.ignoreunknownencoding", "true");
    	return Session.getDefaultInstance(props);
    }
}
