package com.bjpowernode.drp.basedata.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.drp.util.datadict.domain.ItemCategory;
import com.bjpowernode.drp.util.datadict.domain.ItemUnit;
import com.bjpowernode.drp.util.datadict.manager.DataDictManager;

/**
 * 显示物料添加页面Servlet
 * @author Administrator
 *
 */
public class ShowAddItemServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//取得物料类别类表
		List<ItemCategory> itemCategoryList = DataDictManager.getInstance().findItemCategoryList();
		
		//取得计量单位列表
		List<ItemUnit> unitList = DataDictManager.getInstance().findItemUnitList();
		
		//设置到request中
		request.setAttribute("itemCategoryList", itemCategoryList);
		request.setAttribute("unitList", unitList);
		
		//转发
		request.getRequestDispatcher("/basedata/item_add.jsp").forward(request, response);
	}

}
