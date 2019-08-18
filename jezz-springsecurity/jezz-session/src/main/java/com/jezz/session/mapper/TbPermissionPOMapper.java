package com.jezz.session.mapper;

import com.jezz.session.domain.po.TbPermissionPO;
import com.jezz.session.domain.po.TbPermissionPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbPermissionPOMapper {
    int countByExample(TbPermissionPOExample example);

    int deleteByExample(TbPermissionPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbPermissionPO record);

    int insertSelective(TbPermissionPO record);

    List<TbPermissionPO> selectByExample(TbPermissionPOExample example);

    TbPermissionPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbPermissionPO record, @Param("example") TbPermissionPOExample example);

    int updateByExample(@Param("record") TbPermissionPO record, @Param("example") TbPermissionPOExample example);

    int updateByPrimaryKeySelective(TbPermissionPO record);

    int updateByPrimaryKey(TbPermissionPO record);
}