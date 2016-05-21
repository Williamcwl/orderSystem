package com.bjpowernode.drp.sysmgr.domain;

import java.util.Date;

/*
 * 用户实体
 * 
 * */
public class User {
	//用户代码
	private String UserId;
	
	//用户姓名
	private String userName;
	
	//密码
	private String password;
	
	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactTel() {
		return contactTel == null ? "" : contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getEmail() {
		return email == null ? "" : email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	//联系电话
	private String contactTel;
	
	//电子邮件
	private String email;
	
	//创建日期
	private Date createDate;
}
