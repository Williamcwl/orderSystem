package com.bjpowernode.drp.sysmgr.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bjpowernode.drp.sysmgr.domain.User;
import com.bjpowernode.drp.util.DbUtil;
import com.bjpowernode.drp.util.PageModel;

/*
 * 采用单例管理用户
 * */
public class UserManager {
	private static UserManager instance = new UserManager();
	
	private UserManager(){}
	
	public static UserManager getInstance(){
		return instance;
	}
	
	//添加用户
	public void addUser(User user){
		String sql = "insert into t_user (user_id,user_name,password,contact_tel,email, create_date) values(?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,user.getUserId());
			pstmt.setString(2,user.getUserName());
			pstmt.setString(3,user.getPassword());
			pstmt.setString(4,user.getContactTel());
			pstmt.setString(5,user.getEmail());
			pstmt.setTimestamp(6,new Timestamp(new Date().getTime()));
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbUtil.close(conn);
			DbUtil.close(pstmt);
		}
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserById(String userId) {
		String sql = "select user_id, user_name, password, contact_tel, email, create_date from t_user where user_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setContactTel(rs.getString("contact_tel"));
				user.setEmail(rs.getString("email"));
				user.setCreateDate(rs.getTimestamp("create_date"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return user;
	}
	
	/*
	 * 分页查询
	 * @param pageNo 第几页
	 * @param pageSize 每页多少条数据
	 * @return pageModel
	 * */
	public PageModel<User> findUserList(int pageNo, int pageSize){
		StringBuffer sbSql = new StringBuffer();	
		sbSql.append("select user_id, user_name, password, contact_tel, email, create_date ")
			.append("from ")
			.append("( ")
			.append("select rownum rn, user_id, user_name, password, contact_tel, email, create_date ")
			.append("from ")
			.append("( ")
			.append("select user_id, user_name, password, contact_tel, email, create_date from t_user where user_id <> 'root' order by user_id ")
			.append(")  where rownum <= ? ")
			.append(")  where rn > ? ");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<User> pageModel = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setInt(1, pageNo * pageSize);
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			
			rs = pstmt.executeQuery();
			List<User> userList = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setContactTel(rs.getString("contact_tel"));
				user.setEmail(rs.getString("email"));
				user.setCreateDate(rs.getTimestamp("create_date"));
				userList.add(user);
			}
			pageModel = new PageModel<User>();
			pageModel.setList(userList);
			pageModel.setTotalRecords(getTotalRecords(conn));
			pageModel.setPageSize(pageSize);
			pageModel.setPageNo(pageNo);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		
		return pageModel;
	}
	
	/**
	 * 取得总记录数
	 * @param conn
	 * @return
	 */
	private int getTotalRecords(Connection conn) throws SQLException{
		String sql = "select count(*) from t_user where user_id <> 'root'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
		return count;
	}
	
	/**
	 * 修改用户
	 * @param user
	 */
	public void modifyUser(User user){
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("update t_user ")
		.append("set    user_name   = ?, ")
		       .append("password    = ?, ")
		       .append("contact_tel = ?, ")
		       .append("email       = ? ") 
		.append("where  user_id     = ? ");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getContactTel());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getUserId());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 删除用户
	 * @param userId
	 */
	public void delUser(String userId){
		String sql = "delete from t_user where user_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userId);
			pstmt.executeUpdate();
		}catch(SQLException e){
			
		}finally{
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}
	
/*	*//**
	 * 批量删除用户
	 * 用一条语句删除
	 * @param userIds
	 *//*
	public void delUser(String[] userIds){
		StringBuilder sbStr = new StringBuilder();
		for(int i=0; i < userIds.length; i++){
			sbStr.append("'")
			.append(userIds[i])
			.append("'")
			.append(",");
		}
		String sql = "delete from t_user where user_id in(" +sbStr.substring(0,sbStr.length() - 1) + ")";
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DbUtil.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbUtil.close(conn);
			DbUtil.close(stmt);
		}
	}*/
	
	
	/**
	 * 批量删除用户
	 * 用一条语句删除
	 * @param userIds
	 */
	public void delUser(String[] userIds){
		StringBuilder sbStr = new StringBuilder();
		for(int i=0; i < userIds.length; i++){
			sbStr.append("?");
			if(i < (userIds.length - 1)){
				sbStr.append(",");
			}
		}
		String sql = "delete from t_user where user_id in(" +sbStr.toString() + ")";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			for(int i=0;i<userIds.length;i++){
				pstmt.setString(i + 1, userIds[i]);
			}
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbUtil.close(conn);
			DbUtil.close(pstmt);
		}
	}
	
	
	/**
	 * 用户登录
	 * @param userId
	 * @param password
	 * @return
	 */
	public User login(String userId,String password){
		User user = findUserById(userId);
		if(user == null){
			throw new UserNotFoundException("用户密码不正确，代码=【" + userId +"]");
		}
		if(!user.getPassword().equals(password)){
			throw new PasswordNotCorrentException("密码错误");
		}
		return user;
	}
	
	/**
	 * 用户登录
	 * 演示sql注入
	 * @param userId
	 * @param password
	 * @return
	 *//*
	public boolean login(String userId,String password){
		String sql = "select * from t_user where user_id='" + userId + "' and password='" + password + "'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<User> pageModel = null;
		boolean success = false;
	
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				success = true;
				System.out.println("用户和密码正确");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return success;
	}*/
	
}
