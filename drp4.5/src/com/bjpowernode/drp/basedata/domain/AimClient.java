package com.bjpowernode.drp.basedata.domain;


/**
 * 需方客户
 * @author Administrator
 *
 */
public class AimClient {
	
	//主键
	private int id;
	
	//分销商或终端ID
	private String clientTemiId;
	
	//名称
	private String name;
	
	//级别ID 
	private String clientTemilevelId;
	
	//级别名称
	private String clientTemilevelName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientTemiId() {
		return clientTemiId;
	}

	public void setClientTemiId(String clientTemiId) {
		this.clientTemiId = clientTemiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientTemilevelId() {
		return clientTemilevelId;
	}

	public void setClientTemilevelId(String clientTemilevelId) {
		this.clientTemilevelId = clientTemilevelId;
	}

	public String getClientTemilevelName() {
		return clientTemilevelName;
	}

	public void setClientTemilevelName(String clientTemilevelName) {
		this.clientTemilevelName = clientTemilevelName;
	}
}
