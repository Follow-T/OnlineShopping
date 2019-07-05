package com.tg.OnlineShoppingMall.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tg.OnlineShoppingMall.domin.Store;
import com.tg.OnlineShoppingMall.repository.StoreRepository;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreRepository storeRepository;

	@Override
	public Store getStoreById(Long id) {
		return storeRepository.getOne(id);
	}

	@Transactional
	@Override
	public void saveStore(Store store) {
		storeRepository.save(store);
	}

	@Transactional
	@Override
	public void deleteStore(Long id) {
		storeRepository.deleteById(id);
	}

	@Override
	public Store getStoreByUsername(String username) {
		return storeRepository.findByUsername(username);
	}

	@Override
	public Page<Store> getHotStoreByVisit(Pageable pageable) {
		return storeRepository.findAll(pageable);
	}

}
