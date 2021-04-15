package com.rbac.common.util;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    /** date pattern like: 2021 */
    public static String YYYY = "yyyy";

    /** date pattern like: 2021-01 */
    public static String YYYY_MM = "yyyy-MM";

    /** date pattern like: 2021-01-01 */
    public static String YYYY_MM_DD = "yyyy-MM-dd";

    /** date+time pattern like: 20210101123000 */
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /** date+time pattern like: 2021-01-01 12:30:00 */
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /** pattern allowed */
    private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM" };

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期字符串, 格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    /**
     * 获取当前日期+时间字符串, 格式为yyyy-MM-dd HH:mm:ss
     * 
     * @return String
     */
    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取服务器启动时间
     * 
     * @return Date()
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     * 
     * @param endDate 结束时间（大）
     * @param nowDate 当前时间（小）
     * @return String：X天X小时X分钟
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取当前日期+时间字符串, 格式为yyyyMMddHHmmss
     * 
     * @return String
     */
    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    /**
     * 将当前日期转为要求的格式的字符串返回
     * 
     * @param format 要求的日期格式
     * @return String
     */
    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    /**
     * 获取 yyyyMMdd 格式的当前日期字符串，返回如：20180808
     * 
     * @return String
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 获取 yyyy-MM-dd 格式的指定日期字符串，返回如：20180808
     * 
     * @param date 指定日期
     * @return
     */
    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    /**
     * 根据format解析输入的ts日期字符串
     * 
     * @param format 日期格式
     * @param ts     日期字符串
     * @return Date()
     */
    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 根据format格式化输入的date日期，输出格式化的日期字符串
     * 
     * @param format 日期格式
     * @param date   日期
     * @return String
     */
    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

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
