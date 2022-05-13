package edu.hfu.train.service.sysset;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import edu.hfu.train.util.CacheData;
import edu.hfu.train.util.XmlBuilder;

@Component
public class InitApplicationRunner implements ApplicationRunner{
	@Override
	public void run(ApplicationArguments appArgs) throws Exception {
		//初始化数据字典
		initConstant();
	}

	private void initConstant() throws Exception{
		//读取Resource目录下的XML文件
		Resource resource = new ClassPathResource("constant.xml");
		// 创建xml解析器
		SAXReader saxReader = new SAXReader();
		// 加载文件,读取到document中
		Document document = saxReader.read(new InputStreamReader(resource.getInputStream(), "UTF-8"));
		Element rootEle =document.getRootElement();
		for(Element  childEle:rootEle.elements()) {
			Attribute attr=childEle.attribute("listClass");
			String listClass=attr.getStringValue();
			Class<?> clazz=Class.forName(listClass);
			//XML转为JAVA对象
			Object data =  XmlBuilder.xmlStrToObject(clazz, childEle.asXML());  
			CacheData.seCacheData(data);
		}
	}
	
}
