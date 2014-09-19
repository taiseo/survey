package com.freeneo.survey.util;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util {

	static Logger logger = LoggerFactory.getLogger(Util.class);
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static String toKB(String sByte) {
		if (sByte == null || checkStr(sByte).equals("")) {
			return "0kB";
		}
		int iByte = Integer.parseInt(sByte);
		int iKByte = iByte / 1024;

		return iKByte + "kB";
	}

	public static String getExtImage(String file_name) {
		String ext = file_name.substring(file_name.lastIndexOf(".") + 1);
		if (ext.equalsIgnoreCase("hwp")) {
			return "icon_hwp.gif";
		} else if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
			return "icon_jpg.gif";
		} else if (ext.equalsIgnoreCase("pdf")) {
			return "icon_pdf.gif";
		} else if (ext.equalsIgnoreCase("zip")) {
			return "icon_zip.gif";
		} else if (ext.equalsIgnoreCase("gif")) {
			return "icon_gif.gif";
		}
		return "icon_etc.gif";
	}

	public static String toWon(String src) {

		if (src == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < src.length(); i++) {
			sb.append(src.charAt(src.length() - i - 1));
			if (i % 3 == 2) {
				sb.append(",");
			}
		}
		String result = sb.toString();

		sb = new StringBuffer();
		for (int i = 0; i < result.length(); i++) {
			sb.append(result.charAt(result.length() - i - 1));
		}
		result = sb.toString();
		if (result.startsWith(",")) {
			result = result.substring(1);
		}

		return result;

	}

	/*
	 * public static OracleResultSet getOracleResultSet(ResultSet rs) {
	 * logger.debug("[OracleUtil.getOracleResultSet] Original Set : " + rs); if
	 * (rs instanceof org.apache.commons.dbcp.DelegatingResultSet) {
	 * DelegatingResultSet drs = (org.apache.commons.dbcp.DelegatingResultSet)
	 * rs; rs = drs.getInnermostDelegate(); // .getOriginalResultSet();
	 * logger.debug("[OracleUtil.getOracleResultSet] Delegating Set : " + rs); }
	 * 
	 * if (rs instanceof OracleResultSet) { return (OracleResultSet) rs; }
	 * 
	 * return null; }
	 */
	public static String toUTF8(String s) {
		try {
			if (s != null) {
				return new String(s.getBytes(), "UTF-8");
			} else {
				return s;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("MyUtil-UTF-8() Exception: " + e);
			return null;
		}
	}

	/**
	 * ???? ???????????? ??? ??;?? ???��?.(UTF-8?? 8859_1???? ???????.)
	 *
	 * @param s
	 *            ?????
	 * @return String 8859_1 ?????
	 */
	public static String to8859(String s) {
		try {
			if (s != null) {
				return new String(s.getBytes("EUC_KR"), "8859_1");
			} else {
				return s;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("MyUtil-to8859() Exception: " + e);
			return null;
		}
	}

	/**
	 * ???? ???????????? ??? ??n??? ???��?.(8859_1??? UTF-8?? ???????.)
	 *
	 * @param s
	 *            ?????
	 * @return String UTF-8 ?????
	 */
	public static String toEUCKR(String s) {
		try {
			if (s != null) {
				return new String(s.getBytes("8859_1"), "EUC_KR");
			} else {
				return s;
			}

		} catch (Exception e) {
			System.out.println("CoUtil-toEUCKR() Exception: " + e);
			return null;
		}
	}

	/**
	 * ???? ???????????? ??? ??n??? ???��?.(8859_1??? UTF-8?? ???????.)
	 *
	 * @param s
	 *            ?????
	 * @return String UTF-8 ?????
	 */
	public static String toEUCKRDB(String s) {
		try {
			if (s != null) {
				return new String(s.getBytes("8859_1"), "EUC_KR");
			} else {
				return s;
			}

		} catch (Exception e) {
			System.out.println("CoUtil-toEUCKR() Exception: " + e);
			return null;
		}
	}

	/**
	 * ???? ?????? ?????; ????? UTF-8?????? ?????8?? ??????.
	 *
	 * @param header
	 *            ?????
	 * @param charSet
	 *            ?????
	 * @return String UTF-8 ?????
	 */
	public static String decode(String header, String charSet) {
		String value = null;
		try {
			value = new String(header.getBytes(charSet), "EUC_KR");
		} catch (Exception e) {
			value = null;
		}
		return value;
	}

	/**
	 * ???? TEXT?? HTML????? ???????. -> TEXT?? ??? <????????>
	 *
	 * @param s
	 *            TEXT
	 * @return String HTML
	 */
	public static String toHTML(String s) {
		String html = null;
		if (s != null) {
			html = replace(s, "'", "''");
		}
		return html;
	}

	/**
	 * ???? '\n'?? <BR>
	 * ?? ???? ??? Method
	 *
	 * @param s
	 *            ?????
	 * @return String ??????
	 */
	public static String toBR(String s) {
		String str = null;
		if (s != null) {
			str = replace(s, "\n", "<BR>");
		}
		return str;
	}

	/**
	 * ???? HTML; Text?? ??????? Method
	 *
	 * @param s
	 *            HTML?????
	 * @return String Text?????
	 */
	public static String toText(String s) {
		String text = null;
		if (s != null) {
			text = replace(s, "<BR>", "\n");
		}
		return text;
	}

	/**
	 * ???? ????? ??????? ????
	 *
	 * @param s
	 *            ?????
	 * @param old
	 *            ???????
	 * @param replacement
	 *            ???????
	 * @return String ??? ?????
	 */
	public static String replace(String s, String old, String replacement) {
		int i = s.indexOf(old);
		StringBuffer r = new StringBuffer();

		if (i == -1) {
			return s;
		}
		r.append(s.substring(0, i) + replacement);

		if (i + old.length() < s.length()) {
			r.append(replace(s.substring(i + old.length(), s.length()), old,
					replacement));

		}
		return r.toString();
	}

	/***************************************************************************
	 * // oracle?? ????? ????????? ??? ???? // ' ??f?? ?????? ????
	 ****************************************************************************/
	public static String replace1(String src, String oldstr, String newstr) {
		if (src == null) {
			return null;
		}

		StringBuffer dest = new StringBuffer("");
		int len = oldstr.length();
		int srclen = src.length();
		int pos = 0;
		int oldpos = 0;

		while ((pos = src.indexOf(oldstr, oldpos)) >= 0) {
			dest.append(src.substring(oldpos, pos));
			dest.append(newstr);
			oldpos = pos + len;
		}

		if (oldpos < srclen) {
			dest.append(src.substring(oldpos, srclen));

		}
		return dest.toString();
	}

	/**
	 * ???? ??????? "" null?? ??? ???? ???? ????? ??????? ??? ????
	 *
	 * @param str
	 *            ?????
	 * @return String "", null ?? ??? ?????
	 */
	public static String getStr(String source) {
		return (source == null || source.trim().length() == 0) ? "" : source;
	}

	/**
	 * ???? ?????; ??d?? ???? ??? ??? ??? ????
	 *
	 * @param str
	 *            ?????
	 * @param len
	 *            ????
	 * @return String ?????
	 */
	public static String cutStr(String str, int len) {
		str = getStr(str);
		if (str.length() > len) {
			str = str.substring(0, len);
		}
		return str;
	}

	/**
	 * ???? ?????; ??d?? ???? ??? ??? ??? ????
	 *
	 * @param str
	 *            ?????
	 * @param len
	 *            ????
	 * @return String ?????
	 */
	public static String cutStrDot(String str, int len) {
		str = getStr(str);
		if (str.length() > len) {
			str = str.substring(0, len) + "...";
		}
		return str;
	}

	/**
	 * ???? ????? ; ??????8?? ???? ??? Method
	 *
	 * @param pageString
	 *            ?????
	 * @return int ??????
	 */
	public static int getPageNum(String pageString) {
		int pagenum = 1;
		try {
			if (pageString != null) {
				pagenum = Integer.parseInt(pageString);
			}
			if (pagenum < 1) {
				pagenum = 1;
			}
		} catch (Exception e) {
			pagenum = 1;
		}
		return pagenum;
	}

	/**
	 * ???? ??? ??;\uFFFD\uFFFD [ ' ] ??? (?????)
	 *
	 * @param content
	 *            ?????
	 * @return String ??????
	 */
	public static String convertAcute(String content) {
		int i = 0;

		int len = content.length();
		StringBuffer dest = new StringBuffer(len + 500);
		for (i = 0; i < len; i++) {

			if (content.charAt(i) == '\'') {
				dest.append("&acute;");
			} else {
				dest.append(content.charAt(i));
			}
		}
		return dest.toString();

	}

	/**
	 * ???? ??? ??;\uFFFD\uFFFD [ ' ] ??? (?????)
	 *
	 * @param content
	 *            ?????
	 * @return String ??????
	 */
	public static String convertAcute2(String content) {
		int i = 0;

		int len = content.length();
		StringBuffer dest = new StringBuffer(len + 500);
		for (i = 0; i < len; i++) {

			if (content.charAt(i) == '\'') {
				dest.append("\"");
			} else {
				dest.append(content.charAt(i));
			}
		}
		return dest.toString();

	}

	/**
	 * <pre>
	 * format ???��? ????(?????) ????? ????????. <br>
	 * format?? "yyyy-MM-dd HH:mm:ss"?? ???
	 * ?????: "2004-01-01 15:00:01"
	 * 
	 * Symbol   Meaning                 Presentation        Example
	 *       ------   -------                 ------------        -------
	 *       y        year                    (Number)            1996
	 *       M        month in year           (Text & Number)     July & 07
	 *       d        day in month            (Number)            10
	 *       h        hour in am/pm (1~12)    (Number)            12
	 *       H        hour in day (0~23)      (Number)            0
	 *       m        minute in hour          (Number)            30
	 *       s        second in minute        (Number)            55
	 *       S        millisecond             (Number)            978
	 *        -----------------------------------------------------------------
	 * </pre>
	 *
	 * @param format
	 * @return
	 */
	public static String systemDate(String format) {
		/*
		 * Calendar cd = new
		 * java.util.GregorianCalendar(java.util.Locale.KOREAN);
		 * 
		 * String year = String.valueOf(cd.get(cd.YEAR)); String month =
		 * addZero(String.valueOf(cd.get(cd.MONTH) + 1), 2); String day =
		 * addZero(String.valueOf(cd.get(cd.DAY_OF_MONTH)), 2); String hour =
		 * addZero(String.valueOf(cd.get(cd.HOUR_OF_DAY)), 2); String minute =
		 * addZero(String.valueOf(cd.get(cd.MINUTE)), 2); String second =
		 * addZero(String.valueOf(cd.get(cd.SECOND)), 2);
		 * 
		 * return (year + month + day + hour + minute + second);
		 */
		// return format(year + month + day, format_str);

		String returnValue = null;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format, java.util.Locale.KOREA);
		java.util.Date currentTime = new java.util.Date();
		returnValue = formatter.format(currentTime);

		if (returnValue == null) {
			returnValue = "";

		}
		return returnValue;
	}

	public static String getPlusMonth(int month) {
		Calendar cd = new GregorianCalendar(Locale.KOREAN);
		cd.add(2, month);
		String year = String.valueOf(cd.get(1));
		String new_month = addZero(String.valueOf(cd.get(2) + 1), 2);
		String day = addZero(String.valueOf(cd.get(5)), 2);
		return (new StringBuilder(String.valueOf(year))).append(new_month)
				.append(day).toString();
	}

	public static String getPlusDate(int iDay) {
		Calendar temp = Calendar.getInstance();
		StringBuffer sbDate = new StringBuffer();
		temp.add(5, iDay);
		int nYear = temp.get(1);
		int nMonth = temp.get(2) + 1;
		int nDay = temp.get(5);
		sbDate.append(nYear);
		if (nMonth < 10)
			sbDate.append("0");
		sbDate.append(nMonth);
		if (nDay < 10)
			sbDate.append("0");
		sbDate.append(nDay);
		return sbDate.toString();
	}

	/**
	 * ???? ?????; ?????? ??????? ?????.<br>
	 * ??f) formating("1234", "##-#-#") --> "12-3-4"
	 * 
	 * @param source
	 *            String
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formating(String source, String format) {
		StringBuffer return_str = new StringBuffer();
		int idx = 0;
		int i;

		if (source == null) {
			return source;
		}
		if (source.length() == 0) {
			return source;
		}

		for (i = 0; i < format.length(); i++) {
			if (format.charAt(i) == '#') {
				if (source.length() <= idx) {
					break;
				}
				return_str.append(source.charAt(idx++));
			} else {
				return_str.append(format.charAt(i));
			}
		}

		return return_str.toString();
	}

	/**
	 * Null ?????; ???????? ????? ????? ????? ??????? ???; ??f???.
	 *
	 * @param str
	 * @return
	 */
	public static String checkStr(String str) {
		if (str != null) {
			return str.trim();
		} else {
			return "";
		}
	}

	/**
	 * ???????? /??? ????? x????? ??? ??;, ?????? NULL; ???????.
	 * 
	 * @param str
	 *            String
	 * @return NULL or ??
	 */
	public static String chkNull(String str) {
		if (str == null) {
			return null;
		}
		if (str.trim().length() == 0) {
			return null;
		}

		return str;
	}

	public static String randomNumber(int size) {
		StringBuffer returnValue = new StringBuffer();
		Random random = new Random();

		returnValue.delete(0, returnValue.length());
		for (int i = 0; i < size; i++) {
			returnValue.append(random.nextInt(9));
		}
		return returnValue.toString();
	}

	/**
	 * @param str
	 * @param size
	 * @return
	 */
	public static String addZero(String str, int size) {
		String result = "";

		if (size < str.length()) {
			return str;
		}

		for (int i = 0; i < size - str.length(); i++) {
			result = result + "0";

		}
		return result + str;
	}

	public static BigDecimal 소수점한자리수로(double distance_original) {
		BigDecimal bD = new BigDecimal(distance_original / 1000);
		BigDecimal distance = bD
				.setScale(1, java.math.BigDecimal.ROUND_HALF_UP);
		return distance;
	}

	public static String implode(String[] ary, String delim) {
		String out = "";
		for (int i = 0; i < ary.length; i++) {
			if (i != 0) {
				out += delim;
			}
			out += ary[i];
		}
		return out;
	}

	public static String implode(Vector<String> strs, String delim) {
		String out = "";
		for (int i = 0; i < strs.size(); i++) {
			if (i != 0) {
				out += delim;
			}
			out += strs.get(i);
		}
		return out;
	}

	public static String getFrequencyFromChannel(int channel) {
		int Channels[] = new int[] { 183, 184, 185, 187, 188, 189, 192, 196, 7,
				8, 9, 11, 12, 16, 34, 36, 38, 40, 42, 44, 46, 48, 52, 56, 60,
				64, 100, 104, 108, 112, 116, 120, 124, 128, 132, 136, 140, 149,
				153, 157, 161, 165 };
		int Frequencies[] = new int[] { 4915, 4920, 4925, 4935, 4940, 4945,
				4960, 4980, 5035, 5040, 5045, 5055, 5060, 5080, 5170, 5180,
				5190, 5200, 5210, 5220, 5230, 5240, 5260, 5280, 5300, 5320,
				5500, 5520, 5540, 5560, 5580, 5600, 5620, 5640, 5660, 5680,
				5700, 5745, 5765, 5785, 5805, 5825 };
		for (int i = 0; i < Channels.length; i++) {
			int ch = Channels[i];
			if (channel == ch) {
				int frequency = Frequencies[i];
				String frequencyString = "";
				if (frequency >= 1000) {
					frequencyString = frequency / 1000.0 + "GHz";
				} else {
					frequencyString = frequency + "MHz";
				}
				return frequencyString;
			}
		}

		return "";
	}

	/**
	 * 쉼표로만 구분된 문자열을 받아서 where in (...) 쿼리 안에 들어갈 수 있게 각 sess_id를 작은
	 * 따옴표(')로 감싼다.
	 * 
	 * @param commaStr
	 * @return
	 */
	public static String makeSessIdsToInQueryString(String commaStr) {
		return "'" + commaStr.replace(",", "','") + "'";
	}

	public static String getTimeDiff(String startTime) {
		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(startTime);
			d2 = new Date();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Get msec from each, and subtract.
		long millis = d2.getTime() - d1.getTime();

		String hms = String.format(
				"%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(millis)));

		return hms;

	}

	public static String getPrintr(Object obj) {
		return gson.toJson(obj);
	}

	public static void printr(Object obj) {
		System.out.print(gson.toJson(obj));
	}

	public static boolean isLocal(HttpServletRequest request){
		return request.getRemoteAddr().equals("127.0.0.1") || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1");
	}
	
	public static String getUri(HttpServletRequest request){
		String uri = request.getScheme() + "://" +
	             request.getServerName() + 
	             ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
	             request.getRequestURI() +
	            (request.getQueryString() != null ? "?" + request.getQueryString() : "");
		return uri;
	}
	
	/**
	 * 2014-09-26 형태의 날짜를 비교한다.
	 * 오늘을 기준으로. 오늘이 더 크면 1, 같으면 0, 오늘이 더 작으면 -1 리턴.
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int compareWithToday(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateParsed = formatter.parse(date);
		Calendar cal = Calendar.getInstance();
		Date today = formatter.parse(formatter.format(cal.getTime()));
		
		logger.debug("String date = {}", date);
		logger.debug("String formatted today = {}", formatter.format(cal.getTime()));
		logger.debug("Date dateParsed = {}", dateParsed);
		logger.debug("Date today = {}", today);
		logger.debug("today.compareTo(dateParsed) = {}", today.compareTo(dateParsed));
		
		return today.compareTo(dateParsed);
	}
}
