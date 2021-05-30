package com.rbac.framework.web.page;

import com.rbac.common.constant.SqlConstants;
import com.rbac.common.util.ServletUtils;

/**
 * 表格数据处理
 *
 * @author ruoyi
 */
public class TableSupport {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = SqlConstants.PAGE_NUM;

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = SqlConstants.PAGE_SIZE;

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = SqlConstants.ORDER_BY_COLUMN;

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = SqlConstants.IS_ASC;

    /**
     * 封装分页对象
     *
     * @return PageDomain() 包含字段：{pageNum, pageSize, orderByColumn, isAsc}
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        return pageDomain;
    }

    /**
     * 同getPageDomain()， 封装分页对象
     *
     *
     * @return PageDomain() 包含字段：{pageNum, pageSize, orderByColumn, isAsc}
     */
    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}
