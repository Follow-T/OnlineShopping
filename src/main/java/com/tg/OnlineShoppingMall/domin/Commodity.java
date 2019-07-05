package com.tg.OnlineShoppingMall.domin;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Commodity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "commodity_name", nullable = false)
	private String commodityname;

	@Column(name = "commodity_storename", nullable = false)
	private String storename;
	
	@Column(name = "commodity_storeid", nullable = false)
	private Long storeid;

	@Column(name = "commodity_price", nullable = false)
	private int price;

	@Column(name = "commodity_type", nullable = false)
	private String type;

	@Column(name = "commodity_info", nullable = false)
	private String info;

	@Column(name = "commodity_picture", nullable = false)
	private String picture;
	
	@Column(name = "commodity_ifground")
	private Boolean ifground=false;
	
	@Column(name = "commodity_browse")
	private int browse=0;
	
	@Column(name = "commodity_joinShoppingCart")
	private int joinShoppingCart=0;
	
	@Column(nullable = false)
	@org.hibernate.annotations.CreationTimestamp
	private Timestamp createTime;
	
	protected Commodity() {

	}

	public Commodity(String commodityname, String storename, int price, String type, String info, String picture,Long storeid) {
		this.commodityname = commodityname;
		this.storename = storename;
		this.price = price;
		this.type = type;
		this.info = info;
		this.picture = picture;
		ifground=false;
		browse=0;
		joinShoppingCart=0;
		this.storeid=storeid;
	}
	
	public Boolean getIfground() {
		return ifground;
	}

	public void setIfground(Boolean ifground) {
		this.ifground = ifground;
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

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getBrowse() {
		return browse;
	}

	public void setBrowse(int browse) {
		this.browse = browse;
	}

	public int getJoinShoppingCart() {
		return joinShoppingCart;
	}

	public void setJoinShoppingCart(int joinShoppingCart) {
		this.joinShoppingCart = joinShoppingCart;
	}
	
	public void increaseBrowse() {
		this.browse++;
	}
	
	public void increaseJoinShoppingCart() {
		this.joinShoppingCart++;
	}
	
	public void reduceJoinShoppingCart() {
		this.joinShoppingCart--;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}
	
}
