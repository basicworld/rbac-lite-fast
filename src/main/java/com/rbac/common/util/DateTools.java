package com.rbac.common.util;

import java.text.ParseException;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author wlfei
 */
public class DateTools extends org.apache.commons.lang3.time.DateUtils {

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

}
