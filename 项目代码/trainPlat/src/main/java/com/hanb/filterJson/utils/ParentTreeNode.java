package com.hanb.filterJson.utils;

public class ParentTreeNode{
	private int leftLen;
	private String parentFieldName;
	private FieldNode childFieldNode;
	
	public int getLeftLen() {
		return leftLen;
	}
	public void setLeftLen(int leftLen) {
		this.leftLen = leftLen;
	}
	public String getParentFieldName() {
		return parentFieldName;
	}
	public void setParentFieldName(String parentFieldName) {
		this.parentFieldName = parentFieldName;
	}
	public FieldNode getChildFieldNode() {
		return childFieldNode;
	}
	public void setChildFieldNode(FieldNode childFieldNode) {
		this.childFieldNode = childFieldNode;
	}
	
	
}
