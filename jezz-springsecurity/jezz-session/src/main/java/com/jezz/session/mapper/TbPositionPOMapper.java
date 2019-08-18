package com.jezz.session.mapper;

import com.jezz.session.domain.po.TbPositionPO;
import com.jezz.session.domain.po.TbPositionPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbPositionPOMapper {
    int countByExample(TbPositionPOExample example);

    int deleteByExample(TbPositionPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbPositionPO record);

    int insertSelective(TbPositionPO record);

    List<TbPositionPO> selectByExample(TbPositionPOExample example);

    TbPositionPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbPositionPO record, @Param("example") TbPositionPOExample example);

    int updateByExample(@Param("record") TbPositionPO record, @Param("example") TbPositionPOExample example);

    int updateByPrimaryKeySelective(TbPositionPO record);

    int updateByPrimaryKey(TbPositionPO record);
}