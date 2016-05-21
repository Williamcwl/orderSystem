package com.bjpowernode.drp.basedata.manager;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.util.PageModel;

/**
 * 物料业务逻辑层接口
 * @author Administrator
 *
 */
public interface ItemManager {

	/**
	 * 添加物料
	 * @param item
	 */
	public void addItem(Item item);
	
	/**
	 * 根据物料代码的集合删除
	 * @param conn
	 * @param itemNos
	 */
	public void delItem(String[] itemNos);
	
	/**
	 * 修改物料
	 * @param conn
	 * @param item
	 */
	public void modifyItem(Item item);
	
	
	/**
	 * 根据物料代码查询
	 * @param conn
	 * @param itemNo
	 * @return 如果存在返回Item对象，否则返回null
	 */
	public Item findItemById(String itemNo);
	
	/**
	 * 根据条件分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param condation
	 * @return
	 */
	public PageModel findItemList(int pageNo, int pageSize, String condation);
	
	/**
	 * 上传物料图片
	 * @param itemNo
	 * @param fileName
	 */
	public void uploadItemImage(String itemNo, String fileName);
}
