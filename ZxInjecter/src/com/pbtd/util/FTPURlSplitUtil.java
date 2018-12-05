package com.pbtd.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * "ftp://cms1:os10+ZTE@111.63.117.243:21/329/XmlRspPath/20170805/1bb4_rsp_607355.xml"; 
 * ftpurl分割方法  
 * @author asus-pc
 *
 */
public class FTPURlSplitUtil {
	
	static final Logger logger = Logger.getLogger(FTPURlSplitUtil.class);
	
	public static void main(String[] args) {
		String ResultFileURL="ftp://cms1:os10+ZTE@111.63.117.243:21/329/XmlRspPath/20170805/1bb4_rsp_607355.xml";
		FTPURlSplitUtil ff= new FTPURlSplitUtil();
		String getid_jmmdByFTPURL = ff.getid_jmmdByFTPURL(ResultFileURL);
		System.out.println(getid_jmmdByFTPURL);
	}
	
	public static HashMap<String,Object> getFTPMessage(String FTPURL){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		String substring = FTPURL.substring(6);
		System.out.println(substring);
		String[] split = substring.split(":");//[cms1, os10+ZTE@111.63.117.243, 21/329/XmlRspPath/20170805/1bb4_rsp_607355.xml]
		hashMap.put("ftpUserName", split[0]);
		//System.out.println(split[0]);  //cms1 
        String[] split1 = split[1].split("@");//截取os10+ZTE@111.63.117.243
        hashMap.put("ftpPassword", split1[0]);
       // System.out.println(split1[0]);//os10+ZTE
        String[] split2 = split1[1].split(":");
        hashMap.put("ftpHost", split2[0]);
      //  System.out.println(split2[0]);//111.63.117.243
        String[] split3 = split[2].split("/");
        hashMap.put("ftpPort", split3[0]);
       // System.out.println(split3[0]);//21
       // String stripEnd = StringUtils.stripEnd(split[2], "/");
        int lastIndexOf = split[2].lastIndexOf("/");
        String substring2 = split[2].substring(lastIndexOf+1);// 1bb4_rsp_607355.xml
        hashMap.put("fileName", substring2);
       // System.out.println(substring2);
        String substring3 = split[2].substring(0,lastIndexOf+1);// 21/329/XmlRspPath/20170805/
       // System.out.println(substring3);
        int indexOf = substring3.indexOf("/");
        String substring4 = substring3.substring(indexOf);
        hashMap.put("ftpPath", substring4);
       // System.out.println(substring4);// /329/XmlRspPath/20170805/
		
		return hashMap;
	}
	/**
	 * 根据ftpurl获取该具体节目的主键id
	 * @param FTPURL
	 * @return
	 */
	public String getid_jmmdByFTPURL(String FTPURL){
		String substring = null;
		int lastIndexOf = FTPURL.lastIndexOf("rsp_");
		if(lastIndexOf!=-1){
			substring = FTPURL.substring(lastIndexOf+4, FTPURL.length()-4);
		}else{
			substring = "";
			logger.error("根据FTPURL截取id失败 ");
		}
		return substring;
	}

}
