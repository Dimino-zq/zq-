package com.hanb.filterJson.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterTools {

	
	public static String getConvert(String fieldName) {
        String first = fieldName.substring(0, 1);
        String after = fieldName.substring(1); //substring(1),获取索引位置1后面所有剩余的字符串
        first = first.toUpperCase();
        return "get"+first + after;
    }
	public static String setConvert(String fieldName) {
        String first = fieldName.substring(0, 1);
        String after = fieldName.substring(1); //substring(1),获取索引位置1后面所有剩余的字符串
        first = first.toUpperCase();
        return "set"+first + after;
    }
	//解析配置的字段
	public static FieldNode setChildFieldNode(String[] fields) {
		FieldNode fieldRoot= new FieldNode();
		for (String field:fields) {
			String nfield=field.replaceAll("\\s*", ""); //可以替换大部分空白字符， 不限于空格 ；
			String pattern = "^\\D*\\:\\{\\D*";//是否包含:{
			// 创建 Pattern 对象
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(nfield);
			//parentDepart:{departName,departId,school:{schoolId,schoolName}};
			if (m.find( )) {//說明是复杂的字段
				//在这里简单判断{}是否匹配
				
				//取key 和value
				int index=nfield.indexOf(":{");
				String tmpField=nfield.substring(0, index);
				String tmpValue=nfield.substring(index+2);
				if (tmpValue.endsWith("}")) {
					tmpValue=tmpValue.substring(0,tmpValue.length()-1);
				}else {
					throw new java.lang.RuntimeException(tmpField+":{"+tmpValue+"未找到匹配的'}'");
				}
				
				fieldRoot.putObjField(tmpField, setChildFieldNode(splitExpression(tmpValue.split(","))));
			}else {
				fieldRoot.putConField(nfield);
			}	
		}
		return fieldRoot;
	}
	private static String[] splitExpression(String[]  childFields) {
		List<String> ls=new ArrayList<>();
		//{"posts:{postName,postId,grants:{grantCode,y:{y1,y2},grantLvl},x:{x1,x2}}"}
		for(int i=0;i<childFields.length;i++) {
			int index=childFields[i].indexOf(":{");
			if (index==-1) {
				ls.add(childFields[i]);
			}else {
				Stack<String> stack = new Stack<String>() ;
				stack.push(childFields[i]);
				i++;
				for(;i<childFields.length;i++) {
					if(childFields[i].endsWith("}")) {//匹配,出站
						String tmpField=childFields[i];
						while(true) {
							String tt=stack.pop();
							tmpField=tt+","+tmpField;
							if (tt.indexOf(":{")>-1) {
								break;
							}
						}
						if (stack.empty()) {
							tmpField=tmpField.replaceAll("@", ":");
							ls.add(tmpField);
							break;
						}else {
							tmpField=tmpField.replaceAll(":", "@");
							stack.push(tmpField);
						}
					}else {
						stack.push(childFields[i]);
					}
				}
				if (!stack.empty()) {
					String cc=stack.toString().replaceAll("@", ":");
					throw new java.lang.RuntimeException("表达式："+cc+"存在:{ }不匹配错误！")   ;
				}
			}
		}
		return ls.toArray(new String[] {});
	}
}
