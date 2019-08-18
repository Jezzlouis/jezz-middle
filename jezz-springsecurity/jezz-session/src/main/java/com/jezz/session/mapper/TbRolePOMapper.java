package com.jezz.session.mapper;

import com.jezz.session.domain.po.TbRolePO;
import com.jezz.session.domain.po.TbRolePOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbRolePOMapper {
    int countByExample(TbRolePOExample example);

    int deleteByExample(TbRolePOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbRolePO record);

    int insertSelective(TbRolePO record);

    List<TbRolePO> selectByExample(TbRolePOExample example);

    TbRolePO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbRolePO record, @Param("example") TbRolePOExample example);

    int updateByExample(@Param("record") TbRolePO record, @Param("example") TbRolePOExample example);

    int updateByPrimaryKeySelective(TbRolePO record);

    int updateByPrimaryKey(TbRolePO record);
}