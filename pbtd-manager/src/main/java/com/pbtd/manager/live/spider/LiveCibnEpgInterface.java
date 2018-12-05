package com.pbtd.manager.live.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.domain.LiveCibnEpg;
import com.pbtd.manager.live.mapper.LiveChannelMapper;
import com.pbtd.manager.live.mapper.LiveCibnEpgMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class LiveCibnEpgInterface {

	private final static Logger logger = LoggerFactory.getLogger(LiveCibnEpgInterface.class); 
	@Autowired
	private LiveCibnEpgMapper liveCibnEpgMapper;
	@Autowired
	private LiveChannelMapper liveChannelMapper;

	
	
	public static List<LiveCibnEpg> getJsonObjectEpg(int epgdate, LiveChannel livechannel) {
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
		List<LiveCibnEpg> list = new ArrayList<>();
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
				return null;
			}
			JSONObject jsonObj2 = JSONObject.fromObject(jsonObject);
			JSONArray result = jsonObj2.getJSONArray("rows");
			if (result.size() > 0) {
				int i;
				for (i = 0; i < result.size(); i++) {
					LiveCibnEpg LiveCibnListEpg = new LiveCibnEpg();
					JSONObject object = result.getJSONObject(i);
					LiveCibnListEpg.setEpgid((Integer) object.get("startTime"));
					LiveCibnListEpg.setStarttime(Long.valueOf((Integer) object.get("startTime")));
					LiveCibnListEpg.setEndtime(Long.valueOf((Integer) object.get("endTime")));
					LiveCibnListEpg.setDuration(Long.valueOf((Integer) object.get("duration")));
					LiveCibnListEpg.setEpgname((String) object.get("epgName"));
					LiveCibnListEpg.setShowname((String) object.get("showName"));
					LiveCibnListEpg.setWeekday((String) object.get("weekDay"));
					LiveCibnListEpg.setStartdate((String) object.get("startDate"));
					LiveCibnListEpg.setChncode(livechannel.getChncode());
					LiveCibnListEpg.setPlayurl(livechannel.getPlayurl());
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

	
	public void ChncodeMans()  throws InterruptedException {
		logger.info(System.currentTimeMillis()+"--开始从国广接口获取节目单--");
		//每执行 获取7天的
		for (int i=-2;i<=4;i++) {
			getCibnEpg(i);
			logger.info(System.currentTimeMillis()+"结束获取节目单--end+  第"+i+"天+--");
		}
	}
	
	/**
	 *
	 * 相对当前时间的前后天数 返回日期 String
	 * @param fix：
	 * @return
	 */
	public static String getCurrentDate(int fix) {
		Date date = new Date(System.currentTimeMillis() + fix * 24 * 60 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");
		return simpleDateFormat.format(date);
	}
	
	public void getCibnEpg(int i) throws InterruptedException {
		List<LiveChannel> list = liveChannelMapper.selectCibnChannel();
		List<LiveCibnEpg> liveCibnEpglist  = null;

		for (LiveChannel liveChannel : list) {
			liveCibnEpglist = getJsonObjectEpg(i,liveChannel);
			logger.info("集合的长度::::"+liveCibnEpglist.size());
			if(liveCibnEpglist.size()>0){
				String startDate = liveCibnEpglist.get(0).getStartdate();
				Map<String, Object> map = new HashMap<>();
				map.put("startDate", startDate);
				map.put("chnCode", liveCibnEpglist.get(0).getChncode());
				liveCibnEpgMapper.delete(map);

				//遍历插入数据库
				for (LiveCibnEpg dsadasd : liveCibnEpglist) {
					logger.info(dsadasd.getStarttime()+"  时间 : "+dsadasd.getEndtime()+"  频道:"+dsadasd.getChncode()+" || 节目: "+ dsadasd.getEpgname()+" || 日期："+dsadasd.getStartdate() +"    星期："+ dsadasd.getWeekday());
					try {
						LiveCibnEpg liveCibnEpg = liveCibnEpgMapper.selectByPrimaryKey(dsadasd);
						if(liveCibnEpg != null){
							logger.info("数据库存在该条数据:"+liveCibnEpg.getEpgname());
						}else{
							liveCibnEpgMapper.insert(dsadasd);
						}} catch (Exception e) {
							logger.info("插入数据库失败"+e.getMessage());
						}
					//Thread.sleep(200);
				}
			}else{
				logger.info("******************无"+liveChannel.getChncode()+"节目******************");
			}
			logger.info("******************"+liveChannel.getChncode()+"1111******************");
	}
	}

}
