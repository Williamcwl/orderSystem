package com.bjpowernode.drp.basedata.domain;

import com.bjpowernode.drp.util.datadict.domain.ItemCategory;
import com.bjpowernode.drp.util.datadict.domain.ItemUnit;

/**
 * 物料实体类
 * @author cnwl
 *
 */
public class Item {
	private String itemNo;
	
	private String itemName;
	
	private String spec;
	
	private String pattern;
	
	private ItemCategory itemCategory;
	
	private ItemUnit itemUnit;
	
	private String fileName;
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSpec() {
		return spec == null ? "" :spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getPattern() {
		return pattern == null ? "":pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	public ItemUnit getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(ItemUnit itemUnit) {
		this.itemUnit = itemUnit;
	}

	
	
}
