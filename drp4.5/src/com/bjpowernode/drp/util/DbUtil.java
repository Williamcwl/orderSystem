package com.bjpowernode.drp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 封装数据常用操作
 * 
 * */
public class DbUtil {

	//取得Connection
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
			throw new ApplicationException("系统错误，请联系系统管理员");
		}catch(SQLException e){
			e.printStackTrace();
			throw new ApplicationException("系统错误，请联系系统管理员");
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
					conn.setAutoCommit(false);  //手动提交
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
