package com.jezz.service;

public interface OrderService {

    /**
     * 创建有并发错误的订单
     *
     * @param sid
     * @return
     */
    int createWrongOrder(Integer sid);

    /**
     * 乐观锁更新
     * @param sid
     * @return
     * @throws Exception
     */
    int createOptimisticOrder(int sid) throws Exception;
}
