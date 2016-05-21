package com.bjpowernode.drp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ID生成器
 * @author cnwl
 *
 */
public class IdGenerator {
/*	public static synchronized  int generate(String tableName){
		String sql = "select value from t_table_id where table_name=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int value = 0;
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,tableName);
			rs = pstmt.executeQuery();
			if(!rs.next()){
				throw new RuntimeException();
			}
			value = rs.getInt("value");
			value++; //自加
			modifyValueField(conn,tableName,value);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		
		return value;
	}*/
	
	public static int generate(String tableName){
		String sql = "select value from t_table_id where table_name=? for update";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int value = 0;
		try{
			conn = DbUtil.getConnection();
			//开始事务
			DbUtil.beginTransaction(conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,tableName);
			rs = pstmt.executeQuery();
			if(!rs.next()){
				throw new RuntimeException();
			}
			value = rs.getInt("value");
			value++; //自加
			modifyValueField(conn,tableName,value);
		}catch(Exception e){
			e.printStackTrace();
			//回滚事务
			DbUtil.commitTransaction(conn);
			throw new RuntimeException();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.resetConnection(conn); //重置Connection的状态
			DbUtil.close(conn);
		}
		
		return value;
	}
	
	
	
	/**
	 * 更新序列字段的值
	 * @param conn
	 * @param value
	 * @throws SQLException 
	 */
	private static void modifyValueField(Connection conn, String tableName,int value) throws SQLException{
		String sql = "update t_table_id set value=? where table_name=?";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value);
			pstmt.setString(2,tableName);
			pstmt.executeUpdate();
			
		}finally{
			DbUtil.close(pstmt);
		}
	}
	
	
	public static void main(String[] args){
		int retValue = IdGenerator.generate("t_client");
		System.out.println(retValue);
	}
	
	
	
	
	
}
