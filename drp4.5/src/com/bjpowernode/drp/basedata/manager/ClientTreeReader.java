package com.bjpowernode.drp.basedata.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bjpowernode.drp.util.Constants;
import com.bjpowernode.drp.util.DbUtil;

/**
 * 完成分销商树的递归读取
 * @author cnwl
 *
 */
public class ClientTreeReader {
	
	private StringBuffer sbTreeHTML = new StringBuffer();
	
	
	/**
	 * 返回分销商树字符串
	 * @return
	 */
	public String getClientTreeHTMLString(){
		Connection conn = null;
		try{
			conn =DbUtil.getConnection();
			readClientTree(conn,0,0);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(conn);
		}
		
		return sbTreeHTML.toString();
	}
	
/*	*//**
	 * 递归读取分销商树
	 * 第一步：以最简单的方式读取分销商树形结果，可以显示到浏览器中即可
	 * 
	 * @param conn
	 * @param id
	 * @param level
	 * @throws SQLException 
	 *//*
	public void readClientTree(Connection conn,int id,int level) throws SQLException{
		String sql = "select * from t_client where pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				sbTreeHTML.append(rs.getString("name"))
				.append("<br>\n");
				if("N".equals(rs.getString("is_leaf"))){
					readClientTree(conn,rs.getInt("id"),level);
				}
			}	
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}*/
	
	
/*	*//**
	 * 递归读取分销商树
	 * 第二步：加入层次感
	 * 
	 * @param conn
	 * @param id
	 * @param level
	 * @throws SQLException 
	 *//*
	public void readClientTree(Connection conn,int id,int level) throws SQLException{
		String sql = "select * from t_client where pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				for(int i=0;i<level;i++){
					sbTreeHTML.append("&nbsp&nbsp;");
				}
				sbTreeHTML.append(rs.getString("name"))
				.append("<br>\n");
				if("N".equals(rs.getString("is_leaf"))){
					readClientTree(conn,rs.getInt("id"),level + 1);
				}
			}	
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}*/
	
	/**
	 * 递归读取分销商树
	 * 第三步：叶子节点前面加入一个“-”号，非叶子节点前边加入一个“+”号
	 * 
	 * @param conn
	 * @param id
	 * @param level
	 * @throws SQLException 
	 *//*
	public void readClientTree(Connection conn,int id,int level) throws SQLException{
		String sql = "select * from t_client where pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				for(int i=0;i<level;i++){
					sbTreeHTML.append("&nbsp&nbsp;");
				}

				if("N".equals(rs.getString("is_leaf"))){
					sbTreeHTML.append("+ ")
							.append(rs.getString("name"))
							.append("<br>\n");
					readClientTree(conn,rs.getInt("id"),level + 1);
				}else{
					sbTreeHTML.append("- ")
							.append(rs.getString("name"))
							.append("<br>\n");
				}
			}	
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}*/
	
	/**
	 * 递归读取分销商树
	 * 第四步：采用div生成树形结构
	 * @param conn
	 * @param id
	 * @param level
	 * @throws SQLException 
	 */
	private void readClientTree(Connection conn, int id, int level) 
			throws SQLException {
				String sql = "select * from t_client where pid=?";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						sbTreeHTML.append("<div>");
						sbTreeHTML.append("\n");
						for (int i=0; i<level; i++) {
							sbTreeHTML.append("<img src=\"../images/white.gif\">");
							sbTreeHTML.append("\n");
						}
						//if ("N".equals(rs.getString("is_leaf"))) {
						if (Constants.NO.equals(rs.getString("is_leaf"))) {
							sbTreeHTML.append("<img alt=\"展开\" style=\"cursor:hand;\" onClick=\"display('" + rs.getInt("id") + "');\" id=\"img" + rs.getInt("id") + "\" src=\"../images/plus.gif\">");
							sbTreeHTML.append("\n");
							sbTreeHTML.append("<img id=\"im" + rs.getInt("id") + "\" src=\"../images/closedfold.gif\">");
							sbTreeHTML.append("\n");
							sbTreeHTML.append("<a href=\"client_node_crud.jsp?id=" + rs.getInt("id") + "\" target=\"clientDispAreaFrame\">" + rs.getString("name") + "</a>");
							sbTreeHTML.append("\n");
							sbTreeHTML.append("<div style=\"display:none;\" id=\"div" + rs.getInt("id") + "\">");
							sbTreeHTML.append("\n");
							readClientTree(conn, rs.getInt("id"), level + 1);
							sbTreeHTML.append("</div>");
							sbTreeHTML.append("\n");
						}else {
							sbTreeHTML.append("<img src=\"../images/minus.gif\">");
							sbTreeHTML.append("\n");
							sbTreeHTML.append("<img src=\"../images/openfold.gif\">");
							sbTreeHTML.append("\n");
							//if ("Y1".equals(rs.getString("is_client"))) {
							if (Constants.YES.equals(rs.getString("is_client"))) {
								sbTreeHTML.append("<a href=\"client_crud.jsp?id=" + rs.getInt("id") + "\" target=\"clientDispAreaFrame\">" + rs.getString("name") +  "</a>");
							}else {
								sbTreeHTML.append("<a href=\"client_node_crud.jsp?id=" + rs.getInt("id") + "\" target=\"clientDispAreaFrame\">" + rs.getString("name") + "</a>");
							}
							sbTreeHTML.append("\n");
						}
						sbTreeHTML.append("</div>");
						sbTreeHTML.append("\n");
					}
				}finally {
					DbUtil.close(rs);
					DbUtil.close(pstmt);
				}
			}	
	
	
	
	
	
}
