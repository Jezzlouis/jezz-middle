package com.jezz.order.consumer.mapper;

import com.jezz.order.consumer.pojo.TbStock;
import com.jezz.order.consumer.pojo.TbStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
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