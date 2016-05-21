package com.bjpowernode.drp.util.datadict.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bjpowernode.drp.util.DbUtil;
import com.bjpowernode.drp.util.datadict.domain.ClientLevel;
import com.bjpowernode.drp.util.datadict.domain.ItemCategory;
import com.bjpowernode.drp.util.datadict.domain.ItemUnit;

/**
 * 采用单例完成
 * @author cnwl
 *
 */
public class DataDictManager {
	
	
	private static DataDictManager instance = new DataDictManager();
	
	private DataDictManager() {}
	
	public static DataDictManager getInstance() {
		return instance;
	}
	
	
	/**
	 * 取得分销商级别列表
	 * @return
	 */
	public List<ClientLevel> findClientLevelList(){
		String sql = "select id,name from t_data_dict where category='A'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		List<ClientLevel> list = new ArrayList<ClientLevel>();
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ClientLevel cl = new ClientLevel();
				cl.setId(rs.getString("id"));
				cl.setName(rs.getString("name"));
				list.add(cl);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return list;
	}
	
	/**
	 * 取得类别物料列表
	 * @return
	 */
	public List<ItemCategory> findItemCategoryList(){
		String sql = "select id,name from t_data_dict where category='C'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		List<ItemCategory> list = new ArrayList<ItemCategory>();
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ItemCategory ic = new ItemCategory();
				ic.setId(rs.getString("id"));
				ic.setName(rs.getString("name"));
				list.add(ic);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return list;
	}
	
	
	/**
	 * 取得物料单位信息
	 * @return
	 */
	public List<ItemUnit> findItemUnitList(){
		String sql = "select id,name from t_data_dict where category='D'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		List<ItemUnit> list = new ArrayList<ItemUnit>();
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ItemUnit iu = new ItemUnit();
				iu.setId(rs.getString("id"));
				iu.setName(rs.getString("name"));
				list.add(iu);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return list;
	}
	
	
	
}
