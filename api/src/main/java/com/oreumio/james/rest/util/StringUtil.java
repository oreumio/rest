package com.oreumio.james.rest.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 문자 관련 유틸
 * </pre>
 * 
 * @author  : doyoung Choi
 */
public class StringUtil {

	/**
	 * xss 제거
	 * @param str 본문 내용
	 * @return xss 제거된 내용
	 */
	public static String removeTag(String str) {
		str = StringUtils.replace(str, "<", "&lt;");
		str = StringUtils.replace(str, ">", "&gt;");
		return str;
	}

	/**
	 * xss 제거
	 * @param str 본문 내용
	 * @return xss 제거된 내용
	 */
	public static String removeXSS(String str) {
        // 스크립트 문자열 필터링 (선별함 - 필요한 경우 보안가이드에 첨부된 구문 추가)
	    String str_low = str.toLowerCase();
        if (str_low.contains("javascript") || str_low.contains("script") || str_low.contains("iframe")
        		|| str_low.contains("document") || str_low.contains("vbscript") || str_low.contains("applet")
                 || str_low.contains("embed") || str_low.contains("object")  || str_low.contains("frame")
                 || str_low.contains("grameset") || str_low.contains("layer") || str_low.contains("bgsound")
                 || str_low.contains("alert") || str_low.contains("onblur") || str_low.contains("onchange")
                 || str_low.contains("onclick") || str_low.contains("ondblclick") || str_low.contains("onerror")
                 || str_low.contains("onfocus") || str_low.contains("onload") || str_low.contains("onmouse")
                 || str_low.contains("onscroll") || str_low.contains("onsubmit") || str_low.contains("onunload")
                 || str_low.contains("<link") || str_low.contains("<style") || str_low.contains("</style")
                 || str_low.contains("<base"))
        {
            str = str.replaceAll("(?i)javascript", "x-javascript");
            str = str.replaceAll("(?i)script", "x-script");
            str = str.replaceAll("(?i)iframe", "x-iframe");
            str = str.replaceAll("(?i)document", "x-document");
            str = str.replaceAll("(?i)vbscript", "x-vbscript");
            str = str.replaceAll("(?i)applet", "x-applet");
            str = str.replaceAll("(?i)embed", "x-embed");
            str = str.replaceAll("(?i)object", "x-object");
            str = str.replaceAll("(?i)frame", "x-frame");
            str = str.replaceAll("(?i)grameset", "x-grameset");
            str = str.replaceAll("(?i)layer", "x-layer");
            str = str.replaceAll("(?i)bgsound", "x-bgsound");
            str = str.replaceAll("(?i)alert", "x-alert");
            str = str.replaceAll("(?i)onblur", "x-onblur");
            str = str.replaceAll("(?i)onchange", "x-onchange");
            str = str.replaceAll("(?i)onclick", "x-onclick");
            str = str.replaceAll("(?i)ondblclick", "x-ondblclick");
            str = str.replaceAll("(?i)enerror", "x-enerror");
            str = str.replaceAll("(?i)onfocus", "x-onfocus");
            str = str.replaceAll("(?i)onload", "x-onload");
            str = str.replaceAll("(?i)onmouse", "x-onmouse");
            str = str.replaceAll("(?i)onscroll", "x-onscroll");
            str = str.replaceAll("(?i)onsubmit", "x-onsubmit");
            str = str.replaceAll("(?i)onunload", "x-onunload");
            str = str.replaceAll("(?i)<link", "<x-link");
            str = str.replaceAll("(?i)<style", "<x-style");
            str = str.replaceAll("(?i)</style", "</x-style");
            str = str.replaceAll("(?i)<base", "<x-base");
        }

        if (str.contains("<x-style")) {
        	int flags = Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
        	Pattern p = Pattern.compile("<x-style[^>]*>(.*?)</x-style>", flags);
    		Matcher m = p.matcher(str);
    		str = m.replaceAll("<x-style></x-style>");
        }
        return str;
	}

	/**
	 * style tag 내용 제거
	 * @param str 본문 내용
	 * @return style tag 내용 제거된 내용
	 */
	public static String removeStyle(String str) {
	    String str_low = str.toLowerCase();
        if (str_low.contains("<x-style")) {
        	int flags = Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
        	Pattern p = Pattern.compile("<x-style[^>]*>(.*?)</x-style>", flags);
    		Matcher m = p.matcher(str);
    		str = m.replaceAll("<x-style></x-style>");
        }
        return str;
	}
}
