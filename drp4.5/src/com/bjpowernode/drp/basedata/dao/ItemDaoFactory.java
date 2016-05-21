package com.bjpowernode.drp.basedata.dao;

import com.bjpowernode.drp.basedata.domain.Item;

/**
 * dao工厂接口
 * @author cnwl
 *
 */
public interface ItemDaoFactory {

	/**
	 * 创建物料Dao
	 * @return
	 */
	public ItemDao createitemDao();
}
