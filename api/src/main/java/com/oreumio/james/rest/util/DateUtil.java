package com.oreumio.james.rest.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * com.newriseup.framework.utils
 * DateUtil.java
 * </pre>
 * 
 * @author  : NamSoon Park
 */
public class DateUtil {
	public static long getDateDiff(Date startDate, Date endData){
		
		Calendar todayCal = new GregorianCalendar();
		todayCal.setTime(endData);
		 
		Calendar saleStrDmCal = new GregorianCalendar();
		saleStrDmCal.setTime(startDate);
		     
		long diffMillis = todayCal.getTimeInMillis() - saleStrDmCal.getTimeInMillis();
		long  diff = 0l;
		 
		// 초
		diff = diffMillis / (1000);
		 
		// 분
		diff = diffMillis / (60 * 1000);
		 
		// 시
		diff = diffMillis / (60 * 60 * 1000);
		 
		// 일자
		diff = diffMillis/ (24 * 60 * 60 * 1000);
		
		return diff;
	}
	
	/**
	 * <p>
	 * 현재 년도를 YYYY 형태로 리턴
	 * </p>
	 * 
	 * @return 년도(YYYY)
	 */
	public static String getYear() {

		Calendar cd = new GregorianCalendar();

		return StringUtils.leftPad(Integer.toString(cd.get(Calendar.YEAR)), 4,
				'0');
	}

	/**
	 * <P>
	 * 현재 월을 MM 형태로 리턴
	 * </p>
	 * 
	 * @return 월(MM)
	 */
	public static String getMonth() {

		Calendar cd = new GregorianCalendar();

		return StringUtils.leftPad(Integer.toString(cd.get(Calendar.MONTH) + 1),
				2, '0');
	}
	
	/**
	 * 문자열을 DATE형으로 변경 하는 함수이다.
	 * 
	 * @param pubDate
	 *            the pub date
	 * @return the date
	 */
	public static Date stringToDate(String pubDate) {
		ArrayList<SimpleDateFormat> dateFormatList = new ArrayList<SimpleDateFormat>();

		dateFormatList.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
		dateFormatList.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("yyMMddHHmmssZ"));
		dateFormatList.add(new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z"));
		dateFormatList.add(new SimpleDateFormat("EEE, MMM d, ''yy"));
		dateFormatList.add(new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa"));
		dateFormatList.add(new SimpleDateFormat("yyyy+MM+dd'T'H:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("yyyy+MM+dd'T'H:mm:ssZ"));
		dateFormatList.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
		dateFormatList.add(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z"));
		dateFormatList.add(new SimpleDateFormat("E, d MMM yyyy hh:mm:ss z"));
		dateFormatList.add(new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z"));
		dateFormatList.add(new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z"));
		dateFormatList.add(new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss z"));
		dateFormatList.add(new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("E, d MMM yyyy hh:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("yyyy+MM+dd H:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ"));
		dateFormatList.add(new SimpleDateFormat("yyyyMMddHHmmssz"));
		dateFormatList.add(new SimpleDateFormat("yyyyMMddHHmmssZ"));
		dateFormatList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("yyyy+MM+dd h:mm:ss a"));
		dateFormatList.add(new SimpleDateFormat("dd MMM yyyy HH:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("d MMM yyyy HH:mm:ss"));
		dateFormatList.add(new SimpleDateFormat("yyyyMMddHHmmss.SSS"));
		dateFormatList.add(new SimpleDateFormat("yyyyMMddHHmmss"));
		dateFormatList.add(new SimpleDateFormat("yyyy/MM/dd"));
		dateFormatList.add(new SimpleDateFormat("yyyy-MM-dd"));
		dateFormatList.add(new SimpleDateFormat("yyyyMMdd"));

		Date date = null;
		pubDate = pubDate.replaceAll("GMT", "").trim();
		for (int i = 0; i < dateFormatList.size(); i++) {
			try {
				date = ((SimpleDateFormat) dateFormatList.get(i))
						.parse(pubDate);
				if (date != null) {
					i = dateFormatList.size();
				}
					
			} catch (ParseException e) {
				;
			}
		}
		if (date == null) {
			date = null;
		}
		return date;
	}
	
	/**
	 * 지정된 날짜만큼 더하거나 뺀 날짜를 구한다.
	 *
	 * @param date (yyyyMMdd)
	 * @param days int
	 * @return the string
	 */
	public static String dateAdd(Date date, int days) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String toDate = dateFormat.format(new Date());
	    return dateAdd(toDate, days);
	}
	
	/**
	 * 지정된 날짜만큼 더하거나 뺀 날짜를 구한다.
	 *
	 * @param date (yyyyMMdd)
	 * @param days int
	 * @return the string
	 */
	public static String dateAdd(String date, int days) {
	    Calendar cal = Calendar.getInstance();
	    int year=0, month=0, day=0;
	    try {
			year   = Integer.parseInt(date.substring(0,4));
		    month  = Integer.parseInt(date.substring(4,6));
		    day    = Integer.parseInt(date.substring(6,8));
	    } catch (Exception e) {}
		cal.set(year, month-1, day+days);
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(cal.getTime());
	}
	
	/**
	 * 오늘 날짜를 입력받은 패턴에 맞게 포맷한 날짜 문자열을 반환한다.<br>
	 *
	 * @param pattern
	 *            구하고자 하는 날짜 패턴
	 * @return String 입력받은 패턴에 맞게 생성된 날짜 문자열
	 */
	public static String getDate(String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * 오늘 날짜를 입력받은 패턴에 맞게 포맷한 날짜 문자열을 반환한다.<br>
	 *
	 * @param timeZone 타임존 GMT+09:00
	 * @return String 입력받은 타임존에 맞게 생성된 날짜 문자열
	 */
	public static String getDateByTimeZone(String timeZone) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		f.setTimeZone(TimeZone.getTimeZone(timeZone));
		return f.format(new Date());
	}

	/**
	 * 서버의 타임존을 알아낸다.
	 * @return 타임존 시간(+09:00)
	 */
	public static String getCurrentTimezoneOffset() {

	    TimeZone tz = TimeZone.getDefault();  
	    Calendar cal = GregorianCalendar.getInstance(tz);
	    int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

	    String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
	    offset = (offsetInMillis >= 0 ? "+" : "-") + offset;

	    return offset;
	} 
}
