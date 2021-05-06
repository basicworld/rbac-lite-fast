package com.rbac.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author wlfei
 */
public class DateTools extends org.apache.commons.lang3.time.DateUtils {

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/** pattern allowed */
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 日期型字符串转化为日期 格式<br>
	 * 
	 * @param str 日期字符串
	 * @return Date() 或 null--转换失败时
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * Date() 转换为 yyyy-MM-dd HH:mm:ss 格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static final String date2FullStringTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
	}

}
