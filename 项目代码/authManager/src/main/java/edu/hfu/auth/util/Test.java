package edu.hfu.auth.util;

import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		int[] s1= {1,2,4,5,5,8,10,10,10, 9,10,10};
		int[] s2= {1,3,4,5,5,8, 8, 9,10,10, 9, 9};
		int[] s3= {1,4,6,10,8,10,15,14,15,14,14,15};
		double T=5;
		Date tm=FormatUtil.strToDate("2020-11-13 10:05:00", "yyyy-MM-dd HH:mm:ss");
		long t=tm.getTime();
		Calendar cl=Calendar.getInstance();
		
		for (int i=1;i<s1.length;i++) {
			int ts1=(s1[i]-s1[i-1])*(s1[i]-s1[i-1]);
			int ts2=(s2[i]-s2[i-1])*(s2[i]-s2[i-1]);
			int ts3=(s3[i]-s3[i-1])*(s3[i]-s3[i-1]);
			T=(1/((ts1+ts2+ts3)*0.5))*5*1000*60;
			t=(long) (t+T);
			cl.setTimeInMillis(t);
			System.out.println(FormatUtil.formatDateToStr(cl.getTime(), "yyyy-MM-dd HH:mm:ss"));
		}
	
	}

}
