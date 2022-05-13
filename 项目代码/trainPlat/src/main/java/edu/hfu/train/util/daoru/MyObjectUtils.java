package edu.hfu.train.util.daoru;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class MyObjectUtils {
	private static Logger log = Logger.getLogger(MyObjectUtils.class);

	private MyObjectUtils() {

	}

	/**
	 * 通过属性名称获取属性值
	 *
	 * @param object
	 *            //@param fieldName
	 * @return
	 */
	public static Object getFieldValueByName(Object object, Field field) {
		try {
			String fieldName = field.getName();
			StringBuilder sb = new StringBuilder();
			sb.append("get");
			sb.append(fieldName.substring(0, 1).toUpperCase());
			sb.append(fieldName.substring(1));

			Method method = object.getClass().getMethod(sb.toString());
			return method.invoke(object);
		} catch (Exception e) {
			log.info("获取字段值异常" + e);
			return MessageUtils.ERROR;
		}
	}

	/**
	 * 通过属性名称给对象赋值
	 *
	 * @param object
	 *            //@param fieldName
	 * @param value
	 * @return
	 */
	public static Object setFieldValueByName(Object object, Field field, Object value) {
		try {
			String fieldName = field.getName();// 类名
			System.out.println("fieldName: " + fieldName);
			Class<?>[] paramTypes = new Class[1];
			paramTypes[0] = field.getType();// 类类型
			StringBuilder sb = new StringBuilder();
			sb.append("set");
			sb.append(fieldName.substring(0, 1).toUpperCase());
			sb.append(fieldName.substring(1));
			Method method = object.getClass().getMethod(sb.toString(), paramTypes);
			System.out.println("object: " + object);
			System.out.println("value：" + value + " " + value.getClass());
			//当从表格中读取的数据类型与实体bean中字段的类型不一致时进行类别转换（暂时只转换字符串类型）
			if(!paramTypes[0].equals(value.getClass()) && paramTypes[0].equals(String.class))
				return method.invoke(object, value.toString());
			else
				return method.invoke(object, value);
		} catch (Exception e) {
			log.error("赋值异常" + e);
			return MessageUtils.ERROR;
		}
	}

}
