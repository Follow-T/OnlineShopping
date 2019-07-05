package com.tg.OnlineShoppingMall.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tg.OnlineShoppingMall.domin.Commodity;

/**
 * 用JPA 有关Commodity表的操作
 * 
 * @author follow_tg
 *
 */
public interface CommodityRepository extends JpaRepository<Commodity, Long> {

	List<Commodity> findByStorename(String storename);

	List<Commodity> findByIfgroundOrderByCreateTimeDesc(Boolean ifground);

	List<Commodity> findByIfgroundAndStorenameOrderByCreateTimeDesc(Boolean ifground, String storename);

	// 从数据库取出第startIndex条 总diaplayLength条数据 根据时间取出
	@Query(nativeQuery = true, value = "select * from commodity where commodity_ifground=true ORDER BY create_time desc LIMIT :startIndex,:displayLength")
	List<Commodity> findPage(@Param("startIndex") int startIndex, @Param("displayLength") int displayLength);

	@Query(nativeQuery = true, value = "select * from commodity where commodity_type=:type and commodity_ifground=true ORDER BY create_time desc LIMIT :startIndex,:displayLength")
	List<Commodity> listCommodityByTypeAndPage(@Param("type") String type, @Param("startIndex") int startIndex,
			@Param("displayLength") int displayLength);

	// 根据商品浏览量以及添加购物车的数量返回商品
	Page<Commodity> findByIfground(Boolean ifground, Pageable page);

	// 根据商品名返回商品
	List<Commodity> findByCommoditynameLike(String commodityname);

}
