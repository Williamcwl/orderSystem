package com.bjpowernode.drp.sysmgr.domain;

import java.util.Date;

/*
 * �û�ʵ��
 * 
 * */
public class User {
	//�û�����
	private String UserId;
	
	//�û�����
	private String userName;
	
	//����
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

	//��ϵ�绰
	private String contactTel;
	
	//�����ʼ�
	private String email;
	
	//��������
	private Date createDate;
}