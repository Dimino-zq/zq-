package com.hanb.filterJson.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hanb.filterJson.annotation.PowerJsonFilter;


public class FilterCache {
	private static Map<Integer, PowerJsonFilter[]> filterMap = new ConcurrentHashMap<>();
	static {
		filterMap = new ConcurrentHashMap<>();
	}
	/**
	 * 将过滤条件放入缓存
	 * @param hashCode
	 * @param filters
	 */
	public static void putFilterByObjKey(Integer hashCode, PowerJsonFilter[] filters) {
		filterMap.put(hashCode, filters);
	}
	public static void delFilterByObjKey(Integer hashCode) {
		filterMap.remove(hashCode);
	}
	public static PowerJsonFilter[] getFilterByObjKey(Integer hashCode) {
		return filterMap.get(hashCode);
	}
}
