package com.tg.OnlineShoppingMall.domin;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户的浏览历史
 * @author follow_tg
 *
 */
@Entity
public class UserScan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="user_id",nullable=false)
	private Long userid;
	
	@Column(name="commodity_id",nullable=false)
	private Long commodityid;
	
	@Column(nullable=false)
	@org.hibernate.annotations.CreationTimestamp
	private Timestamp createTime;
	
	protected UserScan() {
		
	}
	
	public UserScan(Long userid,Long commodityid) {
		this.userid=userid;
		this.commodityid=commodityid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getCommodityid() {
		return commodityid;
	}

	public void setCommodityid(Long commodityid) {
		this.commodityid = commodityid;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
