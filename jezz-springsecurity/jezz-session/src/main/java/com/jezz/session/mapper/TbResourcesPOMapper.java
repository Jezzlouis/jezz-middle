package com.jezz.session.mapper;

import com.jezz.session.domain.po.TbResourcesPO;
import com.jezz.session.domain.po.TbResourcesPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbResourcesPOMapper {
    int countByExample(TbResourcesPOExample example);

    int deleteByExample(TbResourcesPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbResourcesPO record);

    int insertSelective(TbResourcesPO record);

    List<TbResourcesPO> selectByExample(TbResourcesPOExample example);

    TbResourcesPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbResourcesPO record, @Param("example") TbResourcesPOExample example);

    int updateByExample(@Param("record") TbResourcesPO record, @Param("example") TbResourcesPOExample example);

    int updateByPrimaryKeySelective(TbResourcesPO record);

    int updateByPrimaryKey(TbResourcesPO record);
}