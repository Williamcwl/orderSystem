package com.bjpowernode.drp.basedata.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.drp.basedata.manager.ItemManager;
import com.bjpowernode.drp.basedata.manager.ItemManagerImpl;

/**
 * É¾³ýÎïÁÏServlet
 * @author Administrator
 *
 */
//public class DelItemServlet extends HttpServlet {
//	
//	private ItemManager itemManager; 
//	
//	@Override
//	public void init() throws ServletException {
//		itemManager = new ItemManagerImpl();
//	}

	public class DelItemServlet extends AbstractItemServlet {
		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] itemNos = request.getParameterValues("selectFlag");
		
		itemManager.delItem(itemNos);
		
		//response.sendRedirect(request.getContextPath() + "/basedata/item_maint.jsp");
		response.sendRedirect(request.getContextPath() + "/servlet/item/SearchItemServlet");
	}

}
