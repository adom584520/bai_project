package com.pbtd.manager.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pbtd.manager.android.AndroidUnicast;
import com.pbtd.manager.conf.PushBeanConfig;
import com.pbtd.manager.demo.AndroidNotification;
import com.pbtd.manager.demo.PushClient;
import com.pbtd.manager.ios.IOSUnicast;

import net.sf.json.JSONObject;

@SuppressWarnings("all")
@Component
public class pushUtils {

	private PushClient client = new PushClient();
	@Autowired
	private PushBeanConfig pushBeanConfig;
	

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
		AndroidUnicast unicast = new AndroidUnicast(pushBeanConfig.AndroidAppkey, pushBeanConfig.AndroidAppMasterSecre);
		long curren = System.currentTimeMillis();
		curren += 1 * 60 * 1000;
		Date da = new Date(curren);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (token == null || "".equals(token)) {
			return false;
		}
		// 设置你的设备令牌
		unicast.setDeviceToken(token);
		unicast.setTicker("Android unicast ticker");
		unicast.setTitle(title);
		unicast.setText(title);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
		// 如何注册一个测试设备,请参阅开发者文档。
		unicast.setProductionMode();
		// 设置自定义字段
		unicast.setExpireTime(myFmt.format(da).toString());// 格式: "yyyy-MM-dd
															// HH:mm:ss"。
		/*
		 * unicast.setExtraField("type", type); unicast.setExtraField("time",
		 * myFmt.toString());
		 */
		JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("text", reqBody);
		json.put("message", title);
		if ("3".equals(type)) {
			json.put("userId", reqBody);
		}
		json.put("time", myFmt.format(da).toString());
		unicast.setCustomField(json.toString());

		return client.send(unicast);
	}

	public boolean sendIOSUnicast(String token, String title, String reqBody, String type) throws Exception {
		IOSUnicast unicast = new IOSUnicast(pushBeanConfig.IOSAppkey, pushBeanConfig.IOSAppMasterSecret);
		long curren = System.currentTimeMillis();
		curren += 1 * 60 * 1000;
		Date da = new Date(curren);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (token == null || "".equals(token)) {
			return false;
		}
		// 设置你的设备令牌
		unicast.setDeviceToken(token);
		unicast.setAlert(title);
		// 开发者模式或生产模式
		if ("1".equals(pushBeanConfig.IOS_Mode)) {
			// 开发者模式
			unicast.setTestMode();
		} else {
			// 生产模式
			unicast.setProductionMode();

		}
		// 设置自定义字段
		unicast.setExpireTime(myFmt.format(da).toString());// 格式: "yyyy-MM-dd
															// HH:mm:ss"。
		unicast.setCustomizedField("type", type);
		unicast.setCustomizedField("text", reqBody);
		unicast.setCustomizedField("message", title);
		if ("3".equals(type)) {
			unicast.setCustomizedField("userId", reqBody);
		}
		unicast.setCustomizedField("time", myFmt.format(da).toString());

		return client.send(unicast);
	}

	public boolean sendTvUnicast(String token, String title, String reqBody, String type) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(pushBeanConfig.TVAppkey, pushBeanConfig.TVAppMasterSecret);
		long curren = System.currentTimeMillis();
		curren += 1 * 60 * 1000;
		Date da = new Date(curren);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (token == null || "".equals(token)) {
			return false;
		}
		// 设置你的设备令牌
		unicast.setDeviceToken(token);
		unicast.setTicker("TV unicast ticker");
		unicast.setTitle(title);
		unicast.setText(title);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
		// 如何注册一个测试设备,请参阅开发者文档。
		unicast.setProductionMode();
		unicast.setExpireTime(myFmt.format(da).toString());// 格式: "yyyy-MM-dd
															// HH:mm:ss"。
		// 设置自定义字段
		JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("text", reqBody);
		json.put("message", title);
		if ("3".equals(type)) {
			json.put("userId", reqBody);
		}
		json.put("time", myFmt.format(da).toString());
		unicast.setCustomField(json.toString());
		/*
		 * unicast.setExtraField("type", type); unicast.setExtraField("time",
		 * myFmt.toString());
		 */

		return client.send(unicast);
	}

}
