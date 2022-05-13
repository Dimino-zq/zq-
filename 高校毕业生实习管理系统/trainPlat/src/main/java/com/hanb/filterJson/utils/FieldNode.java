package com.hanb.filterJson.utils;

import java.util.HashMap;
import java.util.Map;

public class FieldNode {
	
	private Map<String,Object> fields;
	public  FieldNode() {
		this.fields=new HashMap<>();
		
	}
	public void  putConField(String field) {
		fields.put(field, true);
	}
	public void  delConField(String field) {
		fields.remove(field);
	}
	public void  putObjField(String field,FieldNode chiledField) {
		fields.put(field,chiledField);
	}
	/**
	 * 是否存在该字段
	 * @param field
	 * @return
	 */
	public boolean isInclude(String field) {
		Object obj=fields.get(field);
		if (null!=obj) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 获取该字段的 所有子字段，可能没有
	 * @param field
	 * @return
	 */
	
	public FieldNode getChildField(String field) {
		Object obj=fields.get(field);
		if(obj==null) {
			return null;
		}else if(obj instanceof FieldNode ) {
			return (FieldNode) obj;
		}else {
			return null;
		}
	}
	@Override
	public String toString() {
		String ss="";
		for (String key : fields.keySet()) {
			if (fields.get(key) instanceof FieldNode) {
				ss+=key+":{"+fields.get(key).toString() +"},";
			}else {
				ss+=key+",";
			}
		}
		if (!"".equals(ss)) {
			ss=ss.substring(0,ss.length()-1);
		}
		return ss;
	}
}
