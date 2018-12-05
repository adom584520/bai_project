package com.zckj.tool.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BASE64Tools {
	
	public static String getDASE64Decoder(String str){
		String s = null;
		try {
			s = new String(new sun.misc.BASE64Decoder().decodeBuffer(str));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	
	public static String getDASE64Encoder(String str){
		String s = null;
		try{
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(new sun.misc.BASE64Encoder().encode(str.getBytes("UTF-8")));
			s = m.replaceAll("");
		
			p = null;
			m = null;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return s;
	}
}
