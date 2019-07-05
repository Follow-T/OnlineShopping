package com.tg.OnlineShoppingMall.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tg.OnlineShoppingMall.domin.Trolley;
import com.tg.OnlineShoppingMall.repository.TrolleyRepository;

/**
 * Service层，真正操作购物车数据的实现方法
 * @author follow_tg
 *
 */
@Service
public class TrolleyServiceImpl implements TrolleyService{
	
	@Autowired
	private TrolleyRepository trolleyRepository;
	
	@Transactional
	@Override
	public Trolley Save(Trolley trolley) {
		return trolleyRepository.save(trolley);
	}

	@Override
	public Trolley geTrolleyById(Long id) {
		return trolleyRepository.getOne(id);
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		trolleyRepository.deleteById(id);
	}

	@Override
	public List<Trolley> geTrolleysByUsername(String username) {
		return trolleyRepository.findByUsernameOrderByCreateTimeDesc(username);
	}

	@Override
	public Trolley geTrolleyByCommodityIdAndUsername(Long commodityid, String username) {
		return trolleyRepository.findByCommodityidAndUsername(commodityid, username);
	}

}
