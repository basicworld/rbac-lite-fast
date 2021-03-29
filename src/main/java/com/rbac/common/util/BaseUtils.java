package com.rbac.common.util;

import java.util.List;

/**
 * 基础工具包
 * 
 * @author wlfei
 *
 */
public class BaseUtils {
	/**
	 * 获取list中的第一个元素
	 * 
	 * @param <T>  返回值类型
	 * @param list
	 * @return T或null
	 */
	public static <T> T firstItemOfList(List<T> list) {
		if (null == list || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

}
