package edu.hfu.auth.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class FormatUtil {
	public static long DAY = 24 * 60 * 60 * 1000L; //一天的毫秒数

	/**
	     * 时间型字符串转换成date型
	  * @param dateStr
	  * @param dateFormat
	  * @return
	  */
    public static Date strToDate(String dateStr, String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);  
		Date date = new Date();  
		try {
			date = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	 }
	/**
	 * 将Date转化为指定格式并返回为字符串类型
	 * @param date
	 * @param dataFormat
	 * @return
	 */
	public static String formatDateToStr(Date date, String dataFormat) {
		SimpleDateFormat todaySDF = new SimpleDateFormat(dataFormat);
		return todaySDF.format(date);
	}
	/**
	 * 将Date转化为指定格式
	 * @param date
	 * @param dataFormat
	 * @return
	 */
	public static Date formatDate(Date date, String dataFormat){
		try {
			SimpleDateFormat todaySDF = new SimpleDateFormat(dataFormat);
			String strDate=todaySDF.format(date);
			return todaySDF.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 为Date设置指定的时分秒 HH:mm:ss
	 * @param date
	 * @param timestr
	 * @return
	 */
	public static Date dateSetHHmmssToDate(Date date, String timestr){
		String datestr=formatDateToStr(date,"yyyy-MM-dd");
		datestr=datestr+" "+timestr;
		return strToDate(datestr,"yyyy-MM-dd HH:mm:ss");
	}
	/**
	   * Date转化为XMLGregorianCalendar
	   * @param date
	   * @return
	   */
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {  
	    GregorianCalendar cal = new GregorianCalendar();  
	    cal.setTime(date);  
	    XMLGregorianCalendar gc = null;  
	    try {  
	    gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);  
	    } catch (Exception e) {  
	         e.printStackTrace();  
	    }  
	    return gc;  
	}
	/**
	 * 获取指定天数后的日期
	 * 
	 * **/
	public static Date getDateAdd(Date d, int days) throws Exception {
		Date r = new Date();
		r.setTime(d.getTime() + days * DAY);
		return r;
	}
	/**根据金额和税率计算贷方值和税金（从SQL语句直接算了，此方法保留备用）
	 * */
	public Map<String,Double> calculateAmount(Double sumAmount,Double tax_rate) throws Exception {
		Map<String,Double> map=new HashMap<String, Double>();
		BigDecimal amountDB = new BigDecimal(sumAmount);
		BigDecimal taxDB = new BigDecimal(tax_rate);
		BigDecimal temp1=new BigDecimal(1.00);
		BigDecimal temp2=new BigDecimal(-1.00);
		System.out.println("------"+((sumAmount/(1+tax_rate))*(1-tax_rate)-sumAmount));
		// 税=(借方合计/(1+税率))*(1-税率）-借方合计
		BigDecimal cal_taxDB=(amountDB.divide(temp1.add(taxDB),2,RoundingMode.HALF_UP).multiply(temp1.subtract(taxDB))).subtract(amountDB);
		//贷方合计 (税+借方合计)*(-1)
		BigDecimal cal_amountDB=(cal_taxDB.add(amountDB)).multiply(temp2);
		map.put("credit_amount", cal_amountDB.doubleValue());
		map.put("tax_amount", cal_taxDB.doubleValue());
		return map;
	}
}
