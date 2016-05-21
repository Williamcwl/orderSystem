package com.bjpowernode.drp.basedata.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.basedata.manager.ItemManager;
import com.bjpowernode.drp.basedata.manager.ItemManagerImpl;
import com.bjpowernode.drp.util.ApplicationException;
import com.bjpowernode.drp.util.datadict.domain.ItemCategory;
import com.bjpowernode.drp.util.datadict.domain.ItemUnit;

/**
 * �������
 * @author Administrator
 *
 */
//public class AddItemServlet extends HttpServlet {
//
//	private ItemManager itemManager;
//	
//	@Override
//	public void init() throws ServletException {
//		itemManager = new ItemManagerImpl();
//	}
	
	public class AddItemServlet extends AbstractItemServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.setCharacterEncoding("GB18030");
		//ȡ�ñ�����
		String itemNo = request.getParameter("itemNo");
		String itemName = request.getParameter("itemName");
		String spec = request.getParameter("spec");
		String pattern = request.getParameter("pattern");
		String category = request.getParameter("category");
		String unit = request.getParameter("unit");
		
		//����Item����
		Item item = new Item();
		item.setItemNo(itemNo);
		item.setItemName(itemName);
		item.setSpec(spec);
		item.setPattern(pattern);
		
		//�����������
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setId(category);
		item.setItemCategory(itemCategory);
		
		//�������ϵ�λ
		ItemUnit itemUnit = new ItemUnit();
		itemUnit.setId(unit);
		item.setItemUnit(itemUnit);
		
		String errorMessage = "";
//		try {
//			itemManager.addItem(item);
//		}catch(ApplicationException e) {
//			//request.setAttribute("error_message", "�������ʧ�ܣ����ϴ��롾" + itemNo + "��");
//			//errorMessage = "�������ʧ�ܣ����ϴ��롾" + itemNo + "��";
//			errorMessage = e.getMessage();
//		}
//		//�ض��򵽲�ѯҳ��
//		response.sendRedirect(request.getContextPath() + "/basedata/item_maint.jsp?errorMessage=" + URLEncoder.encode(errorMessage, "GB18030"));
//		
//		//response.sendRedirect(request.getContextPath() + "/basedata/item_add.jsp?errorMessage=" + URLEncoder.encode(errorMessage, "GB18030"));
//		
//		//ת��
//		//request.getRequestDispatcher("/basedata/item_maint.jsp").forward(request, response);
		
		//��������ʽ�쳣
		itemManager.addItem(item);
		//response.sendRedirect(request.getContextPath() + "/basedata/item_maint.jsp");
		
		response.sendRedirect(request.getContextPath() + "/servlet/item/SearchItemServlet");		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
