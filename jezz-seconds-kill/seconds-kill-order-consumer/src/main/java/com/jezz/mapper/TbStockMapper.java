package com.jezz.mapper;

import com.jezz.pojo.TbStock;
import com.jezz.pojo.TbStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbStockMapper {
    int countByExample(TbStockExample example);

    int deleteByExample(TbStockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbStock record);

    int insertSelective(TbStock record);

    List<TbStock> selectByExample(TbStockExample example);

    TbStock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbStock record, @Param("example") TbStockExample example);

    int updateByExample(@Param("record") TbStock record, @Param("example") TbStockExample example);

    int updateByPrimaryKeySelective(TbStock record);

    int updateByPrimaryKey(TbStock record);
}