package com.bjpowernode.drp.basedata.dao;


public class ItemDaoFactory4MySql implements ItemDaoFactory {

	@Override
	public ItemDao createitemDao() {
		return new ItemDao4MySqlImpl();
	}

}
