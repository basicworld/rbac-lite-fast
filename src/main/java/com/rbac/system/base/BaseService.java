package com.rbac.system.base;

/**
 * service类基本模版，包含增删改查
 * 
 * @author wlfei
 *
 * @param <T>
 */
public interface BaseService<T> {
	/**
	 * 新增
	 * 
	 * @param item
	 * @return
	 */
	Integer insertSelective(T item);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	Integer deleteByPrimaryKey(Long id);

	/**
	 * 修改
	 * 
	 * @param item
	 * @return
	 */
	Integer updateSelective(T item);

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(Long id);

}
