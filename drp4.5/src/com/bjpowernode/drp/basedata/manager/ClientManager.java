package com.bjpowernode.drp.basedata.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bjpowernode.drp.basedata.domain.AimClient;
import com.bjpowernode.drp.basedata.domain.Client;
import com.bjpowernode.drp.util.Constants;
import com.bjpowernode.drp.util.DbUtil;
import com.bjpowernode.drp.util.IdGenerator;
import com.bjpowernode.drp.util.PageModel;
import com.bjpowernode.drp.util.datadict.domain.ClientLevel;

/**
 * 采用单例实现
 * @author cnwl
 *
 */
public class ClientManager {
	
	private static ClientManager instance =  new ClientManager();
	
	private ClientManager(){};
	
	public static ClientManager getInstance(){
		return instance;
	}
	
	
	/**
	 * 返回HTML字符串
	 * @return
	 */
	public String getClientTreeHTMLString(){
		
		return new ClientTreeReader().getClientTreeHTMLString();
	}
	
	/**
	 * 根据id查询分销商或区域
	 * @param id
	 * @return
	 */
	public Client findClientOrRegionById(int id){
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select a.id, a.pid, a.name, a.client_id, a.client_level_id, ")
			.append("b.name as client_level_name, a.bank_acct_no, a.contact_tel, a.address, a.zip_code, ")
			.append("a.is_client, a.is_leaf ")
			.append("from t_client a left join t_data_dict b on a.client_level_id=b.id where a.id=?");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Client client = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				client = new Client();
				client.setId(rs.getInt("id"));
				client.setPid(rs.getInt("pid"));
				client.setName(rs.getString("name"));
				client.setClientId(rs.getString("client_id"));
				
				//分销商级别
				ClientLevel clientLevel = new ClientLevel();
				clientLevel.setId(rs.getString("client_level_id"));
				clientLevel.setName(rs.getString("client_level_name"));
				client.setClientLevel(clientLevel);
				
				client.setBankAcctNo(rs.getString("bank_acct_no"));
				client.setContactTel(rs.getString("contact_tel"));
				client.setAddress(rs.getString("address"));
				client.setZipCode(rs.getString("zip_code"));
				client.setIsClient(rs.getString("is_client"));
				client.setIsLeaf(rs.getString("is_leaf"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return client;
	}
	
	
	/**
	 * 修改分销商或区域
	 */
	public void modifyClientOrRegion(Client clientOrRegion) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("update t_client ")
		.append("set    client_level_id = ?, ")
		       .append("name            = ?, ")
		       .append("bank_acct_no    = ?, ")
		       .append("contact_tel     = ?, ")
		       .append("address         = ?, ")
		       .append("zip_code        = ? ")
		.append("where  id              = ? ");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setString(1, clientOrRegion.getClientLevel().getId());
			pstmt.setString(2, clientOrRegion.getName());
			pstmt.setString(3, clientOrRegion.getBankAcctNo());
			pstmt.setString(4, clientOrRegion.getContactTel());
			pstmt.setString(5, clientOrRegion.getAddress());
			pstmt.setString(6, clientOrRegion.getZipCode());
			pstmt.setInt(7, clientOrRegion.getId());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 添加分销商或区域
	 * @param clientOrRegion
	 */
	public void addClientOrRegion(Client clientOrRegion){
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("insert into t_client ( ")
				   .append("id, pid, client_level_id, ") 
				   .append("name, client_id, bank_acct_no, ") 
				   .append("contact_tel, address, zip_code, ") 
				   .append("is_leaf, is_client) ") 
				.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			//手动控制事务提交
			DbUtil.beginTransaction(conn);
			//取得序号
			pstmt.setInt(1, IdGenerator.generate("t_client"));
			pstmt.setInt(2, clientOrRegion.getPid());
			pstmt.setString(3, clientOrRegion.getClientLevel().getId());
			pstmt.setString(4, clientOrRegion.getName());
			pstmt.setString(5, clientOrRegion.getClientId());
			pstmt.setString(6, clientOrRegion.getBankAcctNo());
			pstmt.setString(7, clientOrRegion.getContactTel());
			pstmt.setString(8, clientOrRegion.getAddress());
			pstmt.setString(9, clientOrRegion.getZipCode());
			pstmt.setString(10, clientOrRegion.getIsLeaf());
			pstmt.setString(11, clientOrRegion.getIsClient());
			pstmt.executeUpdate();
			
			Client parentClientOrRegion = findClientOrRegionById(clientOrRegion.getPid());
			//如果为叶子
			if (Constants.YES.equals(parentClientOrRegion.getIsLeaf())) {
				//修改为非叶子
				modifyIsLeafField(conn, clientOrRegion.getPid(), Constants.NO);
			}
			//提交事务
			DbUtil.commitTransaction(conn);
		}catch(Exception e) {
			e.printStackTrace();
			//回滚事务
			DbUtil.rollbackTransaction(conn);
		}finally {
			DbUtil.close(pstmt);
			DbUtil.resetConnection(conn);
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 修改isLeaf字段
	 * @param conn
	 * @param id 
	 * @param leaf Y/N
	 */
	private void modifyIsLeafField(Connection conn, int id, String leaf)
	throws SQLException {
		String sql = "update t_client set is_leaf=? where id=?";
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, leaf);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} finally {
			DbUtil.close(pstmt);	
		}
	}
	
	
	/**
	 * 根据分销商代码查询
	 * @param clientId
	 * @return
	 */
	public boolean findClientByClientId(String clientId){
		String sql = "select count(*) from t_client where client_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, clientId);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1) > 0 ? true :false;
		}catch(SQLException e){
			e.printStackTrace();
			
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		
		return flag;
	}
	
	
	/**
	 * 删除分销商或区域
	 * @param id
	 */
	public void delClientOrRegion(int id) {
		Connection conn = null;
		
		try{
			conn = DbUtil.getConnection();
			//手动控制事务
			DbUtil.beginTransaction(conn);
			
			//保存父节点对象
			Client currentNode = findClientOrRegionById(id);
			
			//递归删除树节点
			recursionDelNode(conn,id);
			
			if(getChildren(conn,currentNode.getPid()) == 0 ){
				//修改为叶子节点
				modifyIsLeafField(conn,currentNode.getPid(),Constants.YES);
			}
			
			
			//提交事务
			DbUtil.commitTransaction(conn);
		}catch(Exception e){
			e.printStackTrace();
			DbUtil.rollbackTransaction(conn);
		}finally{
			DbUtil.resetConnection(conn);
			DbUtil.close(conn);
		}
		
		
	}
	
	/**
	 * 递归删除
	 * @param conn
	 * @param id
	 * @throws SQLException 
	 */
	private void recursionDelNode(Connection conn,int id) throws SQLException{
			
		String sql = "select * from t_client where pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(Constants.NO.equals(rs.getString("is_leaf"))){
					recursionDelNode(conn,rs.getInt("id"));
				}
				delNode(conn,rs.getInt("id"));
			}
			//删除自身
			delNode(conn,id);
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}
	
	/**
	 * 删除节点
	 * @param conn
	 * @param id
	 * @throws SQLException 
	 */
	private void delNode(Connection conn ,int id) throws SQLException{
		String sql = "delete from t_client where id=?";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		}finally{
			DbUtil.close(pstmt);
		}
	}
	
	/**
	 * 取得指定节点的孩子数目
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	private int getChildren(Connection conn , int id) throws SQLException{
		String sql = "select count(*) as c from t_client where pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("c");
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
		return count;
	}
	
	
	/**
	 * 查询所有的供方分销商
	 * 
	 * 操作t_client表
	 * 
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            每页多少条
	 * @param queryStr
	 *            查询条件
	 * @return pageMode对象
	 */
	@SuppressWarnings("unchecked")
	public PageModel<Client> findAllClient(int pageNo, int pageSize, String queryStr) {
		return null;
	}
	
	/**
	 * 查询所有的需方客户
	 * 
	 * 操作v_aim_client视图
	 * 
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            每页多少条
	 * @param queryStr
	 *            查询条件
	 * @return pageMode对象
	 */
	public PageModel<AimClient> findAllAimClient(int pageNo, int pageSize, String queryStr) {
		return null;
	}
	
}
