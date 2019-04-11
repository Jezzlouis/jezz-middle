package com.jezz.order.consumer.service.impl;

import com.jezz.order.consumer.service.StockService;
import com.jezz.order.consumer.mapper.StockMapper;
import com.jezz.order.consumer.mapper.TbStockMapper;
import com.jezz.order.consumer.pojo.TbStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private TbStockMapper tbStockMapper;
    @Autowired
    private StockMapper stockMapper;

    @Override
    public int getStockCount(Integer id) {
        TbStock tbStock = tbStockMapper.selectByPrimaryKey(id);
        return tbStock.getCount();
    }

    @Override
    public TbStock queryStockById(Integer id) {
        return tbStockMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateStockById(TbStock tbStock) {
        return tbStockMapper.updateByPrimaryKeySelective(tbStock);
    }

    @Override
    public int updateStockByOptimistic(TbStock tbStock) {
        return stockMapper.updateByOptimistic(tbStock);
    }
}
