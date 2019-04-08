package com.jezz.mapper;

import com.jezz.pojo.TbStock;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMapper {

    int updateByOptimistic(TbStock tbStock);

}
