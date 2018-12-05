package com.pbtd.manager.vod.tv.mould.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.util.RequestUtil;
import com.pbtd.manager.vod.tv.mould.service.face.IvodTvModuleInterface;

import net.sf.json.JSONObject;

/**模版相关接口
 * @author zr
 */
@Controller 
@RequestMapping("/tv/vod/module")
public class ModuleController {
	
	@Autowired
	private IvodTvModuleInterface   moduleService;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ModuleController.class); 


	//1.获取频道下的所有模块数据
	@RequestMapping(value = "/getChannelModuleList")
	public String getChannelModuleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "10" : queryParams.get("pageSize").toString();
		//频道
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		System.out.println("====>"+channel);
		List<Map<String, Object>>   outputlist =new ArrayList<>();//拼接返回数据
		if(channel.equals("") || channel == null){
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "{}");
			response.getWriter().write(json.toString());
			return null;
		}
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("pagesize", pagesize);
		queryParams.put("limit",limit);
		queryParams.put("status", 1);
		queryParams.put("levels", 2);
		try {
			queryParams.put("channel", channel);
			List<Map<String, Object>> channellist = moduleService.getModuleAlbum(queryParams);	//获取数据
			if(channellist.size()>0){

				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", channellist);
			}else{
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 二级栏目换一批专辑列表 tgetchannel2Albumpage?"+queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}



	//2.换一批
	@RequestMapping(value = "/getChannelModuleByOne")
	public String getChannelModuleByOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		//频道
		//String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		//模块id
		String moduleId = queryParams.get("moduleId") == null ? "" : queryParams.get("moduleId").toString();
		String pageNum = queryParams.get("pageNum") == null ? "1" : queryParams.get("pageNum").toString();
		System.out.println("getChannelModuleByOne接口请求参数   moduleId:"+ moduleId +" || pageNum:"+pageNum);
		List<Map<String, Object>>   outputlist =new ArrayList<>();//拼接返回数据
		if(moduleId == null || moduleId.equals("")){
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "{}");
			response.getWriter().write(json.toString());
			return null;
		}
		queryParams.put("status", 1);
		queryParams.put("levels", 2);
		try {
			queryParams.put("id", moduleId);
			List<Map<String, Object>> channellist = moduleService.getModuleAlbumforOne(queryParams);	//获取数据
			if(channellist.size()>0){
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("moduleId", moduleId);
				json.put("data", channellist);
			}else{
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 二级栏目换一批专辑列表 tgetchannel2Albumpage?"+queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

}
