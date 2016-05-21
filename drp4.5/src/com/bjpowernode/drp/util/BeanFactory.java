package com.bjpowernode.drp.util;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bjpowernode.drp.basedata.dao.ItemDao;
import com.bjpowernode.drp.basedata.manager.ItemManager;

/**
 * ���󹤳�,��Ҫ��������ϵ�еĲ�Ʒ��
 *   1��Managerϵ��
 *   2��Daoϵ�в�Ʒ
 * @author Administrator
 *
 */
public class BeanFactory {

	private static BeanFactory instance = new BeanFactory();
	
	private final String beansConfigFile = "beans-config.xml";
	
	//����Service��ض���
	private Map serviceMap = new HashMap();
	
	//����Dao��ض���
	private Map daoMap = new HashMap();
	
	private Document doc;
	
	private BeanFactory() {
		try {
			doc = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(beansConfigFile));
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static BeanFactory getInstance() {
		return instance;
	}
	
	/**
	 * ���ݲ�Ʒ���ȡ��Serviceϵ�в�Ʒ
	 * @param beanId
	 * @return
	 */
	public synchronized Object getServiceObject(Class c){
		//���������ض���ʵ��������
		if (serviceMap.containsKey(c.getName())) {
			return serviceMap.get(c.getName());
		}
		Element beanElt = (Element)doc.selectSingleNode("//service[@id=\"" + c.getName() + "\"]");
		String className = beanElt.attributeValue("class");
		Object service = null;
		try {
			service = Class.forName(className).newInstance();
			
			//�������ö�Ķ���ŵ�Map��
			serviceMap.put(c.getName(), service);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return service;
	}
	
	/**
	 * ���ݲ�Ʒ���ȡ��Serviceϵ�в�Ʒ
	 * @param beanId
	 * @return
	 */
	public synchronized Object getDaoObject(Class c){
		//���������ض���ʵ��������
		if (daoMap.containsKey(c.getName())) {
			return daoMap.get(c.getName());
		}
		Element beanElt = (Element)doc.selectSingleNode("//dao[@id=\"" + c.getName() + "\"]");
		String className = beanElt.attributeValue("class");
		Object dao = null;
		try {
			dao = Class.forName(className).newInstance();
			
			//�������ö�Ķ���ŵ�Map��
			daoMap.put(c.getName(), dao);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return dao;
	}
	
	public static void main(String[] args) {
		//ItemManager itemManager = (ItemManager)BeanFactory.getInstance().getServiceObject("itemManager");
		//System.out.println(itemManager);
		ItemDao itemDao = (ItemDao)BeanFactory.getInstance().getDaoObject(ItemDao.class);
		System.out.println(itemDao);
	}
}
