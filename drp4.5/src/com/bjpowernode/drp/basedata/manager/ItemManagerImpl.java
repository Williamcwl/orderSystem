package com.bjpowernode.drp.basedata.manager;

import java.sql.Connection;

import com.bjpowernode.drp.basedata.dao.ItemDao;
import com.bjpowernode.drp.basedata.dao.ItemDaoFactory;
import com.bjpowernode.drp.basedata.dao.ItemDaoFactory4MySql;
import com.bjpowernode.drp.basedata.dao.ItemDaoFactory4Oracle;
import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.util.ApplicationException;
import com.bjpowernode.drp.util.BeanFactory;
import com.bjpowernode.drp.util.DbUtil;
import com.bjpowernode.drp.util.PageModel;
import com.bjpowernode.drp.util.XmlConfigReader;

/**
 * 物料管理接口实现
 * 
 * @author Administrator
 *
 */
public class ItemManagerImpl implements ItemManager {
	
	private ItemDao itemDao = null;
	
	public ItemManagerImpl() {
		itemDao = (ItemDao)BeanFactory.getInstance().getDaoObject(ItemDao.class);
//		//ItemDaoFactory factory = new ItemDaoFactory4Oracle();
//		//ItemDaoFactory factory = new ItemDaoFactory4MySql();
//		String className = XmlConfigReader.getInstance().getDaoFactory("item-dao-facotry");
//		ItemDaoFactory factory = null;
//		try {
//			factory = (ItemDaoFactory)Class.forName(className).newInstance();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		itemDao = factory.createItemDao();		
	}
	
	public void addItem(Item item) {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			if (itemDao.findItemById(conn, item.getItemNo()) != null) {
				throw new ApplicationException("物料代码已经存在，代码=" + item.getItemNo()  + "");
			}
			itemDao.addItem(conn, item);
		}catch(ApplicationException e) {
			throw e;
		}finally {
			DbUtil.close(conn);
		}	
	}

	
	public void delItem(String[] itemNos) {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			itemDao.delItem(conn, itemNos);
		}catch(ApplicationException e) {
			throw e;
		}finally {
			DbUtil.close(conn);
		}	
	}

	public Item findItemById(String itemNo) {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			return itemDao.findItemById(conn, itemNo);
		}finally {
			DbUtil.close(conn);
		}	
	}

	public PageModel findItemList(int pageNo, int pageSize, String condation) {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			return itemDao.findItemList(conn, pageNo, pageSize, condation);
		}finally {
			DbUtil.close(conn);
		}	
	}

	public void modifyItem(Item item) {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			itemDao.modifyItem(conn, item);
		}finally {
			DbUtil.close(conn);
		}	
	}
	
	public void uploadItemImage(String itemNo, String fileName) {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			Item item = itemDao.findItemById(conn, itemNo);
			item.setFileName(fileName);
			itemDao.modifyItem(conn, item);
		}catch(Exception e) {
			throw new ApplicationException("上传物料图片失败！");
		}finally {
			DbUtil.close(conn);
		}	
	}

/*	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}*/
}
