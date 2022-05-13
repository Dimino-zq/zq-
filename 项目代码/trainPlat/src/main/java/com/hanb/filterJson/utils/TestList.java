package com.hanb.filterJson.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestList {

	public static void main(String[] args) {
	
		
		List<Map<String,Object>>  lsMap=new ArrayList<>();
		Map<String,Object> map1=new HashMap<>();
		map1.put("key1", "x");
		map1.put("key2", "y");
		map1.put("key3", "z");
		
		lsMap.add(map1);
		Map<String,Object> map2=new HashMap<>();
		map2.put("key1", "x2");
		map2.put("key2", "y2");
		map2.put("key3", "z2");
		lsMap.add(map2);
		
		
	}

}
