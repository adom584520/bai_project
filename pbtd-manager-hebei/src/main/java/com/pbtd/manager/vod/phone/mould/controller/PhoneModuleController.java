package com.pbtd.manager.vod.phone.mould.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.conf.VodInterfaceBeanConfig;
import com.pbtd.manager.vod.phone.mould.service.face.IVodPhoneModuleInterface;

import net.sf.json.JSONObject;

/**模版相关接口
 * @author zr
 */
@Controller 
@RequestMapping("/phone/vod/module")
public class PhoneModuleController {
	
	@Autowired
	private IVodPhoneModuleInterface   moduleService;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(PhoneModuleController.class); 


	//1.获取频道下的所有模块数据
	@RequestMapping(value = "/getphoneChannelModuleList")
	public String getChannelModuleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long beginTime=logPreMethod(request);//开始日志
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "5" : queryParams.get("pageSize").toString();
		//频道
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		System.out.println("获取频道下的所有模块数据接口getphoneChannelModuleList 入参channel：====>"+channel+"入参pageNo：====>"+limitstring );
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
		int limit = Integer.parseInt(limitstring)*pagesize;
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
		logReturnMethod(request, json.toString().substring(0, 50), beginTime);
		return null;
	}



	//2.换一批
	@RequestMapping(value = "/getphoneChannelModuleListForChannage")
	public String getChannelModuleByOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long beginTime=logPreMethod(request);//开始日志
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		//频道
		//String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		//模块id
		String moduleId = queryParams.get("mId") == null ? "" : queryParams.get("mId").toString();
		//
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
		//queryParams.put("status", 1);
		//queryParams.put("levels", 2);
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
		logReturnMethod(request, json.toString(), beginTime);
		return null;
	}
	
	public static long logPreMethod(HttpServletRequest request){
		long beginTime=Calendar.getInstance().getTimeInMillis();
		StringBuilder builder=new StringBuilder();
		builder.append(" -----------------begin-------------------------");
		builder.append("\n请求路径：");
		String uri=request.getRequestURI();
		builder.append(uri);
		builder.append("  \n请求参数：");
		@SuppressWarnings("rawtypes")
		Enumeration em = request.getParameterNames();
		 while (em.hasMoreElements()) {
		    String name = (String) em.nextElement();
		    String value = request.getParameter(name);
		    builder.append(name+":"+value+" ");
		}
		logger.info(builder.toString());
		return beginTime;
	}
	
	public static void logReturnMethod(HttpServletRequest request,Object object,long beginTime){
		StringBuilder builder=new StringBuilder("\n请求路径：");
		String uri=request.getRequestURI();
		builder.append(uri);
		builder.append("  \n返回数据：");
		builder.append(object);
		long endTime=Calendar.getInstance().getTimeInMillis();
		long executeTime=endTime-beginTime;
		builder.append("  \n方法执行耗时："+executeTime+"ms");
		builder.append(" \n-------------------end-----------------------\n");
		logger.info(builder.toString());
	}

}
