package com.bjpowernode.drp.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ���õ���ģʽ����sys-config.xml�ļ� 
 * @author Administrator
 *
 */
public class XmlConfigReader {
	
//	//����ʽ(Ԥ�ȼ���)
//	private static XmlConfigReader instance = new XmlConfigReader();
//	
//	private XmlConfigReader() {
//		
//	}
//	
//	public static XmlConfigReader getInstance() {
//		return instance;
//	}

	//����ʽ���ӳټ���lazy��
	private static XmlConfigReader instance = null;
	
//	//����dao����������
//	//key=���ƣ�value=����������·��
//	private Map<String, String> daoFactoryMap = new HashMap<String, String>();
	
	//����jdbc���������Ϣ
	private JdbcConfig jdbcConfig = new JdbcConfig();
	
	private XmlConfigReader() {
		SAXReader reader = new SAXReader();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("sys-config.xml");
		try {
			Document doc = reader.read(in);
			
			//ȡ��jdbc���������Ϣ
			Element driverNameElt = (Element)doc.selectObject("/config/db-info/driver-name");
			Element urlElt = (Element)doc.selectObject("/config/db-info/url");
			Element userNameElt = (Element)doc.selectObject("/config/db-info/user-name");
			Element passwordElt = (Element)doc.selectObject("/config/db-info/password");
			
			//����jdbc��ص�����
			jdbcConfig.setDriverName(driverNameElt.getStringValue());
			jdbcConfig.setUrl(urlElt.getStringValue());
			jdbcConfig.setUserName(userNameElt.getStringValue());
			jdbcConfig.setPassword(passwordElt.getStringValue());
			
			System.out.println("��ȡjdbcConfig-->>" + jdbcConfig);
			
//			//ȡ��DaoFactory��Ϣ
//			List daoFactorylist = doc.selectNodes("/config/dao-factory/*");
//			for (int i=0; i<daoFactorylist.size(); i++) {
//				Element daoFactoryElt = (Element)daoFactorylist.get(i);
//				String tagName = daoFactoryElt.getName();
//				String tagText = daoFactoryElt.getText();
//				System.out.println("��ȡDaoFactory-->>" + tagText);
//				
//				//���뵽Map��
//				daoFactoryMap.put(tagName, tagText);
//			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}			
	}
	
	public static synchronized XmlConfigReader getInstance() {
		if (instance == null) {
			instance = new XmlConfigReader();
		}
		return instance;
	}
	
	/**
	 * ����jdbc�������
	 * @return
	 */
	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}
	
//	/**
//	 * ���ݱ�ǩ����ȡ��DaoFactory������
//	 * @param name
//	 * @return daoFactory��������·��
//	 */
//	public String getDaoFactory(String name) {
//		return daoFactoryMap.get(name);
//	}
	
	public static void main(String[] args) {
//		String itemDaoFactory = XmlConfigReader.getInstance().getDaoFactory("item-dao-facotry");
//		System.out.println("itemDaoFactory=" + itemDaoFactory);
		//JdbcConfig jdbcConfig = XmlConfigReader.getInstance().getJdbcConfig();
//		System.out.println(jdbcConfig.getDriverName());
//		System.out.println(jdbcConfig.getUrl());
//		System.out.println(jdbcConfig.getUserName());
		//System.out.println(jdbcConfig);
	}
}
