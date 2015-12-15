package com.geniusgithub.bcoinmonitor.util;

public class StringUtil {

	public static boolean compareDoubleStr(String str1, String str2){
		double val1 = Double.valueOf(str1);
		double val2 = Double.valueOf(str2);
		
		return val1 > val2 ? true : false;
	}
}
