package com.rbac.common.util.sql;

import org.apache.commons.lang3.StringUtils;

/**
 * sql操作工具类
 *
 * @author ruoyi
 */
public class SqlUtil {
    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new RuntimeException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

    /**
     * 获取全模糊查询所用的参数 不建议用，sql性能不好
     *
     * @param param
     * @return %param%
     */
    public static String getFuzzQueryParam(String param) {
        return "%" + param + "%";
    }

    /**
     * 获取左模糊查询所用的参数
     *
     * @param param
     * @return %param
     */
    public static String getLeftFuzzQueryParam(String param) {
        return "%" + param;
    }

    /**
     * 获取右模糊查询所用的参数 推荐使用，sql性能最好
     *
     * @param param
     * @return param%
     */
    public static String getRightFuzzQueryParam(String param) {
        return param + "%";
    }
}
