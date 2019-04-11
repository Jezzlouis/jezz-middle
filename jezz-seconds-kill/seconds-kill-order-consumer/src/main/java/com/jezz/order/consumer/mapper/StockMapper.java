package com.jezz.order.consumer.mapper;

import com.jezz.order.consumer.pojo.TbStock;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMapper {

    int updateByOptimistic(TbStock tbStock);

}
