package com.jezz.order.consumer.service;

import com.jezz.order.consumer.pojo.TbStock;

public interface StockService {

    /**
     * 获取库存数量
     *
     * @param id
     * @return
     */
    int getStockCount(Integer id);

    /**
     * 根据id获取库存信息
     *
     * @param id
     * @return
     */
    TbStock queryStockById(Integer id);

    /**
     * 更新库存
     *
     * @param tbStock
     * @return
     */
    int updateStockById(TbStock tbStock);

    /**
     * 乐观锁更新库存
     *
     * @param tbStock
     * @return
     */
    int updateStockByOptimistic(TbStock tbStock);
}
