package com.tg.OnlineShoppingMall.service;

import com.tg.OnlineShoppingMall.domin.Commodity;
import java.util.List;

import com.tg.OnlineShoppingMall.domin.UserScan;

public interface UserScanService {
	
	//存储实例userScan;
	UserScan  save (UserScan userScan);
	
	void delete(UserScan userScan);
	
	//根据userid获取commodityid，并获取浏览的商品集合
	List<Commodity> getCommodityByUserid(Long userid);
	
	//根据userid以及commodityid获取浏览历史
	UserScan getUserScanByUseridAndCommodityid(Long userid,Long commodityid);
	
}
