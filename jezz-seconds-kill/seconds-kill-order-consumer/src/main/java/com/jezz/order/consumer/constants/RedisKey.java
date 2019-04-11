package com.jezz.order.consumer.constants;

import java.io.Serializable;

/**
 * @author jezzlouis
 */
public class RedisKey implements Serializable {

    /**
     * 库存
     */
    public final static String STOCK_COUNT = "stock_count_";

    /**
     * 已售数量
     */
    public final static String STOCK_SALE = "stock_sale_";

    /**
     * 版本
     */
    public final static String STOCK_VERSION = "stock_version_";
}
