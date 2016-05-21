package com.bjpowernode.drp.basedata.dao;

import java.sql.Connection;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.util.PageModel;

public class ItemDao4MySqlImpl implements ItemDao {

	@Override
	public void addItem(Connection conn, Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delItem(Connection conn, String[] itemNos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyItem(Connection conn, Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public Item findItemById(Connection conn, String itemNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageModel findItemList(Connection conn,int pageNo, int pageSize, String condition) {
		// TODO Auto-generated method stub
		return null;
	}

}
