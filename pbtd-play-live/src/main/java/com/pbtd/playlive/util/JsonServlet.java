package com.pbtd.playlive.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pbtd.playlive.domain.LiveChannel;
import com.pbtd.playlive.domain.LiveCibnListEpg;
import com.pbtd.playlive.domain.LiveCibnNextEpg;
import com.pbtd.playlive.service.impl.RedisServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class JsonServlet {
	private static final Logger logger = LoggerFactory.getLogger(JsonServlet.class);
	@Autowired
	private  RedisServiceImpl redisServiceImpl;


	public String getJsonObjectChannel(String urla) {
		String requestMethod = "GET";
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
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
			List<Object> jsonObj3 = (List<Object>) jsonObj2.get("datas");
			JSONObject jsonObj4 = (JSONObject) jsonObj3.get(0);
			JSONArray result = jsonObj4.getJSONArray("data");
			if (result.size() > 0) {
				for (Object object : result) {
					System.out.println(object.toString());
				}
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
		return null;
	}

	/**
	 *
	 * 相对当前时间的前后天数 返回日期 String
	 * 
	 * @param fix：
	 * @return
	 */
	public static String getCurrentDate(int fix) {
		Date date = new Date(System.currentTimeMillis() + fix * 24 * 60 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");
		return simpleDateFormat.format(date);
	}

	/**
	 *
	 * 相对当前时间的前后天数 返回日期 String
	 * 
	 * @param fix：
	 * @return
	 */
	public static long getStampDate(int fix) {
		Date date = new Date();// 取时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, fix);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(yesterday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		return time / 1000;
	}

	public static List<LiveCibnListEpg> getJsonObjectEpg(int epgdate, LiveChannel livechannel) {
		StringBuffer urll = new StringBuffer();
		urll.append("https://e.starschina.com/api/channels/");
		urll.append(livechannel.getVideoid());
		urll.append("/epgs?appOs=Android&appVer=1.1&appKey=ZjQxMzQ2ZmYwZTU5&page=1&pageSize=120&startDate=");
		urll.append(getCurrentDate(epgdate));
		logger.info("访问国广ip：" + urll.toString());
		logger.info("访问国广ip开始：" + System.currentTimeMillis());
		String requestMethod = "GET";
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		List<LiveCibnListEpg> list = new ArrayList<>();
		try {
			URL url = new URL(urll.toString());
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
			JSONArray result = jsonObj2.getJSONArray("rows");
			if (result.size() > 0) {

				int i;
				for (i = 0; i < result.size(); i++) {
					LiveCibnListEpg LiveCibnListEpg = new LiveCibnListEpg();
					JSONObject object = result.getJSONObject(i);
					LiveCibnListEpg.setStartTime(Long.valueOf((Integer) object.get("startTime")));
					LiveCibnListEpg.setEndTime(Long.valueOf((Integer) object.get("endTime")));
					LiveCibnListEpg.setDuration(Long.valueOf((Integer) object.get("duration")));
					LiveCibnListEpg.setEpgName((String) object.get("epgName"));
					LiveCibnListEpg.setShowName((String) object.get("showName"));
					LiveCibnListEpg.setWeekDay((String) object.get("weekDay"));
					LiveCibnListEpg.setStartDate((String) object.get("startDate"));
					LiveCibnListEpg.setChnCode(livechannel.getChncode());
					LiveCibnListEpg.setPlayUrl(livechannel.getPlayurl());
					list.add(LiveCibnListEpg);
				}
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
		return list;
	}

	/**
	 * 当前节目和下一个节目
	 * 
	 * @return
	 */
	public LiveCibnNextEpg getCibnEpg(String videoid){
		String object;
		try {
			object = redisServiceImpl.get("CIBNEPG"+videoid);
		} catch (Exception e) {
			logger.info("连接缓存出错：");
			e.printStackTrace();
			return null;	
		}
		if(object != null){
			LiveCibnNextEpg liveCibnEpg = JSON.parseObject(object, LiveCibnNextEpg.class);
			return liveCibnEpg;	
		}else{
			return null;	
		}
	}

	public boolean getJsonObjectNowEpg() {
		String urla = "https://e.starschina.com/api/currentepgs?appOs=Android&appVer=1.1&appKey=ZjQxMzQ2ZmYwZTU5";
		String requestMethod = "GET";
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		Map<String, Object> maps = new HashMap<>();
		Map<String, String> map = new HashMap<String, String>();
	//	logger.info("访问国广ip：" + urla.toString());
	//	logger.info("访问国广ip开始：" + System.currentTimeMillis());
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
		//	logger.info("访问国广ip结束：" + System.currentTimeMillis());
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
				return false;
			}
			JSONObject jsonObj2 = JSONObject.fromObject(jsonObject);
			JSONObject result = (JSONObject) jsonObj2.get("rows");
			maps = (Map<String, Object>) JSON.parse(result.toString());
			//	System.out.println("cahgndu+++++++:"+map.size());
			try {
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
					redisServiceImpl.set("CIBNEPG"+key,epg.toString());
					map.put(key, epg.toString());
				}
			} catch (Exception e) {
				logger.info("连接缓存出错");
				return false;
			}
		} catch (ConnectException ce) {
			ce.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
