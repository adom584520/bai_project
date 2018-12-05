package com.pbtd.playuser.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pbtd.playuser.domain.LiveCibnNextEpg;

import net.sf.json.JSONObject;

public class JsonServlet {
	private static final Logger logger = LoggerFactory.getLogger(JsonServlet.class);

	/**
	 * 当前节目和下一个节目
	 * 
	 * @return
	 */
	public static Map<Integer, LiveCibnNextEpg> getJsonObjectNowEpg() {
		String urla = "https://e.starschina.com/api/currentepgs?appOs=Android&appVer=1.1&appKey=ZjQxMzQ2ZmYwZTU5";
		String requestMethod = "GET";
		String jsonObject = null;	
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		Map<String, Object> maps = new HashMap<>();
		Map<Integer, LiveCibnNextEpg> map = new HashMap<>();
		logger.info("访问国广ip：" + urla.toString());
		logger.info("访问国广ip开始：" + System.currentTimeMillis());
		try {
			URL url = new URL(urla);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			logger.info("访问国广ip结束：" + System.currentTimeMillis());
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = buffer.toString();
			// 得到数据 转换json
			if (jsonObject.equals("[]")) {
				// updatetime=0;
				return null;
			}
			JSONObject jsonObj2 = JSONObject.fromObject(jsonObject);
			JSONObject result = (JSONObject) jsonObj2.get("rows");
			maps = (Map<String, Object>) JSON.parse(result.toString());
			for (String key : maps.keySet()) {
				LiveCibnNextEpg epg = new LiveCibnNextEpg();
				JSONObject jsonObj3 = (JSONObject) result.get(key);
				JSONObject jsonObj4 = (JSONObject) jsonObj3.get("current");
				JSONObject jsonObj5 = (JSONObject) jsonObj3.get("next");
				epg.setVideoId(Integer.valueOf(key));
				epg.setStartTime(Long.valueOf((Integer) jsonObj4.get("startTime")));
				epg.setEndTime(Long.valueOf((Integer) jsonObj4.get("endTime")));
				epg.setEpgName((String) jsonObj4.get("epgName"));
				epg.setTypeName((String) jsonObj4.get("typeName"));
				epg.setNextStartTime(Long.valueOf((Integer) jsonObj5.get("startTime")));
				epg.setNextEndTime(Long.valueOf((Integer) jsonObj5.get("endTime")));
				epg.setNextEpgName((String) jsonObj5.get("epgName"));
				map.put(Integer.valueOf(key), epg);
			}
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

}
