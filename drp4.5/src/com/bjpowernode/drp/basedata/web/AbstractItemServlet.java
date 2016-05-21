package com.bjpowernode.drp.basedata.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.bjpowernode.drp.basedata.dao.ItemDao;
import com.bjpowernode.drp.basedata.manager.ItemManager;
import com.bjpowernode.drp.basedata.manager.ItemManagerImpl;
import com.bjpowernode.drp.util.BeanFactory;

/**
 * 物料Servlet抽象
 * @author Administrator
 *
 */
public abstract class AbstractItemServlet extends HttpServlet {
	
	protected ItemManager itemManager;
	
	@Override
	public void init() throws ServletException {
		//BeanFactory beanFactory = (BeanFactory)this.getServletContext().getAttribute("beanFactory");
		//取得ItemManager
//		itemManager = (ItemManagerImpl)beanFactory.getBean("itemManager");
//		ItemDao itemDao = (ItemDao)beanFactory.getBean("itemDao");
//		itemManager.setItemDao(itemDao);
		
		//itemManager = (ItemManager)BeanFactory.getInstance().getServiceObject("itemManager");
		BeanFactory beanFactory = (BeanFactory)this.getServletContext().getAttribute("beanFactory");
		itemManager = (ItemManager)beanFactory.getServiceObject(ItemManager.class);
	}
}
