package com.tg.OnlineShoppingMall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tg.OnlineShoppingMall.domin.UserScan;

/**
 * 定义操作UserScan表的方法
 * 
 * @author follow_tg
 *
 */
public interface UserScanRepostiory extends JpaRepository<UserScan, Long> {

	// 根据userid查找UserScan表
	List<UserScan> findByUserid(Long userid);

	// 根据userid以及commodityid查找UserScan表
	UserScan findByUseridAndCommodityid(Long userid, Long commodityid);
}
