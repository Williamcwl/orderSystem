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
 * ��ʾ�������ҳ��Servlet
 * @author Administrator
 *
 */
public class ShowAddItemServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//ȡ������������
		List<ItemCategory> itemCategoryList = DataDictManager.getInstance().findItemCategoryList();
		
		//ȡ�ü�����λ�б�
		List<ItemUnit> unitList = DataDictManager.getInstance().findItemUnitList();
		
		//���õ�request��
		request.setAttribute("itemCategoryList", itemCategoryList);
		request.setAttribute("unitList", unitList);
		
		//ת��
		request.getRequestDispatcher("/basedata/item_add.jsp").forward(request, response);
	}

}
