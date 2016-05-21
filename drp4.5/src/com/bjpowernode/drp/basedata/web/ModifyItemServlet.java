package com.bjpowernode.drp.basedata.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.basedata.manager.ItemManager;
import com.bjpowernode.drp.basedata.manager.ItemManagerImpl;
import com.bjpowernode.drp.util.datadict.domain.ItemCategory;
import com.bjpowernode.drp.util.datadict.domain.ItemUnit;
import com.bjpowernode.drp.util.datadict.manager.DataDictManager;

/**
 * 修改物料Servlet
 * @author Administrator
 *
 */
//public class ModifyItemServlet extends HttpServlet {
//	
//	private ItemManager itemManager;
//	
//	@Override
//	public void init() throws ServletException {
//		itemManager = new ItemManagerImpl();
//	}
	
	
public class ModifyItemServlet extends AbstractItemServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//取得表单数据
		String itemNo = request.getParameter("itemNo");
		String itemName = request.getParameter("itemName");
		String spec = request.getParameter("spec");
		String pattern = request.getParameter("pattern");
		String category = request.getParameter("category");
		String unit = request.getParameter("unit");
		
		//构造Item对象
		Item item = new Item();
		item.setItemNo(itemNo);
		item.setItemName(itemName);
		item.setSpec(spec);
		item.setPattern(pattern);
		
		//构造物料类别
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setId(category);
		item.setItemCategory(itemCategory);
		
		//构造物料单位
		ItemUnit itemUnit = new ItemUnit();
		itemUnit.setId(unit);
		item.setItemUnit(itemUnit);
		
		itemManager.modifyItem(item);
		
		//response.sendRedirect(request.getContextPath() + "/basedata/item_maint.jsp");
		
		response.sendRedirect(request.getContextPath() + "/servlet/item/SearchItemServlet");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);	
	}

}
