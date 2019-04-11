package com.jezz.order.consumer.service.impl;

import com.jezz.order.consumer.constants.RedisKey;
import com.jezz.order.consumer.mapper.TbOrderMapper;
import com.jezz.order.consumer.pojo.TbOrder;
import com.jezz.order.consumer.service.OrderService;
import com.jezz.order.consumer.service.StockService;
import com.jezz.order.consumer.pojo.TbStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author jezzlouis
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private StockService stockService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int createWrongOrder(Integer sid) {
        // 1.校验库存
        TbStock tbStock = checkStock(sid);
        // 2.扣库存
        saleStock(tbStock);
        // 3.创建订单
        return createOrder(tbStock);
    }

    /**
     * 乐观锁更新
     * @param sid
     * @return
     * @throws Exception
     */
    @Override
    public int createOptimisticOrder(int sid) throws Exception {
        //校验库存
        TbStock tbStock = checkStock(sid);
        //乐观锁更新库存
        saleStockOptimistic(tbStock);
        //创建订单
        int id = createOrder(tbStock);
        return id;

    }

    /**
     * 乐观锁加redis
     * @param sid
     * @return
     * @throws Exception
     */
    @Override
    public int createOptimisticOrderByRedis(int sid) throws Exception {
        //检验库存，从 Redis 获取
        TbStock tbStock = checkStockByRedis(sid);
        //乐观锁更新库存 以及更新 Redis
        saleStockOptimisticByRedis(tbStock);
        //创建订单
        int id = createOrder(tbStock);
        return id ;

    }

    private TbStock checkStockByRedis(int sid) throws Exception{
        Integer count = Integer.parseInt(redisTemplate.opsForValue().get(RedisKey.STOCK_COUNT + sid).toString());
        Integer sale = Integer.parseInt(redisTemplate.opsForValue().get(RedisKey.STOCK_SALE + sid).toString());
        if (count.equals(sale)){
            throw new RuntimeException("库存不足 Redis currentCount=" + sale);
        }
        Integer version = Integer.parseInt(redisTemplate.opsForValue().get(RedisKey.STOCK_VERSION + sid).toString());
        TbStock tbStock = new TbStock() ;
        tbStock.setId(sid);
        tbStock.setCount(count);
        tbStock.setSale(sale);
        tbStock.setVersion(version);
        return tbStock;
    }

    private void saleStockOptimisticByRedis(TbStock tbStock){
        int count = stockService.updateStockByOptimistic(tbStock);
        if (count == 0){
            throw new RuntimeException("并发更新库存失败") ;
        }
        //自增
        redisTemplate.opsForValue().increment(RedisKey.STOCK_SALE + tbStock.getId(),1) ;
        redisTemplate.opsForValue().increment(RedisKey.STOCK_VERSION + tbStock.getId(),1) ;

    }

    private void saleStockOptimistic(TbStock tbStock) {
        int count = stockService.updateStockByOptimistic(tbStock);
        if (count == 0){
            throw new RuntimeException("并发更新库存失败") ;
        }
    }

    /**
     * 总库存 和 已售相等 库存就不足
     *
     * @param sid
     * @return
     */
    private TbStock checkStock(int sid) {
        TbStock tbStock = stockService.queryStockById(sid);
        if (tbStock.getSale().equals(tbStock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return tbStock;
    }

    /**
     * 每条订单 已售加1
     *
     * @param tbStock
     * @return
     */
    private int saleStock(TbStock tbStock) {
        tbStock.setSale(tbStock.getSale() + 1);
        return stockService.updateStockById(tbStock);
    }

    /**
     * 创建订单
     *
     * @param tbStock
     * @return
     */
    private int createOrder(TbStock tbStock) {
        TbOrder tbOrder = new TbOrder();
        tbOrder.setSid(tbStock.getId());
        tbOrder.setName(tbStock.getName());
        int id = tbOrderMapper.insertSelective(tbOrder);
        return id;
    }
}
