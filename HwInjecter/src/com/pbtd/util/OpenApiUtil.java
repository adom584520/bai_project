package com.pbtd.util;


import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.pbtd.helpBean.AccessTokenBean;
import com.pbtd.helpBean.MovieUrlAddressBean;
import com.pbtd.helpBean.MovieUrlBean;
import com.pbtd.helpBean.RequestParam;

import net.sf.json.JSONObject;

public class OpenApiUtil {
	
	private static final Logger logger=Logger.getLogger(OpenApiUtil.class);
	
	private final String userId="guest";
	private final String password="123456";
	private final String clientId="ggtv";
	private final String deviceType="Mobile";
	private final String deviceVersion="v1.0";
	private final String deviceId="webServer";
	private final String reserved="";
	
	private final String ipUrl="http://111.63.145.4:8082/EDS/jsp/AuthenticationURL?Action=Login&UserID="+userId+"&return_type=1&client_id="+clientId;
	private final String encryTokenUrl="/EPG/oauth/v2/authorize?response_type=EncryToken&client_id="+clientId+"&userid="+userId;
	private final String accessTokenUrlPrefix="/EPG/oauth/v2/token?grant_type=EncryToken&client_id="+clientId+"&authinfo="; 
	private final String accessTokenUrlSuffix="&UserID="+userId+"&DeviceType="+deviceType+"&DeviceVersion="+deviceVersion;
	private final String movieUrl="/EPG/interEpg/user/default/authorize";
	
	private Calendar calLast=Calendar.getInstance();
	private PropertiesUtil properUtil=new PropertiesUtil();
	private int expires=0;
	private boolean isValid=false;
	
	private String serverIP="";
	private String accessToken="";
	
	private OpenApiUtil(){};
	private static final OpenApiUtil oaUtil=new OpenApiUtil();
	public static OpenApiUtil getInstance(){
		return oaUtil;
	}
	
	
	//根据节目id、programId来获取节目
	public String getUrlFromOpenApi(String cid){
		String playUrl="";
		judgeTokenValid(5); //误差5秒
		if(!isValid){
			Map<String,Object> tokenMap=this.getToken();
			serverIP=tokenMap.get("serverIP").toString();
			accessToken=tokenMap.get("accessToken").toString();
		}
		playUrl=this.getMovieUrl(serverIP, accessToken, cid);
		return playUrl;
	}
	
	
	//一套流程获取token，并返回关键几个值
	public Map<String,Object> getToken(){
		Map<String,Object> tokenMap=new HashMap<String,Object>();
		String serverIP=this.getServerIP();
		String encryToken=this.getEncryToken(serverIP);
		Map<String,Object> accessMap=this.getAccessToken(serverIP, encryToken);
		calLast=Calendar.getInstance();
		expires=Integer.valueOf(accessMap.get("expiresTime").toString());

		//返回值
		tokenMap.put("serverIP", serverIP);
		tokenMap.put("encryToken", encryToken);
		tokenMap.put("accessToken", accessMap.get("accessToken"));
		return tokenMap;
	}

	
	public void judgeTokenValid(int deviation){
		Calendar cal=Calendar.getInstance();
		if(calLast.getTimeInMillis()+expires-deviation>=cal.getTimeInMillis()){
			isValid=true;
		}else{
			isValid=false;
		}
	}
	
	
	
	public String getServerIP(){
		String serverIP="";
		String strResult=HttpClientUtil.httpGet(ipUrl);
		System.out.println(strResult);
		int inxBegin=0;
		int inxEnd=0;
		inxBegin=strResult.indexOf(":")+2;//冒号往前2个
		inxEnd=strResult.indexOf("/EPG");
		serverIP=strResult.substring(inxBegin, inxEnd);
		logger.info("ServerIP："+serverIP);
		return serverIP;
	}
	
	public String getEncryToken(String serverIP){
		String encryToken="";
		String url=serverIP+encryTokenUrl;
		String strResult=HttpClientUtil.httpGet(url);
		System.out.println(strResult);
		int inxBegin=0;
		int inxEnd=0;
		inxBegin=strResult.indexOf(":")+2;
		inxEnd=strResult.indexOf("}")-1;
		encryToken=strResult.substring(inxBegin, inxEnd);
		logger.info("encryToken:"+encryToken);
		return encryToken;
	}
	
	public String getAuthInfo(String encryToken){
		StringBuilder authInfo=new StringBuilder("");
		authInfo.append(UUID.randomUUID());
		authInfo.append("$");
		authInfo.append(encryToken); // EncryToken
		authInfo.append("$");
		authInfo.append(userId); //UserID
		authInfo.append("$");
		authInfo.append(deviceId);//DeviceID
		authInfo.append("$");
		authInfo.append(this.getLocalIP());//IP
		authInfo.append("$");
		authInfo.append(this.getLocalMac());
		authInfo.append("$");
		authInfo.append(reserved);
		authInfo.append("$");
		authInfo.append("OTT");
		String strAuth=authInfo.toString();
		String threeEDSAuthoInfo= ThreeDESUtil.encryptModeInhex(strAuth.getBytes(), password);
		return threeEDSAuthoInfo;
	}
	
	
	public String getLocalIP(){
		String address="111.63.144.250"; //默认当前web地址
	/*	try {
			address = InetAddress.getLocalHost().getHostAddress();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		logger.info("localIP:"+address);*/
		address=properUtil.getValue("OpenApiIP");
		return address;
	}
	
	private String getLocalMac() {
		logger.info("进入getLocalMac方法");
		String mac="";
		/*InetAddress ia=null;
		try {
			ia = InetAddress.getLocalHost();
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		logger.info("ia:"+ia);
		//获取网卡，获取地址
		byte[] mac=null;
		try {
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
		logger.info("mac:"+mac);
		System.out.println("mac数组长度："+mac.length);
		StringBuffer sb = new StringBuffer(""); //250对应mac地址  默认
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			System.out.println("每8位:"+str);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		if("".equals(sb.toString())){
			sb=new StringBuffer("C8:1F:BE:E0:80:D1");
		}
		logger.info("LocalMac:"+sb.toString().toUpperCase());
		mac=sb.toString();
		*/
		mac=properUtil.getValue("OpenApiMac");
		return mac;
	}
	
	/*
	 * 获取访问令牌
	 * @return map 
	 * accessToken 令牌，expiresTime 有效时间
	 */
	public Map<String,Object> getAccessToken(String serverIP,String encryToken){
		Map<String,Object> map=new HashMap<String,Object>();
		String accessToken="";
		int expiresTime=0;
		String url=serverIP+accessTokenUrlPrefix+this.getAuthInfo(encryToken)+accessTokenUrlSuffix;
		String strResult=HttpClientUtil.httpGet(url);
		JSONObject json=JSONObject.fromObject(strResult);
		AccessTokenBean bean=(AccessTokenBean)json.toBean(json, AccessTokenBean.class);
		System.out.println(strResult);
		accessToken=bean.getAccess_token();
		expiresTime=Integer.valueOf(bean.getExpires_in());
		map.put("accessToken", accessToken);
		map.put("expiresTime",expiresTime);
		logger.info("accessToken:"+accessToken);
		logger.info("expiresTime:"+expiresTime);
		return map;
	}
	
	
	public String getMovieUrl(String serverIP,String accessToken,String cid){
		String playUrl="";
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> headerMap=new HashMap<String,Object>();
		headerMap.put("authorization",accessToken ); //accessToken传参
		String url=serverIP+this.movieUrl;
		RequestParam requestParam=new RequestParam();
		requestParam.setBusinessType("1"); //1:VOD 
		requestParam.setCid(cid); //影片传过去的ID  
		//3216ec3a5a184346a123804134033b97
		//5a71bd95d51646a0bf922586390b2c7e
		requestParam.setTid("-1");  //影片填写-1
		requestParam.setSupercid("-2");  // （影片-2）
		requestParam.setPlayType("1"); // 1:VOD播放
		requestParam.setContentType("0"); // 0:视频VOD
		requestParam.setIdflag("1"); //1: 外部牌照商CODE
		JSONObject jsonParam=JSONObject.fromObject(requestParam);
		
		String strResult= HttpClientUtil.httpPost(url, jsonParam,headerMap,false);
		JSONObject json=JSONObject.fromObject(strResult);
		Map<String,Object> classMap=new HashMap<String,Object>();
		classMap.put("urls",MovieUrlAddressBean.class );
		logger.info("strResult:"+strResult);
		MovieUrlBean movieUrlBean=(MovieUrlBean)JSONObject.toBean(json, MovieUrlBean.class,classMap);
		if(movieUrlBean!=null){
			String returnCode=movieUrlBean.getReturncode();
			List<MovieUrlAddressBean> urls=movieUrlBean.getUrls();
			if(!"0".equals(returnCode)){
				logger.info("ReturnCode:"+movieUrlBean.getReturncode()+","+movieUrlBean.getDescription());
			}else{
				if(urls!=null){
					playUrl=urls.get(0).getPlayurl();
				}
			}
		}
		logger.info("playUrl:"+playUrl);
		return playUrl;
	}
	
	
	public static void main(String[] args){
		OpenApiUtil oaUtil=new OpenApiUtil();
		String url=oaUtil.getUrlFromOpenApi("");
	}
}
