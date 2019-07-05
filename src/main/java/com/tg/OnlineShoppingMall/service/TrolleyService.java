package com.tg.OnlineShoppingMall.service;

import java.util.List;

import com.tg.OnlineShoppingMall.domin.Trolley;

public interface TrolleyService {
	
	Trolley Save(Trolley trolley);
	
	Trolley geTrolleyById(Long id);
	
	void delete(Long id);
	
	List<Trolley> geTrolleysByUsername(String username);
	
	Trolley geTrolleyByCommodityIdAndUsername(Long commodityid,String username);
	
}
