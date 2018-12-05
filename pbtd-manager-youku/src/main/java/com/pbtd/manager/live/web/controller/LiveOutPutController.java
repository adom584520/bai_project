package com.pbtd.manager.live.web.controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.service.ILiveChannelService;
import com.pbtd.manager.live.service.ILiveVersionService;
import com.pbtd.manager.live.util.GetManualData;

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
	private ILiveChannelService liveChannelService;
	@Autowired
	private ILiveVersionService liveVersionService;
	@Autowired
	private GetManualData getManualData;




	/**
	 * 1 根据项目号获取该项目的版本信息
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/below/versioninterface/1
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/versioninterface/{bussid}")
	public List<LiveChannel> versioninterface(@PathVariable("bussid") int bussid){
		try {
			getManualData.getVersion(bussid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 2 根据项目号获取该项目的上线频道
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/below/channelinterface/1
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/channelinterface/{bussid}")
	public List<LiveChannel> channelinterface(@PathVariable("bussid") int bussid){
		try {
			getManualData.getChannel(bussid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}