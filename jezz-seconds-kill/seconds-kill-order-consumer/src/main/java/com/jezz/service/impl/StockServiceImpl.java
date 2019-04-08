package com.jezz.service.impl;

import com.jezz.mapper.StockMapper;
import com.jezz.mapper.TbStockMapper;
import com.jezz.pojo.TbStock;
import com.jezz.service.StockService;
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
