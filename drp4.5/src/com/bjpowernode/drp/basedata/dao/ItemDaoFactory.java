package com.bjpowernode.drp.basedata.dao;

import com.bjpowernode.drp.basedata.domain.Item;

/**
 * dao�����ӿ�
 * @author cnwl
 *
 */
public interface ItemDaoFactory {

	/**
	 * ��������Dao
	 * @return
	 */
	public ItemDao createitemDao();
}
