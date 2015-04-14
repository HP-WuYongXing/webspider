package com.oliver.spiders.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
	public static String removeBlank(String str){
		String s = "";
		if(str!=null){
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher matcher = p.matcher(str);
			s = matcher.replaceAll("");
		}
		return s;
	}
	
	public static String getUrlWithoutHtmFileTail(String str){
		int index = str.lastIndexOf("/");
		return str.substring(0,index+1);
	}
}
