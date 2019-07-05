package com.tg.OnlineShoppingMall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tg.OnlineShoppingMall.domin.Trolley;

/**
 * 定义操作购物车的方法，JPA会根据方法名字自动生成sql语句，在数据库进行查询
 * @author follow_tg
 *
 */
public interface TrolleyRepository extends JpaRepository<Trolley, Long>{
	
	//根据username获取购车车数据 根据加入购物车的时间排序
	List<Trolley> findByUsernameOrderByCreateTimeDesc(String username);
	
	//根据commodityid以及username获取购物车中的商品
	Trolley findByCommodityidAndUsername(Long id,String username);
}
