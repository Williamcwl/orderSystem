package com.bjpowernode.drp.basedata.domain;

import com.bjpowernode.drp.util.datadict.domain.ClientLevel;

/**
 * 分销商实体类
 * @author cnwl
 *
 */
public class Client {
	
	
	private int id;
	
	private int pid;
	
	private String name;

	private String clientId;
	
	private String bankAcctNo;
	
	private String contactTel;
	
	private String address;
	
	private String zipCode;
	
	private String isLeaf;
	
	private String isClient;
	
	private ClientLevel clientLevel = new ClientLevel();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getClientId() {
		return clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getBankAcctNo() {
		return bankAcctNo == null ?"" :bankAcctNo;
	}

	public void setBankAcctNo(String bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}

	public String getAddress() {
		return address == null ?"" : address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode == null ?"" :zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode ;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getIsClient() {
		return isClient;
	}

	public void setIsClient(String isClient) {
		this.isClient = isClient;
	}

	public String getContactTel() {
		return contactTel == null ?"": contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public ClientLevel getClientLevel() {
		return clientLevel;
	}

	public void setClientLevel(ClientLevel clientLevel) {
		this.clientLevel = clientLevel;
	}
	
	
	
	
}
