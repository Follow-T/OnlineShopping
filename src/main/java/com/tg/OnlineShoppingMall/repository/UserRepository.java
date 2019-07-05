package com.tg.OnlineShoppingMall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tg.OnlineShoppingMall.domin.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
	
	@Query(nativeQuery=true, value = "select * from user ORDER BY create_time desc LIMIT :startIndex,:displayLength")
	List<User> findPage(@Param("startIndex")int startIndex, @Param("displayLength")int displayLength);
}
