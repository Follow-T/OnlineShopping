package com.tg.OnlineShoppingMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tg.OnlineShoppingMall.domin.Store;

public interface StoreRepository extends JpaRepository<Store, Long>{
	
	Store findByUsername(String username);
	
}
