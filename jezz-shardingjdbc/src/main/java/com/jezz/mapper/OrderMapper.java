package com.jezz.mapper;


import com.jezz.entity.Order;
import org.springframework.stereotype.Repository;

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
	Order findAll();
}
