package com.pbtd.playuser.util.sendPost;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.service.IRedisService;

import net.sf.json.JSONObject;

//对接口进行测试  
@Service
public class TestMain {
	
	private static final Logger logger = LoggerFactory.getLogger(TestMain.class); 

	private static String url = "https://218.207.75.17:8060/";  
	private static String charset = "UTF-8";  
	@Autowired
	private IRedisService redisService;

	public  String  getToken(){
		String httpOrgCreateTest = url+"OAuth/restOauth2Server/access_token";  
		Map<String,Object> createMap = new HashMap<String,Object>();  
		createMap.put("grant_type","client_credentials");  
		createMap.put("client_id","20170830000440088");  
		createMap.put("client_secret","d734g382b78c8e49opc162efb3e6b621");  
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest,createMap,charset);  
		System.out.println("返回值："+httpOrgCreateTestRtn);
		JSONObject job2 = JSONObject.fromObject(httpOrgCreateTestRtn);
		String token = (String) job2.get("access_token");
		System.out.println("获取token："+token);
		redisService.set("access_token",token);
		return token;
	}
	
	public  void sendMassage(long mobile,String code){
		logger.info("开始发送验证码：1");
		String access_token = redisService.get("access_token");
		logger.info("开始发送验证码：2");
		if(access_token == null || "".equals(access_token) ){
			access_token = getToken();
		}
		logger.info("获取移动token："+ access_token);
		StringBuffer url = new StringBuffer();
		url.append("https://218.207.75.17:8060/OpenEbus/httpService/UserService?access_token=");
		url.append("6bae5b3dc27784d4d4565d79dae4a227a30a8818");
		url.append(access_token);
		JSONObject obj = new JSONObject();  
		obj.put("TELNUM", mobile);
		obj.put("CONTENT","【河北移动大视频】您的短信验证码："+code+"，5分钟内有效。欢迎使用河北移动大视频，如非本人操作，请忽略此短信"); 
		obj.put("CHANNELID","bsacHQKJ"); 
		obj.put("PUBLICPARAM", "我是一个兵");
		String str=obj.toString();
		System.out.println("2："+System.currentTimeMillis());
		String httpOrgCreateTestRtn = HttpClientUtil.doPostjson(url.toString(), str, "UTF-8");
		System.out.println("5："+System.currentTimeMillis());
		System.out.println("返回值:"+httpOrgCreateTestRtn); 
		JSONObject job2 = JSONObject.fromObject(httpOrgCreateTestRtn);
		String requst  =  job2.getString("res_code");
		System.out.println(requst);
	}
	public static void main(String[] args){
		TestMain main = new TestMain();  
		//main.getToken();
		System.out.println("1："+System.currentTimeMillis());
		main.sendMassage(18731604827l,"111111");
	}  
}  