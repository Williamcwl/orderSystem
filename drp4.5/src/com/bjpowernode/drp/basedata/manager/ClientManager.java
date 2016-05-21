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
 * ���õ���ʵ��
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
	 * ����HTML�ַ���
	 * @return
	 */
	public String getClientTreeHTMLString(){
		
		return new ClientTreeReader().getClientTreeHTMLString();
	}
	
	/**
	 * ����id��ѯ�����̻�����
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
				
				//�����̼���
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
	 * �޸ķ����̻�����
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
	 * ��ӷ����̻�����
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
			//�ֶ����������ύ
			DbUtil.beginTransaction(conn);
			//ȡ�����
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
			//���ΪҶ��
			if (Constants.YES.equals(parentClientOrRegion.getIsLeaf())) {
				//�޸�Ϊ��Ҷ��
				modifyIsLeafField(conn, clientOrRegion.getPid(), Constants.NO);
			}
			//�ύ����
			DbUtil.commitTransaction(conn);
		}catch(Exception e) {
			e.printStackTrace();
			//�ع�����
			DbUtil.rollbackTransaction(conn);
		}finally {
			DbUtil.close(pstmt);
			DbUtil.resetConnection(conn);
			DbUtil.close(conn);
		}
	}
	
	/**
	 * �޸�isLeaf�ֶ�
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
	 * ���ݷ����̴����ѯ
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
	 * ɾ�������̻�����
	 * @param id
	 */
	public void delClientOrRegion(int id) {
		Connection conn = null;
		
		try{
			conn = DbUtil.getConnection();
			//�ֶ���������
			DbUtil.beginTransaction(conn);
			
			//���游�ڵ����
			Client currentNode = findClientOrRegionById(id);
			
			//�ݹ�ɾ�����ڵ�
			recursionDelNode(conn,id);
			
			if(getChildren(conn,currentNode.getPid()) == 0 ){
				//�޸�ΪҶ�ӽڵ�
				modifyIsLeafField(conn,currentNode.getPid(),Constants.YES);
			}
			
			
			//�ύ����
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
	 * �ݹ�ɾ��
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
			//ɾ������
			delNode(conn,id);
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}
	
	/**
	 * ɾ���ڵ�
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
	 * ȡ��ָ���ڵ�ĺ�����Ŀ
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
	 * ��ѯ���еĹ���������
	 * 
	 * ����t_client��
	 * 
	 * @param pageNo
	 *            �ڼ�ҳ
	 * @param pageSize
	 *            ÿҳ������
	 * @param queryStr
	 *            ��ѯ����
	 * @return pageMode����
	 */
	@SuppressWarnings("unchecked")
	public PageModel<Client> findAllClient(int pageNo, int pageSize, String queryStr) {
		return null;
	}
	
	/**
	 * ��ѯ���е��跽�ͻ�
	 * 
	 * ����v_aim_client��ͼ
	 * 
	 * @param pageNo
	 *            �ڼ�ҳ
	 * @param pageSize
	 *            ÿҳ������
	 * @param queryStr
	 *            ��ѯ����
	 * @return pageMode����
	 */
	public PageModel<AimClient> findAllAimClient(int pageNo, int pageSize, String queryStr) {
		return null;
	}
	
}
