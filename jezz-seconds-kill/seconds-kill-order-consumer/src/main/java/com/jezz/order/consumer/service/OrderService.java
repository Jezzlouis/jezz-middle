package com.jezz.order.consumer.service;

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


    /**
     * 乐观锁加redis
     * @param sid
     * @return
     * @throws Exception
     */
    int createOptimisticOrderByRedis(int sid) throws Exception;
}
