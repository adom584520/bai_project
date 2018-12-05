package com.pbtd.service;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pbtd.dao.seriesDetail.SeriesDetailMapper;
import com.pbtd.entity.SeriesDetail;
import com.pbtd.helpBean.SubPhoneSeriesDetail;
import com.pbtd.util.BeanFactory;
import com.pbtd.util.JsonDateValueProcessor;

import common.Logger;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class VideoReceiveService {

	private SeriesDetailMapper seriesDetailMapper=(SeriesDetailMapper)BeanFactory.getBean(SeriesDetailMapper.class);
	private static final Logger logger=Logger.getLogger(VideoReceiveService.class);
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf_hms=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void saveSeriesDetail(SeriesDetail seriesDetail){
		//也可以做点什么 
		seriesDetailMapper.saveInfo(seriesDetail);
		
	}
	
	
	public int receivePhoneAlbumVideo(Map<String, Object> map) {
		try {
			logger.info("开始取数据");
			String jsonString = importurl(map, 1, "phoneAlbumVideo");
			if (jsonString != null && !"".equals(jsonString)) {
				//JsonConfig  jsonConfig=new JsonConfig();
				//jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据  1:成功  0：失败
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数 
				if (code.equals("1")) {
					JSONArray jsonArr = json.getJSONArray("data");
					logger.info("第"+1+"页");
					saveAlbumVideoJson(jsonArr);
					if (numsum > 1) { 		//是否再取
						for (int i = 2; i <= numsum; i++) {  	//第几页传几
							String nextjsonString = importurl(map,i, "phoneAlbumVideo");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									logger.info("第"+i+"页");
									saveAlbumVideoJson(nextjsonObj);	//保存
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
					//开始注入
					InjectionService.beginInject();
					
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	
		/**
		 *  根据接口获取数据 并返回json字符串
		 * @param urlpath
		 * @param curtime
		 * @param limit
		 * @param type
		 * @param id
		 * @return
		 */
		public String importurl(Map map, int limit, String type) {
			String curtime=null;
			if(map.get("curtime")!=null){
				curtime=sdf.format(map.get("curtime"));
			}
			String requestUrl = map.get("url")+ "?limit=" + limit
					+"&start="+map.get("start")+"&curtime="+curtime+"&ids="+map.get("ids");
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
					return "";
				}
			}
			return jsonObject;
		}
		
		// 解析手机专辑数据信息 保存数据
		private int saveAlbumVideoJson(JSONArray jsonArr) {
			if (jsonArr.size() > 0) {
				for (int i = 0; i < jsonArr.size(); i++) {
					try {
						JSONObject jsonObj = jsonArr.getJSONObject(i);
						//json Date处理  防止当前日期
						//String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss"};  
				        //JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));  
						SubPhoneSeriesDetail subSeriesDetail=(SubPhoneSeriesDetail)JSONObject.toBean(jsonObj, SubPhoneSeriesDetail.class);
						logger.info(i+"条记录："+subSeriesDetail.getSeriesCode()+" "+subSeriesDetail.getDrama());
						List<SeriesDetail>  sdList=this.webBeanToLocalBean(subSeriesDetail);
						for(SeriesDetail ss:sdList){
							Integer num=this.seriesDetailMapper.getInfoNumBySeriesAndCodeAndVersionAndTerminal(ss);
							if(num>0){ //只更新未注入，失败部分 
								this.seriesDetailMapper.updateInfoBySeriesAndCodeAndVersion(ss);
							}else{
								this.seriesDetailMapper.saveInfo(ss);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue; //继续执行
					}
				}
			}
			return 1;
		}
		
		
		private List<SeriesDetail> webBeanToLocalBean(SubPhoneSeriesDetail bean){
			List<SeriesDetail> sdList=new ArrayList<SeriesDetail>();
			
			SeriesDetail sd=new SeriesDetail();
			sd.setReceiveTime(new Date());
			sd.setTerminalType(1);//手机1 tv 2
			
			sd.setSubWebId(bean.getId());
			sd.setCpSeriesId(bean.getCpseriesCode());
			sd.setSeriesId(bean.getSeriesCode().toString());
			sd.setMovieCode(bean.getDrama().toString());
			sd.setProgramName(bean.getDramaname());
			try {
				sd.setCreateTime(bean.getCreatetime()==null?null:sdf_hms.parse(bean.getCreatetime()));
			} catch (ParseException e) {
				logger.error("接收数据转换成Date异常",e);
			}
			sd.setSourceUrl(bean.getZxfileurl());
			sd.setPriority(bean.getZxPriority());
			sd.setIsInsert(bean.getZxInjectState());
			
			String versionList=bean.getZxversionlist();
			boolean isInclude=this.checkSourceUrlIncludeVersion(sd.getSourceUrl());
			
			if(isInclude){ //银河媒资处理
				if(versionList!=null){
					String[] versionArr=versionList.split(",");
					if(versionArr.length>0){
						for(String ss:versionArr){
							Integer version=Integer.valueOf(ss);
							sd.setVersion(version);
							sd.setSourceUrl(this.getNewUrlFromVersion(ss, sd.getSourceUrl()));
							sdList.add(sd);
						}
					}
				}
				
			}else{ //国广媒资处理 
				int inx=versionList.indexOf(",");
				if(inx==-1){
					sd.setVersion(Integer.valueOf(versionList));
				}else{
					sd.setVersion(4); //默认4，720P
				}
				sdList.add(sd);
				
			}
			return sdList;
		}
		
		
		private String getNewUrlFromVersion(String version,String oldUrl){
			String newUrl="";
			int inx=oldUrl.lastIndexOf("/");
			int inxDot=oldUrl.lastIndexOf(".");
			//   /2.m3u8
			if(inx!=-1 && inxDot!=-1){
				String prefixUrl=oldUrl.substring(0, inx+1);
				String suffixUrl=oldUrl.substring(inxDot);
				newUrl=prefixUrl+version+suffixUrl;
			}
			return newUrl; 
		}
		
		private boolean checkSourceUrlIncludeVersion(String sourceUrl){
			boolean isInclude=false;
			int inxLine=sourceUrl.lastIndexOf("/");
			int inxDot=sourceUrl.lastIndexOf(".");
			String versionFromUrl=sourceUrl.substring(inxLine+1, inxDot);
			Pattern pattern = Pattern.compile("^[0-9]*$"); 
			Matcher matcher = pattern.matcher(versionFromUrl);
		   if(matcher.matches()){
		       isInclude=true;
		   } 
			return isInclude;
		}
		
		
		public static void main(String[] args) {
			
			
		}
	
}
