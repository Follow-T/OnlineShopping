package com.tg.OnlineShoppingMall.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tg.OnlineShoppingMall.domin.Authority;
import com.tg.OnlineShoppingMall.repository.AuthorityRepository;





@Service
public class AuthorityServiceImpl  implements AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public Authority getAuthorityById(Long id) {
		return authorityRepository.getOne(id);
	}

}
