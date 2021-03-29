package com.rbac.system.base;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rbac.common.constant.ResultConstants;
import com.rbac.common.util.DateUtils;
import com.rbac.common.util.StringUtils;
import com.rbac.common.util.sql.SqlUtil;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.framework.web.page.PageDomain;
import com.rbac.framework.web.page.TableDataInfo;
import com.rbac.framework.web.page.TableSupport;

/**
 * 基本controller，包括分页功能
 * 
 * @author wlfei
 *
 */
public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}

	/**
	 * 设置请求分页数据
	 */
	protected void startPage() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
	}

	/**
	 * 响应请求分页数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected TableDataInfo getDataTable(List<?> list) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(ResultConstants.CODE_SUCCESS);
		rspData.setMsg("查询成功");
		rspData.setRows(list);
		rspData.setTotal(new PageInfo(list).getTotal());
		return rspData;
	}

	/**
	 * 响应请求分页数据
	 */
	protected TableDataInfo getDataTable(List<?> list, Long total) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(ResultConstants.CODE_SUCCESS);
		rspData.setMsg("查询成功");
		rspData.setRows(list);
		rspData.setTotal(total);
		return rspData;
	}

	/**
	 * 响应返回结果
	 * 
	 * @param rows 影响行数
	 * @return 操作结果
	 */
	protected AjaxResult toAjax(int rows) {
		return rows > 0 ? AjaxResult.success() : AjaxResult.error();
	}
}
