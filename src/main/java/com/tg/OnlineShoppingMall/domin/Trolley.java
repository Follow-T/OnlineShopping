package com.tg.OnlineShoppingMall.domin;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 购物车实体类
 * 
 * @author follow_tg
 *
 */

@Entity
public class Trolley {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long commodityid;
	
	@Column(nullable = false)
	private String commodityname;

	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String storename;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private String info;

	@Column(nullable = false)
	@org.hibernate.annotations.CreationTimestamp
	private Timestamp createTime;

	protected Trolley() {

	}

	public Trolley(Long commodityid,String commodityname, String username, int price, String info,String storename) {
		this.commodityid=commodityid;
		this.commodityname = commodityname;
		this.username = username;
		this.price = price;
		this.info = info;
		this.storename=storename;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommodityname() {
		return commodityname;
	}

	public void setCommodityname(String commodityname) {
		this.commodityname = commodityname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}
	
	
}
