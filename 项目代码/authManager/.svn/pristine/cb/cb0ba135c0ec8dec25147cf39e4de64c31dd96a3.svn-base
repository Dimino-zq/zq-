package edu.hfu.auth.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.hfu.auth.entity.Authentication;
import edu.hfu.auth.entity.xmlBean.SysDictionary;
import edu.hfu.auth.entity.xmlBean.SysDictionaryList;

public class CacheData {
	
	private static List<SysDictionary> dictList=new ArrayList<>();//数据字典 列表
	private static Map<String,List<SysDictionary>> dictMap=new HashMap<>();//根据字典类型进行分类
	private static Map<String,Authentication>  authMap=new ConcurrentHashMap<>() ;
	
	public static List<SysDictionary> getDictList() {
		return dictList;
	}

	/**
	 * 根据类型获取 数据字典
	 * @param dictType
	 * @return
	 */
	public static List<SysDictionary> getDictListByType(String dictType){
		return dictMap.get(dictType);
	}
	/**
	 * 根据科研获取value
	 * @param dictKey
	 * @param dictType
	 * @return
	 */
	
	public static String getDictValByKey(String dictKey,String dictType) {
		List<SysDictionary> ls=dictMap.get(dictType);
		String dictVal="";
		for (SysDictionary dict:ls) {
			if (dict.getDictKey().equals(dictKey)) {
				dictVal= dict.getDictValue();
			}
		}
		return dictVal;
	}
	
	public static void seCacheData(Object data) {
		if (data instanceof SysDictionaryList) {
			SysDictionaryList dictls=(SysDictionaryList)data;
			dictList=dictls.getDictList();
			for(SysDictionary dict:dictList) {
				List<SysDictionary> dictTypes=dictMap.get(dict.getDictType());
				if (null==dictTypes) {
					dictTypes=new ArrayList<>();
					dictTypes.add(dict);
				}else {
					dictTypes.add(dict);
				}
				dictMap.put(dict.getDictType(), dictTypes);
			}
		}
	}

	public static String checkAuth(String userCode,String accessCode) {
		Authentication auth=authMap.get(userCode);
		if (null==auth) {
			return "未找到授权信息";
		}else {
			if (!accessCode.equals(auth.getAccessCode())) {
				return "授权信息错误";
			}else {
				Long nowTime=new Date().getTime();
				if (auth.getEffectTime()>=nowTime) {
					auth.setEffectTime(auth.getEffectTime()+1000*60*30);
					authMap.put(userCode, auth);
					return "succ";
				}else {
					return "授权信息超时";
				}
				
			}
		}
	}

	public static String getAuthAccessCode(String userCode) {
		Authentication auth=new Authentication();
		auth.setUserCode(userCode);
		String accessCode=DesEncrypt.getEncString(RandomNum.getRandomPass(64));
		auth.setAccessCode(accessCode);
		//有效期-延后30分
		auth.setEffectTime(new Date().getTime()+1000*60*30);
		authMap.put(userCode, auth);
		return accessCode;
	}
	
}
