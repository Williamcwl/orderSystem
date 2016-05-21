package com.bjpowernode.drp.basedata.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.drp.basedata.manager.ItemManager;
import com.bjpowernode.drp.basedata.manager.ItemManagerImpl;
import com.bjpowernode.drp.util.PageModel;

//public class SearchItemServlet extends HttpServlet {
//
//	private ItemManager itemManager; 
//	
//	@Override
//	public void init() throws ServletException {
//		itemManager = new ItemManagerImpl();
//	}
	
//public class SearchItemServlet extends HttpServlet {

public class SearchItemServlet extends AbstractItemServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		int pageNo = 0;
//		//�ڼ�ҳ
//		String pageNoString = request.getParameter("pageNo");
//		if (pageNoString == null) {
//			pageNo = 1;
//		}else {
//			pageNo = Integer.parseInt(pageNoString);
//		}
		int pageNo = 1;
		//int pageSize = 2;
		
		//ȡ��Servlet��ʼ������page-size
		//int pageSize = Integer.parseInt(this.getServletConfig().getInitParameter("page-size"));
		
		//��application��Χ��ȡ��page-size,applicationָ����ServletContext����
		int pageSize = Integer.parseInt(this.getServletContext().getInitParameter("page-size"));
		
		String pageNoString = request.getParameter("pageNo");
		if (pageNoString != null && !"".equals(pageNoString)) {
			pageNo = Integer.parseInt(pageNoString);
		}
		//ȡ������
		String itemNoOrName = request.getParameter("itemNoOrName");
		
		//ItemManager itemManager = new ItemManagerImpl();
		PageModel pageModel = itemManager.findItemList(pageNo, pageSize, itemNoOrName);
		
		request.setAttribute("pageModel", pageModel);
		
		request.getRequestDispatcher("/basedata/item_maint.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
