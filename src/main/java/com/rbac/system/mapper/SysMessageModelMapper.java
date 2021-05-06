package com.rbac.system.mapper;

import com.rbac.system.domain.SysMessageModel;
import com.rbac.system.domain.SysMessageModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysMessageModelMapper {
    long countByExample(SysMessageModelExample example);

    int deleteByExample(SysMessageModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysMessageModel record);

    int insertSelective(SysMessageModel record);

    List<SysMessageModel> selectByExample(SysMessageModelExample example);

    SysMessageModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysMessageModel record, @Param("example") SysMessageModelExample example);

    int updateByExample(@Param("record") SysMessageModel record, @Param("example") SysMessageModelExample example);

    int updateByPrimaryKeySelective(SysMessageModel record);

    int updateByPrimaryKey(SysMessageModel record);
}