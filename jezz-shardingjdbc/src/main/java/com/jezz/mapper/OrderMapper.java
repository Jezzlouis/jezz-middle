package com.jezz.mapper;


import com.jezz.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
	/**
	 * 保存
	 */
	void save(Order order);
	
	/**
	 * 查询
	 * @return
	 */
	List<Order> findAll();
}
