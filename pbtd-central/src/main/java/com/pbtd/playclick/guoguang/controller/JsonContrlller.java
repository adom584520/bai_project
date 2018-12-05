package com.pbtd.playclick.guoguang.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.pbtd.playclick.guoguang.domain.GgAlbums;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfo;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfovideo;
import com.pbtd.playclick.guoguang.domain.GgChannel;
import com.pbtd.playclick.guoguang.domain.GgLabel;
import com.pbtd.playclick.guoguang.service.IGgAlbumService;
import com.pbtd.playclick.integrate.controller.centralController;
import com.pbtd.playclick.yinhe.service.impl.QuartzServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller("guoguang.JsonContrlller")
@RequestMapping("/guoguang/jsonalbum")
public class JsonContrlller {
	public static Logger log = Logger.getLogger(JsonContrlller.class);
	@Autowired
	private IGgAlbumService ggalbumService;
	
	@Autowired
	private centralController centralurl;
	private int updatetime = 0;

	@RequestMapping(value = {"/show"})
	public void show() {
		System.out.println("开始获取");
		// 定时获取更新数据
		importzxjmmd();
	}

	// 根据接口获取数据
	public String importzxjmmd() {
		Long sincecur = new Date().getTime()/1000;//当前时间
		Map<String, Object> maptime=ggalbumService.getupdate();
		String updatime="0";
		if(maptime!=null){
			updatime=maptime.get("updatetime")==null?"0":maptime.get("updatetime").toString();
		}
		int updatimesince=Integer.parseInt(updatime);//上次自动更新时间
		int since =updatimesince;//增量更新时间
		String requestUrl = centralurl.gg_album;
		if (updatimesince==since) {
			requestUrl += "&since=" + since;
		}else{
			requestUrl += "&since=" + since;
		}
		String requestMethod = "GET";
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
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
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject=buffer.toString() ;
			//得到数据 转换json
			if(jsonObject.equals("[]")){
				return null;
			}
			JSONObject jsonObj2 = JSONObject.fromObject("{'msg':" + jsonObject + "}");
			JSONArray result = jsonObj2.getJSONArray("msg");
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					JSONObject job = result.getJSONObject(i);
					GgAlbums albums = JSON.parseObject(job.toString(), GgAlbums.class);
					int listalbum=ggalbumService.countAlbums(albums);
					if (listalbum<1) {
						ggalbumService.insertAlbums(albums);
					}
					if(since<albums.getUpdatetime()){
						since=albums.getUpdatetime();
						updatetime=since;
					}
					ggalbumService.updatetime(since+"");//更新时间
					importzxjmmdmx(albums.getCode(), "admin");
				}
				try {
				//更新dramacode
				ggalbumService.updateDramacode();
				//国广剧集排序增量sequence
				ggalbumService.updateSequence();
				} catch (Exception e) {
					log.error("国广剧集处理异常: " + e);
				}
				if(sincecur>updatetime){
					//importzxjmmd();
					log.info("当前时间"+sincecur);
					log.info("执行结束，最后时间"+updatetime);
					return null;
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

	public String importzxjmmdmx(String code, String userId) {
		String requestUrl = centralurl.gg_albuminfo+ code;
		String requestMethod = "GET";
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
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
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = buffer.toString();
			// 得到数据 转换json
			GgAlbumsinfo albumsinfo = JSON.parseObject(jsonObject.toString(), GgAlbumsinfo.class);
			int albumsinfocount=ggalbumService.countAlbuminfo(albumsinfo);
			if(albumsinfocount<1){
				String lableids="";//标签id
				String chnids="";//频道id
				//更新频道
				String chnName=albumsinfo.getProgramType();
				GgChannel channel=new GgChannel();
				channel.setChnName(chnName);
				Map<String, Object> chnmap=ggalbumService.getGgChannel(channel);
				if(chnmap==null){
					ggalbumService.insertGgChannel(channel);
					chnmap=ggalbumService.getGgChannel(channel);
				} 
				chnids=chnmap.get("chnid").toString();
				albumsinfo.setProgramTypeids(chnids);//频道id
				//标签
				String bq=albumsinfo.getProgramType2();
				if (bq != "") {// 更新标签
					String[] bqs = null;
					if (bq.indexOf(",") > 0) {
						bqs = bq.split(",");
					} else { 
						bqs = bq.split("\\|");
					}
					for (String name : bqs) {
						GgLabel label = new GgLabel();
						label.setBz(chnName);
						label.setTagName(name);
						Map<String, Object>  lablemap = ggalbumService.getLabel(label);
						if(lablemap==null){
							ggalbumService.insertLabel(label);
							lablemap = ggalbumService.getLabel(label);
						}
						lableids+=lablemap.get("tagId").toString()+","; 
					}
					albumsinfo.setProgramType2ids(lableids);
				}
					ggalbumService.insertAlbuminfo(albumsinfo);
				}else{
					ggalbumService.updateAlbuminfo(albumsinfo);
				}
				JSONObject playjson = JSONObject.fromObject(jsonObject);
				//播放记录
				if(albumsinfo.getPlayURL()!=null &&
						albumsinfo.getPlayURL()!="null" &&
						albumsinfo.getPlayURL()!=""){
					JSONArray result = playjson.getJSONArray("playURL");
					for (int i = 0; i < result.size(); i++) {
						JSONObject job = result.getJSONObject(i);
						GgAlbumsinfovideo albumsinfovideo = JSON.parseObject(job.toString(), GgAlbumsinfovideo.class);
						albumsinfovideo.setCode(albumsinfo.getCode());
						/*int videlcount=ggalbumService.countAlbuminfovideo(albumsinfovideo);
						if(videlcount<1){*/
							try {
								String Dramacode = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 10);
								albumsinfovideo.setDramacode(Dramacode);
								/*String Dramacode = System.currentTimeMillis()+"";
								albumsinfovideo.setDramacode(Dramacode.substring(3, Dramacode.length()-1));*/
								ggalbumService.insertAlbuminfovideo(albumsinfovideo);
							} catch (Exception e) {
								log.info("数据已存在" + "..." +result.getJSONObject(i));
								continue;
							}
						/*}*/
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
	}
