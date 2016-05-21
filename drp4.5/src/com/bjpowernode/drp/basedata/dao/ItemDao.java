package com.bjpowernode.drp.basedata.dao;

import java.sql.Connection;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.util.PageModel;

/**
 * 物料数据访问接口
 * @author cnwl
 *
 */   
public interface ItemDao {
	
	/**
	 * 添加物料
	 * @param conn
	 * @param item
	 */
	public void addItem(Connection conn , Item item);
	
	/**
	 * 根据物料代码的集合删除
	 * @param conn
	 * @param itemNos
	 */
	public void delItem(Connection conn , String[] itemNos);
	
	/**
	 * 修改物料
	 * @param conn
	 * @param item
	 */
	public void modifyItem(Connection conn , Item item);
	
	
	/**
	 * 根据物料代码查询
	 * @param conn
	 * @param itemNo
	 */
	public Item findItemById(Connection conn ,String itemNo);
	
	/**
	 * 根据条件分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public PageModel findItemList(Connection conn ,int pageNo, int pageSize,String condition);
	
	
}
