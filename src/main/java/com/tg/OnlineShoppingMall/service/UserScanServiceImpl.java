package com.tg.OnlineShoppingMall.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tg.OnlineShoppingMall.domin.Commodity;
import com.tg.OnlineShoppingMall.domin.UserScan;
import com.tg.OnlineShoppingMall.repository.UserScanRepostiory;

/**
 * 实现操作UserScan表数据的方法
 * @author follow_tg
 *
 */
@Service
public class UserScanServiceImpl implements UserScanService{
	
	@Autowired
	private UserScanRepostiory userScanRepostiory;
	
	@Autowired
	private CommodityService commodityService;
	
	@Transactional
	@Override
	public UserScan save(UserScan userScan) {
		return userScanRepostiory.save(userScan);
	}

	@Override
	public List<Commodity> getCommodityByUserid(Long userid) {
		List<UserScan> userScans=userScanRepostiory.findByUserid(userid);
		List<Commodity> commodities=new ArrayList<>();
		for(UserScan userScan:userScans) {
			commodities.add(commodityService.getCommodityById(userScan.getCommodityid()));
		}
		return commodities;
	}

	@Override
	public UserScan getUserScanByUseridAndCommodityid(Long userid, Long commodityid) {
		return userScanRepostiory.findByUseridAndCommodityid(userid, commodityid);
	}
	
	@Transactional
	@Override
	public void delete(UserScan userScan) {
		userScanRepostiory.delete(userScan);
	}

}
