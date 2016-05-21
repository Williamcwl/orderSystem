package com.bjpowernode.drp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * ��װ���ݳ��ò���
 * 
 * */
public class DbUtil {

	//ȡ��Connection
	public static Connection getConnection(){
		
		Connection conn = null;
		
		/*try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.21.125:1521:drp";
			String username = "system";
			String password = "CWLcwl123";
			conn=DriverManager.getConnection(url,username,password);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}*/
		try{
			JdbcConfig jdbcConfig = XmlConfigReader.getInstance().getJdbcConfig();
			
			Class.forName(jdbcConfig.getDriverName());
			conn=DriverManager.getConnection(jdbcConfig.getUrl(),jdbcConfig.getUserName(),jdbcConfig.getPassword());
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			throw new ApplicationException("ϵͳ��������ϵϵͳ����Ա");
		}catch(SQLException e){
			e.printStackTrace();
			throw new ApplicationException("ϵͳ��������ϵϵͳ����Ա");
		}
		
		
		return conn;
	}
	
	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Statement pstmt){
		if(pstmt != null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static void main (String[] args){
		System.out.println(DbUtil.getConnection());
	}
	
	public static void close(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void beginTransaction(Connection conn){
		try{
			if(conn != null){
				if(conn.getAutoCommit()){
					conn.setAutoCommit(false);  //�ֶ��ύ
				}
			}
		}catch(SQLException e){}
		
	}
	
	public static void commitTransaction(Connection conn){
		try{
			if(conn != null){
				if(!conn.getAutoCommit()){
					conn.commit();
				}
			}
		}catch(SQLException e){}
		
	}
	
	public static void rollbackTransaction(Connection conn){
		try{
			if(conn != null){
				if(!conn.getAutoCommit()){
					conn.rollback();
				}
			}
		}catch(SQLException e){}
		
	}
	
	public static void resetConnection(Connection conn){
		try{
			if(conn != null){
				if(conn.getAutoCommit()){
					conn.setAutoCommit(false);
				}else{
					conn.setAutoCommit(true);
				}
			}
		}catch(SQLException e){}
		
	}
	
}
