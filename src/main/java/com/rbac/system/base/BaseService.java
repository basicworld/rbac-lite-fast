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
     * @return 新增加的数据项个数
     */
    Integer insertSelective(T item);

    /**
     * 删除
     *
     * @param id
     * @return 删除的数据项个数
     */
    Integer deleteByPrimaryKey(Long id);

    /**
     * 修改
     *
     * @param item
     * @return 修改的数据项个数
     */
    Integer updateSelective(T item);

    /**
     * 主键查询
     *
     * @param id
     * @return 根据主键查询到的唯一数据项 可以为null
     */
    T selectByPrimaryKey(Long id);

}
