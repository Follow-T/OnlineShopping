package com.tg.OnlineShoppingMall.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tg.OnlineShoppingMall.domin.Store;

public interface StoreService {
	
	public Store getStoreById(Long id);
	
	public Store getStoreByUsername(String username);
	
	public void saveStore(Store store);
	
	public void deleteStore(Long id);
	
	// 根据访问量来返回最热店铺
	public Page<Store> getHotStoreByVisit(Pageable pageable);
}
