package com.pbtd.vodinterface.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.vodinterface.util.LoggerUtil;
import com.pbtd.vodinterface.util.RequestUtil;
import com.pbtd.vodinterface.web.service.face.IModuleService;

import net.sf.json.JSONObject;


/**模版相关接口
 * @author zr
 */
@Controller 
@RequestMapping("/phone/vod/module")
public class ModuleController {
	@Autowired
	private IModuleService moduleService;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(AlbumController.class); 

		/**
		* showdoc
		* @catalog 测试文档/用户相关
		* @title 1.获取频道下的所有模块数据
		* @description 获取频道下的所有模块数据
		* @method get
		* @url https://www.showdoc.cc/home/user/login
		* @param username 必选 string 用户名  
		* @param password 必选 string 密码  
		* @param name 可选 string 用户昵称  
		* @return {"error_code":0,"data":{"uid":"1","username":"12154545","name":"吴系挂","groupid":2,"reg_time":"1436864169","last_login_time":"0"}}
		* @return_param groupid int 用户组id
		* @return_param name string 用户昵称
		* @remark 这里是备注信息
		* @number 98
		*/
	@RequestMapping(value = "/getChannelModuleList")
	@ResponseBody
	public Map<String, Object> getChannelModuleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "5" : queryParams.get("pageSize").toString();
		//频道
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		System.out.println("获取频道下的所有模块数据接口phone 的接口： getChannelModuleList 入参channel：====>"+channel+"入参pageNo：====>"+limitstring );

		List<Map<String, Object>>   outputlist =new ArrayList<>();//拼接返回数据
		if(channel.equals("") || channel == null){
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", null);
			return json;
		}
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("pagesize", pagesize);
		queryParams.put("limit",limit*pagesize);
		queryParams.put("status", 1);
		queryParams.put("levels", 2);
		try {
			queryParams.put("channel", channel);
			List<Map<String, Object>> channellist = moduleService.getModuleAlbum(queryParams);	//获取数据
			if(channellist.size()>0){
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", channellist);
			}else{
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 二级栏目换一批专辑列表 tgetchannel2Albumpage?"+queryParams);
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", null);
		}
		return json;
	}



	 /**
		* showdoc
		* @catalog 测试文档/用户相关
		* @title 2.换一换
		* @description 模块换一换接口的接口
		* @method get
		* @url https://www.showdoc.cc/home/user/login
		* @param username 必选 string 用户名  
		* @param password 必选 string 密码  
		* @param name 可选 string 用户昵称  
		* @return {"error_code":0,"data":{"uid":"1","username":"12154545","name":"吴系挂","groupid":2,"reg_time":"1436864169","last_login_time":"0"}}
		* @return_param groupid int 用户组id
		* @return_param name string 用户昵称
		* @remark 这里是备注信息
		* @number 99
		*/
	@RequestMapping(value = "/getModuleListForChange")
	@ResponseBody
	public Object getChannelModuleByOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		//频道
		//String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		//模块id
		String moduleId = queryParams.get("mId") == null ? "" : queryParams.get("mId").toString();
		//
		String pageNum = queryParams.get("pageNo") == null ? "1" : queryParams.get("pageNo").toString();
		queryParams.put("pageNum", pageNum);
		System.out.println("getChannelModuleByOne接口请求参数   moduleId:"+ moduleId +" || pageNo:"+pageNum);
		List<Map<String, Object>>   outputlist =new ArrayList<>();//拼接返回数据
		if(moduleId == null || moduleId.equals("")){
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "{}");
			response.getWriter().write(json.toString());
			return null;
		}
		try {
			queryParams.put("moduleId", moduleId);
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
