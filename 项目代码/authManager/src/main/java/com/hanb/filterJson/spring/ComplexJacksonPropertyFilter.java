package com.hanb.filterJson.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.hanb.filterJson.annotation.PowerJsonFilter;
import com.hanb.filterJson.utils.FieldNode;
import com.hanb.filterJson.utils.FilterTools;
import com.hanb.filterJson.utils.ParentTreeNode;
@JsonFilter("JacksonFilter")
public class ComplexJacksonPropertyFilter extends FilterProvider{
	private final Logger LOG = LoggerFactory.getLogger(ComplexJacksonPropertyFilter.class);
	private Map<Class<?>, FieldNode> includesTree = new HashMap<Class<?>, FieldNode>();  
	private Stack<ParentTreeNode> includeStack=null;
	@Override
	public BeanPropertyFilter findFilter(Object filterId) {
		throw new UnsupportedOperationException("Access to deprecated filters not supported");
	}

	public ComplexJacksonPropertyFilter(Map<Class<?>, String[]> includes) {  
		Iterator<Entry<Class<?>, String[]>> entries = includes.entrySet().iterator();
		while(entries.hasNext()){
		    Entry<Class<?>, String[]> entry = entries.next();
		    String[] fields=entry.getValue();
		    FieldNode tmp=FilterTools.setChildFieldNode(fields);
		    includesTree.put(entry.getKey(), tmp);
		}
		includeStack=new Stack<>();
	}  
	
	//构造过滤树
	public ComplexJacksonPropertyFilter(PowerJsonFilter[] filters) {  
		for(PowerJsonFilter filter:filters) {
			String[] fields=filter.include();
			FieldNode tmp=FilterTools.setChildFieldNode(fields);
			includesTree.put(filter.clazz(), tmp);
		}
		includeStack=new Stack<>();
		LOG.debug("includesTree:"+includesTree);
	}
		
	
	@Override
	public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
		
		 return new SimpleBeanPropertyFilter() {
	            @Override
	            public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer)
	            		throws Exception {
	            	LOG.debug("clazz:"+pojo.getClass().getName()+",field name:"+writer.getName());
	            	if (apply(pojo, writer.getName())) {
	            		writer.serializeAsField(pojo, jgen, prov);
	            	} else if (!jgen.canOmitFields()) {
	            		writer.serializeAsOmittedField(pojo, jgen, prov);
	            	}
	            }
	        };
	}

	public boolean apply(Object source, String name) {
		// 对象为空。直接放行
		if (source == null) {
			return true;
		}
		// 获取当前需要序列化的对象的类对象
		Class<?> clazz = source.getClass();

		// 如果是普通的集合，则直接返回
		if (source instanceof Map || source instanceof List || source instanceof Collection || source instanceof Set) {
			return true;
		}
		// 需要序列的对象集合为空 表示 全部需要序列化
		if (this.includesTree.isEmpty()) {
			return true;
		}

		// 需要序列的对象
		// 找到不需要的序列化的类型
		if (includeStack.empty()) {// 堆栈为空，则从includesTree中排查
			FieldNode node = getNode(clazz);
			if (null != node) {
				if (node.isInclude(name)) {// 如果包含当前字段
					FieldNode childNode = node.getChildField(name);
					if (null != childNode) {// 普通字段，直接返回true
						// 当前字段的确是复杂字段，但是的确定该字段的是否为空，如果为空则不进行记录
						dealField(clazz, name, source, childNode);
					}
					return true;
				} else {
					return false;
				}
			} else {// 未定义该类
				return false;
			}
		} else {
			ParentTreeNode pTreeNode = includeStack.peek();
			int leftLen = pTreeNode.getLeftLen() - 1;
			pTreeNode.setLeftLen(leftLen);
			FieldNode childNode = pTreeNode.getChildFieldNode();
			if (leftLen == 0) {
				includeStack.pop();
			}
			boolean rtnFlag = false;
			if (childNode.isInclude(name)) {
				FieldNode nextChildNode = childNode.getChildField(name);
				if (null != nextChildNode) {// 普通字段，直接返回true
					// 复杂字段,记录这个复杂字段
					dealField(clazz, name, source, nextChildNode);
				}
				rtnFlag = true;
			} else {
				rtnFlag = false;
			}
			
			return rtnFlag;
		}

	}

	/**
	 * 对复杂字段进行解析
	 * @param clazz
	 * @param name
	 * @param source
	 * @param childNode
	 */
	private  void dealField(Class<?> clazz,String name,Object source,FieldNode childNode){
		//当前字段的确是复杂字段，但是的确定该字段的是否为空，如果为空则不进行记录
		Object fieldVal =null;
		try {
			Method method=clazz.getMethod(FilterTools.getConvert(name));
			if (method!=null) {
				fieldVal=method.invoke(source);
			}else {
				Field field=clazz.getDeclaredField(name);
				field.setAccessible(true);
				fieldVal = field.get(source);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fieldVal=null;
		}
		if (null!=fieldVal) {
			try {
				Field field=clazz.getDeclaredField(name);
				field.setAccessible(true);
				Class<?> belongCls=field.getType();
				//对于懒加载的 特殊处理下，让其加载一下
				if (fieldVal instanceof PersistentCollection) {//实体关联集合一对多等 
					LOG.debug("clazz:"+clazz.getName()+"对应的 "+name+" 的 value 集合对象");
					int size=0;
					Object item=null;
					//取值（目前 仅支持list和set两个类型）
					if (List.class.isAssignableFrom(belongCls)) {
						List<?> ls=(List<?>)fieldVal;
						size=ls.size();
						if(size>0)
							item=ls.get(0);
						
					}else if (Set.class.isAssignableFrom(belongCls)) {
						Set<?> ls=(Set<?>)fieldVal;
						size=ls.size();
						Iterator<?> it = ls.iterator();
				        if (it.hasNext()) {
				        	item=it.next();
				        }
					}
					if (size>0&&null!=item) {
						//复杂字段,记录这个复杂字段
						ParentTreeNode parent=new ParentTreeNode();
						parent.setChildFieldNode(childNode);
						parent.setLeftLen(size*analyseFieldObj(item.getClass()));
						parent.setParentFieldName(name);
						includeStack.push(parent);
					}
				}else if (fieldVal instanceof HibernateProxy) {//hibernate代理对象  
					LOG.debug("clazz:"+clazz.getName()+"对应的 "+name+" 的 value hibernate 代理对象");
					//复杂字段,记录这个复杂字段
					ParentTreeNode parent=new ParentTreeNode();
					parent.setChildFieldNode(childNode);
					parent.setLeftLen(analyseFieldObj(belongCls));
					parent.setParentFieldName(name);
					includeStack.push(parent);
				}else {//普通对象
					//复杂字段,记录这个复杂字段
					ParentTreeNode parent=new ParentTreeNode();
					parent.setChildFieldNode(childNode);
					parent.setLeftLen(analyseFieldObj(belongCls));
					parent.setParentFieldName(name);
					includeStack.push(parent);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 统计 该字段所属类型 可用于序列化的 字段个数
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	private int analyseFieldObj(Class<?> belongCls) {
		if (null != belongCls) {
			JsonIgnoreProperties jsonIgnoreProperties=belongCls.getAnnotation(JsonIgnoreProperties.class);
			String[] ignoreFields= {};
			if (null!=jsonIgnoreProperties) {
				ignoreFields=jsonIgnoreProperties.value(); //不进行序列化的字段列表
			}
			Field[] fields = belongCls.getDeclaredFields();
			int fieldLen = fields.length;
			for (Field field : fields) {
				field.setAccessible(true);// 私有可访问
				for (String ignoreField:ignoreFields) {
					if (field.getName().equals(ignoreField)) {
						fieldLen--;
						continue;
					}
				}
				
				JsonIgnore jsonField = field.getAnnotation(JsonIgnore.class);
				if (null != jsonField && jsonField.value() == true) {// 该字段不序列化
					fieldLen--;
				} else {
					// 判断 该字段对应的get方法 是否有忽略的注解
					try {
						Method mth = belongCls.getMethod(FilterTools.getConvert(field.getName()));
						JsonIgnore jsonMethod = mth.getAnnotation(JsonIgnore.class);
						if (null != jsonMethod && jsonMethod.value() == true) {// 该字段不序列化
							fieldLen--;
						}

					} catch (NoSuchMethodException e) {
//							不处理
//							e.printStackTrace();
					} catch (SecurityException e) {
//							不处理
//							e.printStackTrace();
					}

				}
			}
			return fieldLen;
		} else {
			return 0;
		}
	}
	/**
	 * 查找是否存在
	 * @param clazz
	 * @return
	 */
	private FieldNode getNode(Class<?> clazz) {
		if (null!=includesTree) {
			for(Map.Entry<Class<?>, FieldNode> entry : includesTree.entrySet()){
				Class<?> mapKey = entry.getKey();
				if (mapKey.isAssignableFrom(clazz)) {
					return entry.getValue();
				}
			}
			return null;
		}else {
			return null;
		}
	}

}
