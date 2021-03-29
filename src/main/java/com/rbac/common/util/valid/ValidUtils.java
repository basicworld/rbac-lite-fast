package com.rbac.common.util.valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类：邮箱、手机号、字符串长度、字符串内容
 * 
 * @author wlfei
 *
 */
public class ValidUtils {
	/**
	 * 校验手机号
	 * 
	 * @param phoneStr
	 * @return
	 */
	public static boolean isValidPhone(String phoneStr) {
		if (null == phoneStr) {
			return false;
		}

		Pattern pattern = Pattern.compile("^[1]\\d{10}$");
		return pattern.matcher(phoneStr).matches();
	}

	/**
	 * 校验邮箱格式
	 * 
	 * @param emailStr
	 * @return
	 */
	public static boolean isValidEmail(String emailStr) {
		if (null == emailStr) {
			return false;
		}

		final String EMAIL_REGEX = "^\\s*?(.+)@(.+?)\\s*$";
		final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
		Matcher emailMatcher = EMAIL_PATTERN.matcher(emailStr);

		return emailMatcher.matches();
	}

	/**
	 * 字符串长度校验
	 * 
	 * @param testStr
	 * @param sizeFrom 长度最小值应 大于等于 sizeFrom
	 * @param sizeTo   长度最大值应 小于 sizeTo
	 * @return true--符合要求 false--长度不符合要求或null
	 */
	public static boolean isLengthBetween(String testStr, int sizeFrom, int sizeTo) {
		if (null == testStr) {
			return false;
		}
		if (testStr.length() < sizeFrom || testStr.length() >= sizeTo) {
			return false;
		}
		return true;
	}

	/**
	 * 判断以小写字母开头
	 * 
	 * @param testStr
	 * @return
	 */
	public static boolean isStartWithSmallcaseAlphaBet(String testStr) {
		if (null == testStr) {
			return false;
		}
		char c = testStr.charAt(0);
		if (c >= 'a' && c <= 'z') {
			return true;
		}
		return false;
	}

	/**
	 * 判断全部是数字或小写英文字母
	 * 
	 * @param testStr
	 * @return
	 */
	public static boolean isAllNumberOrSmallcaseAlphaBet(String testStr) {
		for (char c : testStr.toCharArray()) {
			if (((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'))) {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * 判断全部是数字
	 * 
	 * @param testStr
	 * @return
	 */
	public static boolean isAllNumber(String testStr) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(testStr).matches();
	}

	/**
	 * 判断全部是小写字母
	 * 
	 * @param testStr
	 * @return
	 */
	public static boolean isAllSmallcaseAlphaBet(String testStr) {
		for (char c : testStr.toCharArray()) {
			if (((c >= 'a' && c <= 'z'))) {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * 判断全部是大写字母
	 * 
	 * @param testStr
	 * @return
	 */
	public static boolean isAllUppercaseAlphaBet(String testStr) {
		for (char c : testStr.toCharArray()) {
			if (((c >= 'A' && c <= 'Z'))) {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * 判断全部是字母，包含大小写
	 * 
	 * @param testStr
	 * @return
	 */
	public static boolean isAllAlphaBet(String testStr) {
		for (char c : testStr.toCharArray()) {
			if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * 判断全部是中文
	 * 
	 * @param testStr
	 * @return
	 */
	public static boolean isChinese(String testStr) {
		char[] chars = testStr.toCharArray();
		boolean isUtf8 = false;
		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;

				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
					isUtf8 = true;
					break;
				}
			}
		}
		return isUtf8;
	}

	public static void main(String[] args) {
		System.out.println(ValidUtils.isAllAlphaBet("asdfASSDA"));
		System.out.println(ValidUtils.isAllSmallcaseAlphaBet("asdf"));
		System.out.println(ValidUtils.isAllUppercaseAlphaBet("ASDFAF"));
		System.out.println(ValidUtils.isAllNumber("1234"));
		System.out.println(ValidUtils.isAllNumberOrSmallcaseAlphaBet("sasdfasd2342"));
		System.out.println(ValidUtils.isStartWithSmallcaseAlphaBet("sasdfasd2342"));
	}
}
