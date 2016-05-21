package com.bjpowernode.drp.basedata.dao;

import java.sql.Connection;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.util.PageModel;

/**
 * �������ݷ��ʽӿ�
 * @author cnwl
 *
 */   
public interface ItemDao {
	
	/**
	 * ��������
	 * @param conn
	 * @param item
	 */
	public void addItem(Connection conn , Item item);
	
	/**
	 * �������ϴ���ļ���ɾ��
	 * @param conn
	 * @param itemNos
	 */
	public void delItem(Connection conn , String[] itemNos);
	
	/**
	 * �޸�����
	 * @param conn
	 * @param item
	 */
	public void modifyItem(Connection conn , Item item);
	
	
	/**
	 * �������ϴ����ѯ
	 * @param conn
	 * @param itemNo
	 */
	public Item findItemById(Connection conn ,String itemNo);
	
	/**
	 * ����������ҳ��ѯ
	 * @param pageNo
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public PageModel findItemList(Connection conn ,int pageNo, int pageSize,String condition);
	
	
}