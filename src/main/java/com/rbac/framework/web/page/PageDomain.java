package com.rbac.framework.web.page;

import org.apache.commons.lang3.StringUtils;

import com.rbac.common.constant.SqlConstants;
import com.rbac.common.util.StringTools;

/**
 * 分页数据<br>
 * 包含字段：pageNum pageSize orderByColumn isAsc
 *
 * @author ruoyi
 */
public class PageDomain {
    /** 当前记录起始索引 */
    private Integer pageNum;
    /** 每页显示记录数 */
    private Integer pageSize;
    /** 排序列 */
    private String orderByColumn;
    /** 排序的方向 "desc" 或者 "asc". */
    private String isAsc;

    /**
     * 获取排序字段
     *
     *
     * @return 排序字段以及排序方式，例子：<br>
     *         假设排序字段名字是column2，没有指定正序或逆序时：column2 asc<br>
     *         假设排序字段名字是column2，指定正序时：column2 asc<br>
     *         假设排序字段名字是column2，指定逆序时：column2 desc<br>
     *         空字符--如果排序字段为空或null
     */
    public String getOrderBy() {
        if (StringUtils.isBlank(orderByColumn)) {
            return "";
        }
        return StringTools.toUnderScoreCase(orderByColumn) + " " + StringUtils.defaultString(isAsc, SqlConstants.ASC);
    }

    /**
     * 获取分页数据的当前页号
     *
     *
     * @return
     */
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 获取分页数据的每页元素个数
     *
     *
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取排序依据列
     */
    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    /**
     * 获取排序列的排序方式，asc或desc
     *
     *
     * @return
     */
    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }
}
