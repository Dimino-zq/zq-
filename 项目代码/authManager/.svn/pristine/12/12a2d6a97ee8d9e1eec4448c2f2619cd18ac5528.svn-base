package com.hanb.filterJson.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {

	public static void main( String args[] ){
		 
	      // 按指定模式在字符串查找
		String nfield = "parentDepart:{departName,departId,school:{schoolId,schoolName}}";
//		String line="plan";
		String pattern = "^\\D*\\:\\{\\D*\\}$";//是否包含:{
	 
	      // 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);
		// 现在创建 matcher 对象
		Matcher m = r.matcher(nfield);
		if (m.find( )) {
			System.out.println(m.group());
			int index=nfield.indexOf(":{");
			String tmpField=nfield.substring(0, index);
			String tmpValue=nfield.substring(index+2,nfield.length()-1);
			System.out.println(tmpField);
			System.out.println(tmpValue);
	      } else {
	         System.out.println("NO MATCH");
	      }
	   }

}
