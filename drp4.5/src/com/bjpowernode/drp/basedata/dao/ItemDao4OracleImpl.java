package com.bjpowernode.drp.basedata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bjpowernode.drp.basedata.domain.Item;
import com.bjpowernode.drp.util.ApplicationException;
import com.bjpowernode.drp.util.DbUtil;
import com.bjpowernode.drp.util.PageModel;
import com.bjpowernode.drp.util.datadict.domain.ItemCategory;
import com.bjpowernode.drp.util.datadict.domain.ItemUnit;

public class ItemDao4OracleImpl implements ItemDao {

	public void addItem(Connection conn, Item item) {
		String sql = "insert into t_items (item_no, item_name, spec, pattern, item_category_id, item_unit_id) ";
			sql+=" values (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			//Dao���������һ����ϸ���ȵģ����û����������Dao��Manager���ȿ���һ������ͬ����̫��
			//Dao������ǱȽϵ����ģ���Ӧ�÷�������ҵ���߼���ҵ�����
			//���������ҵ���߼�����ЩManager�����ô�ҵ���߼����������Dao������û�и�������
			//�������ǵ�Ӧ����˵Dao��ײ�ģ�����Ӧ��Խͨ��Խ��
//			if (findItemById(conn, item.getItemNo()) != null) {
//				throw new ApplicationException("���ϴ����Ѿ����ڣ�����=" + item.getItemNo()  + "");
//			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItemNo());
			pstmt.setString(2, item.getItemName());
			pstmt.setString(3, item.getSpec());
			pstmt.setString(4, item.getPattern());
			pstmt.setString(5, item.getItemCategory().getId());
			pstmt.setString(6, item.getItemUnit().getId());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			//System.out.println("errorCode=" + e.getErrorCode());
			//System.out.println("description=" + e.getMessage());
//			if (e.getErrorCode() == 1) {
//				throw new ApplicationException("���ϴ����Ѿ����ڣ����롾" + item.getItemNo()  + "��");
//			}
			throw new ApplicationException("�������ʧ�ܣ�");
		}finally {
			DbUtil.close(pstmt);
		}
	}

	public void delItem(Connection conn, String[] itemNos) {
		StringBuffer sbString = new StringBuffer(); 
		for (int i=0; i<itemNos.length; i++) {
			sbString.append("?");
			if (i < (itemNos.length - 1)) {
				sbString.append(",");
			}
		}
		String sql = "delete from t_items where item_no in(" + sbString.toString()  + ")";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i=0; i<itemNos.length; i++) {
				pstmt.setString(i+1, itemNos[i]);
			}
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("ɾ������ʧ�ܣ�");
		}finally {
			DbUtil.close(pstmt);
		}
	}

	public Item findItemById(Connection conn, String itemNo) {
		StringBuffer sbSql = new StringBuffer();
		//��һ�з���
		sbSql.append("select a.item_no, a.item_name, a.spec, a.pattern, a.item_category_id, ")
		     .append("b.name as item_category_name, a.item_unit_id, c.name as item_unit_name, a.file_name ")
		     .append("from t_items a, t_data_dict b, t_data_dict c ")
		     .append("where a.item_category_id=b.id and a.item_unit_id=c.id and a.item_no=?");

//        //�ڶ��з��� 		
//		sbSql.append("select a.item_no, a.item_name, a.spec, a.pattern, a.category as category_id, ")
//			 .append("(select b.name from t_data_dict b where a.category=b.id) as category_name, ")
//			 .append("a.unit as unit_id, ")
//			 .append("(select c.name from t_data_dict c where a.unit=c.id) as unit_name ")
//			 .append("from t_items a where a.item_no=?");
		
		//ͨ��������־�����¼����log4j, ����info,debug,error...
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Item item = null;
		try {
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setString(1, itemNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				item = new Item();
				item.setItemNo(rs.getString("item_no"));
				item.setItemName(rs.getString("item_name"));
				item.setSpec(rs.getString("spec"));
				item.setPattern(rs.getString("pattern"));
				//����ItemCategory
				ItemCategory ic = new ItemCategory();
				ic.setId(rs.getString("item_category_id"));
				ic.setName(rs.getString("item_category_name"));
				item.setItemCategory(ic);
			
				
				//����ItemUnit
				ItemUnit iu = new ItemUnit();
				iu.setId(rs.getString("item_unit_id"));
				iu.setName(rs.getString("item_unit_name"));
				item.setItemUnit(iu);
				
				item.setFileName(rs.getString("file_name"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			//��¼����־�ļ� error
			throw new ApplicationException("�������ϴ����ѯ�������ϴ���[" + itemNo + "]");
		}finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
		return item;
	}

	public PageModel findItemList(Connection conn, int pageNo, int pageSize, String condation) {
		StringBuffer sbSql = new StringBuffer();

		//��һ�з���
//		sbSql.append("select a.item_no, a.item_name, a.spec, a.pattern, a.item_category_id, ")
//		     .append("b.name as item_category_name, a.item_unit_id, c.name as item_unit_name ")
//		     .append("from t_items a, t_data_dict b, t_data_dict c ")
//		     .append("where a.item_category_id=b.id and a.item_unit_id=c.id and a.item_no=?");

//        //�ڶ��з��� 		
//		sbSql.append("select a.item_no, a.item_name, a.spec, a.pattern, a.category as category_id, ")
//			 .append("(select b.name from t_data_dict b where a.category=b.id) as category_name, ")
//			 .append("a.unit as unit_id, ")
//			 .append("(select c.name from t_data_dict c where a.unit=c.id) as unit_name ")
//			 .append("from t_items a where a.item_no=?");
		
		sbSql.append("select * ")
			.append("from (")
				.append("select i.*, rownum rn from (")
				.append("select a.item_no, a.item_name, a.spec, a.pattern, a.item_category_id, ")
				.append("b.name as item_category_name, a.item_unit_id, c.name as item_unit_name, a.file_name ")
				.append("from t_items a, t_data_dict b, t_data_dict c ")
				.append("where a.item_category_id=b.id and a.item_unit_id=c.id  ");
				if (condation != null && !"".equals(condation)) {
					sbSql.append(" and (a.item_no like '" + condation + "%' or a.item_name like '" + condation + "%') ");
				}
				sbSql.append(" order by a.item_no")
				.append(") i where rownum<=? ")
				.append(") ")
				.append("where rn >? ");
		System.out.println("sql=" + sbSql.toString());
				
		//ͨ��������־�����¼����log4j, ����info,debug,error...
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel pageModel = null;
		try {
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setInt(1, pageNo * pageSize);
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			rs = pstmt.executeQuery();
			List itemList = new ArrayList();
			while (rs.next()) {
				Item item = new Item();
				item.setItemNo(rs.getString("item_no"));
				item.setItemName(rs.getString("item_name"));
				item.setSpec(rs.getString("spec"));
				item.setPattern(rs.getString("pattern"));
				//����ItemCategory
				ItemCategory ic = new ItemCategory();
				ic.setId(rs.getString("item_category_id"));
				ic.setName(rs.getString("item_category_name"));
				item.setItemCategory(ic);
				
				//����ItemUnit
				ItemUnit iu = new ItemUnit();
				iu.setId(rs.getString("item_unit_id"));
				iu.setName(rs.getString("item_unit_name"));
				item.setItemUnit(iu);
				
				item.setFileName(rs.getString("file_name"));
				
				itemList.add(item);
			}
			pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(itemList);
			//��������ȡ�ü�¼��
			int totalRecords = getTotalRecords(conn, condation);
			pageModel.setTotalRecords(totalRecords);
		}catch(SQLException e) {
			e.printStackTrace();
			//��¼����־�ļ� error
			throw new ApplicationException("��ҳ��ѯʧ��");
		}finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
		return pageModel;
	}

	/**
	 * ��������ȡ�ü�¼��
	 * @param conn
	 * @param queryStr
	 * @return
	 */
	private int getTotalRecords(Connection conn, String condation) 
	throws SQLException {
		String sql = "select count(*) from t_items ";
		if (condation != null && !"".equals(condation)) {
			sql+="where item_no like '" + condation + "%' or item_name like '" + condation + "%' ";
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int temp = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			temp = rs.getInt(1);
		}finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
		return temp;
	}	
	
	public void modifyItem(Connection conn, Item item) {
		String sql = "update t_items set item_name=?, spec=?, pattern=?, item_category_id=?, item_unit_id=?, file_name=? ";
		sql+=" where item_no=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItemName());
			pstmt.setString(2, item.getSpec());
			pstmt.setString(3, item.getPattern());
			pstmt.setString(4, item.getItemCategory().getId());
			pstmt.setString(5, item.getItemUnit().getId());
			pstmt.setString(6, item.getFileName());
			pstmt.setString(7, item.getItemNo());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("�޸�����ʧ�ܣ�");
		}finally {
			DbUtil.close(pstmt);
		}
	}

	
}
