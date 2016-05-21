package com.bjpowernode.drp.basedata.manager;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.util.PageModel;

/**
 * ����ҵ���߼���ӿ�
 * @author Administrator
 *
 */
public interface ItemManager {

	/**
	 * �������
	 * @param item
	 */
	public void addItem(Item item);
	
	/**
	 * �������ϴ���ļ���ɾ��
	 * @param conn
	 * @param itemNos
	 */
	public void delItem(String[] itemNos);
	
	/**
	 * �޸�����
	 * @param conn
	 * @param item
	 */
	public void modifyItem(Item item);
	
	
	/**
	 * �������ϴ����ѯ
	 * @param conn
	 * @param itemNo
	 * @return ������ڷ���Item���󣬷��򷵻�null
	 */
	public Item findItemById(String itemNo);
	
	/**
	 * ����������ҳ��ѯ
	 * @param pageNo
	 * @param pageSize
	 * @param condation
	 * @return
	 */
	public PageModel findItemList(int pageNo, int pageSize, String condation);
	
	/**
	 * �ϴ�����ͼƬ
	 * @param itemNo
	 * @param fileName
	 */
	public void uploadItemImage(String itemNo, String fileName);
}
