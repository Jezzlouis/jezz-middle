package com.jezz.session.mapper;

import com.jezz.session.domain.po.TbAdminRolePO;
import com.jezz.session.domain.po.TbAdminRolePOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAdminRolePOMapper {
    int countByExample(TbAdminRolePOExample example);

    int deleteByExample(TbAdminRolePOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAdminRolePO record);

    int insertSelective(TbAdminRolePO record);

    List<TbAdminRolePO> selectByExample(TbAdminRolePOExample example);

    TbAdminRolePO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAdminRolePO record, @Param("example") TbAdminRolePOExample example);

    int updateByExample(@Param("record") TbAdminRolePO record, @Param("example") TbAdminRolePOExample example);

    int updateByPrimaryKeySelective(TbAdminRolePO record);

    int updateByPrimaryKey(TbAdminRolePO record);
}