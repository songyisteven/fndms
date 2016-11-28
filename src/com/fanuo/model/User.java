/**
 * 
 */
package com.fanuo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author tao
 *
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 4981732609802652209L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	/**
	 * 1：超级管理员
	 * 2：官方
	 * 3：财务
	 * 4:一级代理
	 * 5：二级代理
	 * 6：三级代理
	 */
	private int userType;
	
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
