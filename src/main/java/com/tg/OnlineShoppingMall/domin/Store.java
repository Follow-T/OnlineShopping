package com.tg.OnlineShoppingMall.domin;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Store {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String storename;
	
	@Column
	private String phone;
	
	@Column
	private String email;
	
	@Column
	private String username;
	
	@Column
	private int type;
	
	@Column
	private String info;
	
	@Column
	private int visit=0;
	
	@Column
	private String file;
	
	@Column(nullable = false)
	@org.hibernate.annotations.CreationTimestamp
	private Timestamp createTime;
	
	protected Store() {
		
	}
	
	public Store(String storename,String phone,String email,String username,int type,String info,String file) {
		this.storename=storename;
		this.email=email;
		this.info=info;
		this.phone=phone;
		this.type=type;
		this.username=username;
		this.file=file;
		visit=0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public void increaseVisit() {
		this.visit++;
	}
}
