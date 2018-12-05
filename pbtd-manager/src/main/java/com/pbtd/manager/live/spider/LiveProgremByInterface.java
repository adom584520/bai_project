package com.pbtd.manager.live.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.junit.Test;

import com.pbtd.manager.live.domain.LiveProgram;
import com.pbtd.manager.live.mapper.LiveProgramMapper;
import com.pbtd.manager.live.util.DataFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class LiveProgremByInterface {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private LiveProgramMapper liveProgramMapper;

	static  Map<String,String> map() {
		Map<String,String> map=new HashMap<String,String>();    
//		map.put("CCTV-1", "https://e.starschina.com/api/channels/271641/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-1-HD", "https://e.starschina.com/api/channels/271641/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-2", "https://e.starschina.com/api/channels/271642/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-2-HD", "https://e.starschina.com/api/channels/271642/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-3", "https://e.starschina.com/api/channels/271643/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-4", "https://e.starschina.com/api/channels/271644/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-4-HD", "https://e.starschina.com/api/channels/271644/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-5", "https://e.starschina.com/api/channels/271645/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-7", "https://e.starschina.com/api/channels/271646/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-8", "https://e.starschina.com/api/channels/271660/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-8-HD", "https://e.starschina.com/api/channels/271660/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-9", "https://e.starschina.com/api/channels/271647/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-10", "https://e.starschina.com/api/channels/271724/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-11", "https://e.starschina.com/api/channels/271725/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-12", "https://e.starschina.com/api/channels/271648/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-13", "https://e.starschina.com/api/channels/271726/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-15", "https://e.starschina.com/api/channels/271728/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CCTV-NEWS","https://e.starschina.com/api/channels/268387/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1200");
		map.put("YOUMAN","https://e.starschina.com/api/channels/271768/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
		map.put("JINYING","https://e.starschina.com/api/channels/265486/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1200");
//		map.put("SHENZHEN","https://e.starschina.com/api/channels/271749/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("SHENZHEN-HD","https://e.starschina.com/api/channels/271749/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("ZHEJIANG","https://e.starschina.com/api/channels/271755/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("ZHEJIANG-HD","https://e.starschina.com/api/channels/271755/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("HUNAN","https://e.starschina.com/api/channels/271739/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("HUNAN-HD","https://e.starschina.com/api/channels/271739/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("JIANGSU-HD","https://e.starschina.com/api/channels/271741/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("JIANGSU","https://e.starschina.com/api/channels/271741/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("BEIJING","https://e.starschina.com/api/channels/271651/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("BTV-1","https://e.starschina.com/api/channels/271651/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("DONGFANG","https://e.starschina.com/api/channels/271731/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("DONGFANG-HD","https://e.starschina.com/api/channels/271731/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("GANSU","https://e.starschina.com/api/channels/271732/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("GUANGDONG","https://e.starschina.com/api/channels/271733/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("GUANGDONG-HD","https://e.starschina.com/api/channels/271733/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("GUANGXI","https://e.starschina.com/api/channels/271734/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("GUIZHOU","https://e.starschina.com/api/channels/271735/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("HEBEI","https://e.starschina.com/api/channels/271736/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("HENAN","https://e.starschina.com/api/channels/271737/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("HUBEI","https://e.starschina.com/api/channels/271650/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("HUBEI-HD","https://e.starschina.com/api/channels/271650/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("JILIN","https://e.starschina.com/api/channels/271740/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("JIANGXI","https://e.starschina.com/api/channels/271742/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
		map.put("LIAONING","https://e.starschina.com/api/channels/271743/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
		map.put("LIAONING-HD","https://e.starschina.com/api/channels/271743/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("NEIMENGGU","https://e.starschina.com/api/channels/271745/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("NINGXIA","https://e.starschina.com/api/channels/271746/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("SHANDONG","https://e.starschina.com/api/channels/271649/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("SHANDONG-HD","https://e.starschina.com/api/channels/271649/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("SHANXI","https://e.starschina.com/api/channels/271747/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("SICHUAN","https://e.starschina.com/api/channels/271749/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("TIANJIN","https://e.starschina.com/api/channels/271751/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("TIANJIN-HD","https://e.starschina.com/api/channels/271751/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("XINJIANG","https://e.starschina.com/api/channels/271753/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("XINJIANG","https://e.starschina.com/api/channels/271753/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CHONGQING","https://e.starschina.com/api/channels/271756/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("CHONGQING-HD","https://e.starschina.com/api/channels/271756/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("HEILONGJIANG-HD","https://e.starschina.com/api/channels/271738/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
//		map.put("ANHUI","https://e.starschina.com/api/channels/271729/epgs?appOs=Android&appVer=6.3&appKey=ZjNmMjc2ODViOTgy&page=1&pageSize=1000");
		return map;
	}

	@Test
	public void ChncodeMansS() throws InterruptedException {
		logger.info("直播通过获取接口");
		//定时获取更新数据
		List<LiveProgram> livePackage = null;
		for (String chnCode : map().keySet()) {
			livePackage = importzxjmmd(chnCode);
			//添加时间：
			logger.info("集合的长度::::"+livePackage.size());
			if(livePackage.size()>0){
				//遍历插入数据库
				String realtime = livePackage.get(0).getRealtime().substring(0,10)+"%";
				logger.info("集合的长度::::"+livePackage.size());
				logger.info("::::::::::::::::::::::::::::::::::::");
				logger.info("::::::::::::::::::::::::::::::::::::");
				logger.info(realtime);
				logger.info("::::::::::::::::::::::::::::::::::::");
				logger.info("::::::::::::::::::::::::::::::::::::");
				Map<String, Object> map = new HashMap<>();
				map.put("realtime", realtime);
				map.put("chnCode", chnCode);
				liveProgramMapper.deletebyreal(map);
				for (LiveProgram dsadasd : livePackage) {
					logger.info(dsadasd.getStarttime()+"  时间 : "+dsadasd.getEndtime()+"   节目: "+ dsadasd.getProgramname()+" |||| 日期："+dsadasd.getRealtime() +"    来源"+ dsadasd.getSource());
					try {
						LiveProgram liveprogram = liveProgramMapper.selectByrecord(dsadasd);
						if(liveprogram != null){
							logger.info("数据库存在该条数据:"+liveprogram.getProgramname());
						}else{
							liveProgramMapper.insert(dsadasd);
						}} catch (Exception e) {
							logger.info("插入数据库失败"+e.getMessage());
						}
					Thread.sleep(100);
				}
			}else{
				logger.info("******************无"+chnCode+"节目******************");
			}
			logger.info("******************"+chnCode+"1111******************");
		}
	}
	// 根据接口获取数据
	public List<LiveProgram>  importzxjmmd(String chnCode) {
		String requestUrl = map().get(chnCode);
		logger.info(requestUrl);
		String requestMethod = "GET";
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		List<LiveProgram> liveProgram = new LinkedList<>();
		try {
			URL url = new URL(requestUrl);
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
			//释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject=buffer.toString() ;
			//得到数据 转换json
			JSONObject jsonObj2 = JSONObject.fromObject(jsonObject);
			JSONArray result = jsonObj2.getJSONArray("rows");
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					LiveProgram  live = new LiveProgram();
					JSONObject job = JSONObject.fromObject(result.getJSONObject(i));
					live.setChncode(chnCode);
					live.setSource(0);;
					live.setStarttime((long)(int)job.get("startTime"));
					live.setRealtime(DataFormat.CSTFormat(DataFormat.LongToDare((long)(int)job.get("startTime")).toString()));
					live.setEndtime((long)(int)job.get("endTime"));
					live.setProgramname((String) job.get("epgName"));
					liveProgram.add(live);
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
		return liveProgram;
	}


}
