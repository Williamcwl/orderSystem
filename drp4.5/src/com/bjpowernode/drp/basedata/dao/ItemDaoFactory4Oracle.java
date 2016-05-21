package com.bjpowernode.drp.basedata.dao;

import com.bjpowernode.drp.basedata.domain.Item;

public class ItemDaoFactory4Oracle implements ItemDaoFactory {

	@Override
	public ItemDao createitemDao() {
		// TODO Auto-generated method stub
		return new ItemDao4OracleImpl();
	}

}
