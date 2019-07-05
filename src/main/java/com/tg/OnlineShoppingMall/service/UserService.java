package com.tg.OnlineShoppingMall.service;

import java.util.List;

import com.tg.OnlineShoppingMall.domin.User;

public interface UserService {
	
	User getUserById(Long id);
	
	User findByUsername(String username);
	
	void saveUser(User user);
	
	List<User> ListUserByPage(int startIndex,int displayLength);
}
