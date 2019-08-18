package com.jezz.session.mapper;

import com.jezz.session.domain.po.TbRolePermissionPO;
import com.jezz.session.domain.po.TbRolePermissionPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbRolePermissionPOMapper {
    int countByExample(TbRolePermissionPOExample example);

    int deleteByExample(TbRolePermissionPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbRolePermissionPO record);

    int insertSelective(TbRolePermissionPO record);

    List<TbRolePermissionPO> selectByExample(TbRolePermissionPOExample example);

    TbRolePermissionPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbRolePermissionPO record, @Param("example") TbRolePermissionPOExample example);

    int updateByExample(@Param("record") TbRolePermissionPO record, @Param("example") TbRolePermissionPOExample example);

    int updateByPrimaryKeySelective(TbRolePermissionPO record);

    int updateByPrimaryKey(TbRolePermissionPO record);
}