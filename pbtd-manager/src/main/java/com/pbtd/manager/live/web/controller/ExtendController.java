package com.pbtd.manager.live.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.live.domain.LiveBussInfo;
import com.pbtd.manager.live.service.ILiveBussService;

/**
 *中心运营平台手动下发接口
 * @author zr
 *
 */
/**
 * 用户操作
 * 
 * @author JOJO
 *
 */
@RequestMapping("/live/DataExtend")
@Controller
public class ExtendController {
	public static Logger logger = Logger.getLogger(ExtendController.class);
	@Autowired
	private ILiveBussService liveBussService;
	/**
	 * 指定分平台版本信息下发/live/DataExtend/version/{id}
	 * @return
	 */
	@RequestMapping("/version/{id}")
	public String setversion(@PathVariable("id") int id) {
		LiveBussInfo  livebuss = liveBussService.load(id);
		if(livebuss != null){
			logger.info("下发"+livebuss.getName()+"项目直播版本数据开始");
				final String address = livebuss.getProjectip();
				final int bussid = id;
				final String url ="http://"+address+"/live/below/versioninterface/"+bussid;
				logger.info("ip:"+url);
				try {
					Thread hth = new Thread() {
						@Override
						public void run() {
							try {
								// 手动下发 到运营库接口
								sethttp(url);
							} catch (IllegalArgumentException ec) {
								interrupted();
							}
						}
					};
					hth.start();
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		logger.info("下发项目直播版本数据结束，查看分平台数据是否成功");
		return null;
	}
	
	/**
	 * 指定分平台频道信息下发/live/DataExtend/livechannel/{id}
	 * @return
	 */
	@RequestMapping("/livechannel/{id}")
	public String setChanel(@PathVariable("id") int id) {
		LiveBussInfo  livebuss = liveBussService.load(id);
		if(livebuss != null){
			logger.info("下发"+livebuss.getName()+"项目直播频道数据开始");
			final String address = livebuss.getProjectip();
			final int bussid = id;
			final String url ="http://"+address+"/live/below/channelinterface/"+bussid;
			logger.info("ip:"+url);
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							sethttp(url);
						} catch (IllegalArgumentException ec) {
							interrupted();
						}
					}
				};
				hth.start();
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("下发项目直播频道数据结束，查看分平台数据是否成功");
		return null;
	}
	

		//手动下发方法
	@ResponseBody
		public int sethttp(String requestUrl){
			//String requestUrl =central.actors;
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
				logger.info(requestUrl+"接口访问开始");
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
				logger.info("接口访问反馈"+jsonObject.toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return 0;
			} catch (ProtocolException e) {
				e.printStackTrace();
				return 0;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return 0;
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}
		
}
