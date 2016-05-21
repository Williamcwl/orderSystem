package com.bjpowernode.drp.basedata.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.basedata.manager.ItemManager;
import com.bjpowernode.drp.basedata.manager.ItemManagerImpl;

/**
 * 
 * @author Administrator
 *
 */
//public class ShowItemUploadServlet extends HttpServlet {
//
//	private ItemManager itemManager;
//	
//	@Override
//	public void init() throws ServletException {
//		itemManager = new ItemManagerImpl();
//	}
	
public class ShowItemUploadServlet extends AbstractItemServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String itemNo = request.getParameter("itemNo");
		Item item = itemManager.findItemById(itemNo);
		request.setAttribute("item", item);
		
		request.getRequestDispatcher("/basedata/item_upload.jsp").forward(request, response);
	}

}
