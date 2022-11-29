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
     * 校验手机号格式
     *
     *
     * @param phoneStr
     * @return 如果传入null，则返回false<br>
     *         如果传入字符是11位的、全部是数字、并且以数字1开头，返回true<br>
     *         其他情况返回false
     */
    public static boolean isValidPhone(String phoneStr) {
        if (null == phoneStr) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(phoneStr).matches();
    }

    /**
     * 校验邮箱格式:
     *
     * xxx@xxx.xxx
     *
     * @param emailStr
     * @return 如果入参为null，则返回false<br>
     *         如果入参符合邮箱格式，则返回true<br>
     *         其他情况返回false
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
     * @param minSize 长度最小值应 大于等于 minSize
     * @param maxSize 长度最大值应 小于 maxSize
     * @return true--符合要求<br>
     *         false--长度不符合要求或null
     */
    public static boolean isLengthBetween(String testStr, int minSize, int maxSize) {
        if (null == testStr) {
            return false;
        }
        if (testStr.length() < minSize || testStr.length() >= maxSize) {
            return false;
        }
        return true;
    }

    /**
     * 判断以小写字母开头
     *
     * @param testStr
     * @return true--以小写字母开头<br>
     *         false--不是小写字母开头或者入参为null
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
     * 判断全部是数字或小写英文字母<br>
     *
     * 入参不能为null，否则抛异常
     *
     * @param testStr
     * @return true--入参全是小写字母或数字<br>
     *         false--其他情况
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
     * @return true--入参全部是数字<br>
     *         false--其他情况
     */
    public static boolean isAllNumber(String testStr) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(testStr).matches();
    }

    /**
     * 判断全部是小写字母<br>
     * 入参不能为null
     *
     * @param testStr
     * @return true--入参全部是小写字母<br>
     *         false--其他情况
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
     * 判断全部是大写字母<br>
     * 注意入参不能为null
     *
     * @param testStr
     * @return true--入参全部是大写字母<br>
     *         false--其他情况
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
     * 判断全部是字母，包含大小写<br>
     * 入参不能为null
     *
     * @param testStr
     * @return true--入参全部是大写或小写字母<br>
     *         false--其他情况
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
     * 判断全部是中文<br>
     * 入参不能为null<br>
     * https://www.cnblogs.com/littlebob/p/13218053.html
     *
     * @param testStr
     * @return true--入参全部是中文字符<br>
     *         false--其他情况<br>
     *
     *
     */
    public static boolean isAllChinese(String testStr) {
        if (testStr == null) {
            return false;
        }
        char[] ch = testStr.toCharArray();
        for (char c : ch) {
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是中文字符<br>
     * 包含中文标点符号<br>
     *
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C) {
            return true;
        } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D) {
            return true;
        } else if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(ValidUtils.isAllAlphaBet("asdfASSDA"));
        System.out.println(ValidUtils.isAllSmallcaseAlphaBet("asdf"));
        System.out.println(ValidUtils.isAllUppercaseAlphaBet("ASDFAF"));
        System.out.println(ValidUtils.isAllNumber("1234"));
        System.out.println(ValidUtils.isAllNumberOrSmallcaseAlphaBet("sasdfasd2342"));
        System.out.println(ValidUtils.isStartWithSmallcaseAlphaBet("sasdfasd2342"));
        System.out.println(ValidUtils.isAllChinese("中国"));
    }
}
