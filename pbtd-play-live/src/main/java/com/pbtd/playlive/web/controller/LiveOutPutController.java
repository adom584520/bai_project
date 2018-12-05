package com.pbtd.playlive.web.controller;

import java.io.IOException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playlive.util.getDate.GetAutomaticData;
import com.pbtd.playlive.util.getDate.GetManualData;


/**
 * 直播版本管理
 * @author YFENG
 *
 */
@RequestMapping("/live/below")
@Controller
public class LiveOutPutController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private GetManualData getManualData;
	@Autowired
	private GetAutomaticData getAutomaticData;

	/**
	 * 1 根据项目号获取该项目的版本信息
	 * @param id 标识
	 * @return 记录
	 * /live/below/versioninterface/1
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/versioninterface")
	public String versioninterface(){
		try {
			getManualData.getVersion();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 2 根据项目号获取该项目的上线频道
	 * @param id 标识
	 * @return 记录
	 * /live/below/channelinterface/1
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/channelinterface")
	public String channelinterface(){
		try {
			getManualData.getChannel();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 3 节目包同步
	 * @param id 标识
	 * @return 记录
	 * /live/below/packageinterface
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/packageinterface")
	public String packageinterface(){
		try {
			getAutomaticData.getPackage();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}



}