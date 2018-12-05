package com.yh.push.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import com.yh.push.AndroidNotification;
import com.yh.push.PushClient;
import com.yh.push.android.AndroidUnicast;
import com.yh.push.ios.IOSUnicast;

@SuppressWarnings("all")
public class pushUtils {

	private PushClient client = new PushClient();

	
	public String getrequest(HttpServletRequest request) throws Exception {
		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		// 将资料解码
		String reqBody = sb.toString();
		reqBody = URLDecoder.decode(reqBody, "utf-8");
		reqBody = URLDecoder.decode(reqBody, "utf-8");

		return reqBody;
	}

	public boolean sendAndroidUnicast(String token, String title, String reqBody, String type) throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/push.properties");
		AndroidUnicast unicast = new AndroidUnicast(pro.getProperty("Android.appkey").toString(),
				pro.getProperty("Android.appMasterSecret").toString());
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (token == null || "".equals(token)) {
			return false;
		}
		// 设置你的设备令牌
		unicast.setDeviceToken(token);
		unicast.setTicker("Android unicast ticker");
		unicast.setTitle(title);
		unicast.setText(reqBody);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// 如何注册一个测试设备,请参阅开发者文档。
		unicast.setProductionMode();
		// 设置自定义字段
		//unicast.setStartTime(myFmt.toString());//格式: "yyyy-MM-dd HH:mm:ss"。
		unicast.setExtraField("type", type);
		unicast.setExtraField("time", myFmt.toString());

		return client.send(unicast);
	}

	public boolean sendIOSUnicast(String token, String title, String reqBody, String type) throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/push.properties");
		IOSUnicast unicast = new IOSUnicast(pro.getProperty("IOS.appkey").toString(),
				pro.getProperty("IOS.appMasterSecret").toString());
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (token == null || "".equals(token)) {
			return false;
		}
		// 设置你的设备令牌
		unicast.setDeviceToken(token);
		unicast.setAlert(title);
		unicast.setTestMode();// 模式
		// 设置自定义字段
		//unicast.setStartTime(myFmt.toString());//格式: "yyyy-MM-dd HH:mm:ss"。
		unicast.setCustomizedField("text", reqBody);
		unicast.setCustomizedField("type", type);
		unicast.setCustomizedField("time", myFmt.toString());

		return client.send(unicast);
	}

	public boolean sendTvUnicast(String token, String title, String reqBody, String type) throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/push.properties");
		AndroidUnicast unicast = new AndroidUnicast(pro.getProperty("TV.appkey").toString(),
				pro.getProperty("TV.appMasterSecret").toString());
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (token == null || "".equals(token)) {
			return false;
		}
		// 设置你的设备令牌
		unicast.setDeviceToken(token);
		unicast.setTicker("TV unicast ticker");
		unicast.setTitle(title);
		unicast.setText(reqBody);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// 如何注册一个测试设备,请参阅开发者文档。
		unicast.setProductionMode();
		//unicast.setStartTime(myFmt.toString());//格式: "yyyy-MM-dd HH:mm:ss"。
		// 设置自定义字段
		unicast.setExtraField("type", type);
		unicast.setExtraField("time", myFmt.toString());

		return client.send(unicast);
	}

}
