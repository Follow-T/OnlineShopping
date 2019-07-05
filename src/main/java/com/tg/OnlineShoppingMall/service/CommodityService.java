package com.tg.OnlineShoppingMall.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tg.OnlineShoppingMall.domin.Commodity;

public interface CommodityService {

	void save(Commodity commodity);

	Commodity getCommodityById(Long id);

	void deleteCommodityById(Long id);

	List<Commodity> ListCommodityByStorename(String storename);

	List<Commodity> ListCommodityByIfground(Boolean ifground);

	List<Commodity> ListCommodityByIfgroundAndStorename(Boolean ifground, String storename);

	// 首页显示商品
	List<Commodity> ListCommodityAtIndex(int startIndex, int displayLength);

	// 首页根据Type显示商品
	List<Commodity> ListCommodityByTypeAndPage(String type, int startIndex, int displayLength);
	
	Page<Commodity> PageCommodityByIfground(Boolean ifground,Pageable page);
	
	//根据商品名模糊搜索商品
	List<Commodity> getCommodityByCommodityname(String commodityname);

}
