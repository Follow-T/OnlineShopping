package com.tg.OnlineShoppingMall.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tg.OnlineShoppingMall.domin.Commodity;
import com.tg.OnlineShoppingMall.repository.CommodityRepository;

/**
 * 服务成 层，业务逻辑，操作Commodity数据
 * 
 * @author follow_tg
 *
 */
@Service
public class CommodityServiceImpl implements CommodityService {

	@Autowired
	private CommodityRepository commodityRepository;

	@Transactional
	@Override
	public void save(Commodity commodity) {
		commodityRepository.save(commodity);
	}

	@Override
	public Commodity getCommodityById(Long id) {
		return commodityRepository.getOne(id);
	}

	@Transactional
	@Override
	public void deleteCommodityById(Long id) {
		commodityRepository.deleteById(id);
	}

	@Override
	public List<Commodity> ListCommodityByIfground(Boolean ifground) {
		return commodityRepository.findByIfgroundOrderByCreateTimeDesc(ifground);
	}

	@Override
	public List<Commodity> ListCommodityByIfgroundAndStorename(Boolean ifground, String storename) {
		return commodityRepository.findByIfgroundAndStorenameOrderByCreateTimeDesc(ifground, storename);
	}

	@Override
	public List<Commodity> ListCommodityByStorename(String storename) {
		return commodityRepository.findByStorename(storename);
	}

	// 首页获取商品数据
	@Override
	public List<Commodity> ListCommodityAtIndex(int startIndex, int displayLength) {
		return commodityRepository.findPage(startIndex, displayLength);
	}

	// 首页根据Type获取商品数据
	@Override
	public List<Commodity> ListCommodityByTypeAndPage(String type, int startIndex, int displayLength) {
		return commodityRepository.listCommodityByTypeAndPage(type, startIndex, displayLength);
	}

	// 首页返回最热的前五商品
	@Override
	public Page<Commodity> PageCommodityByIfground(Boolean ifground, Pageable page) {
		return commodityRepository.findByIfground(true, page);
	}

	@Override
	public List<Commodity> getCommodityByCommodityname(String commodityname) {
		commodityname = "%" + commodityname + "%";
		return commodityRepository.findByCommoditynameLike(commodityname);
	}

}
