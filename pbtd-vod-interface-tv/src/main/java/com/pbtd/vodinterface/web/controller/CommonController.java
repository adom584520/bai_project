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

import com.alibaba.fastjson.JSON;
import com.pbtd.vodinterface.util.RequestUtil;
import com.pbtd.vodinterface.web.service.impl.CommonService;

import net.sf.json.JSONObject;











/**辅助接口
 * @author molly
 */
@Controller 
@RequestMapping("/tv/vod")
public class CommonController {
	
	@Autowired
	private CommonService commonService;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CommonController.class); 
     
	/**
	 * 
	 * @param request group_id=&proj_id=& actorId=
	 * @param response 演员详情 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getActor")
	public String getactor(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		JSONObject json=new JSONObject();
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		String actorId=queryParams.get("actorId") == null ? "" : queryParams.get("actorId").toString();
		if("".equals(actorId)){
			json=new JSONObject();
		    json.accumulate("code", 1);
		    json.accumulate("message", "参数有误");
		    json.accumulate("data","{}");
		    response.getWriter().write(json.toString());
			return null;
		}
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("status", 1);
		queryParams.put("limit", limit);
		queryParams.put("pagesize", pagesize);
		try{
			List<Map<String,Object>> list=commonService.getactors(queryParams);
			if(list.size()>0){
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "成功");
			    json.accumulate("data",list.get(0));			    
			}else{
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "无数据");
			    json.accumulate("data","{}");	
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("访问出错: 演员 getActor?"+queryParams);
			json=new JSONObject();
		    json.accumulate("code", -1);
		    json.accumulate("message", "访问出错,请稍候访问!");
		    json.accumulate("data","{}");
		}
		response.getWriter().write(json.toString());
		return null;
		
	}
	/**
	 *  
	 * @param request 演员id
	 * @param response 演员列表 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getActorList")
	public String getactorlist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		JSONObject json=new JSONObject();
		Map<String,Object> queryParams=RequestUtil.asMap(request);	
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		String seriesCodestring = queryParams.get("seriesCode") == null ? "" : queryParams.get("seriesCode").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		if("".equals(seriesCodestring)){
			json=new JSONObject();
		    json.accumulate("code", 0);
		    json.accumulate("message", "参数有误");
		    return null;
		}
		int seriesCode=Integer.parseInt(seriesCodestring);
		queryParams.put("status", 1);
		queryParams.put("limit", limit);
		queryParams.put("pagesize", pagesize);
		queryParams.put("seriesCode",seriesCode);
		try{
			List<Map<String,Object>> list=commonService.getactorlist(queryParams);
			if(list.size()>0){
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "成功");
			    json.accumulate("data",JSON.toJSONString(list));			    
			}else{
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "无数据");
			    json.accumulate("data","[]");	
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("访问出错: 演员tgetActorList?"+queryParams);
			json=new JSONObject();
		    json.accumulate("code", -1);
		    json.accumulate("message", "访问出错,请稍候访问!");
		    json.accumulate("data","[]");
		}
		response.getWriter().write(json.toString());
		return null;
		
	}

	
	/**
	 *  
	 * @param request group_id=&proj_id=
	 * @param response 频道列表
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getChannel")
	public String getchannel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		JSONObject json=new JSONObject();
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("status", 1);
		queryParams.put("limit", limit);
		queryParams.put("pagesize", pagesize);
		queryParams.put("levels", 1);
		try{
			List<Map<String,Object>> list=commonService.getChannel(queryParams);
			if(list.size()>0){
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "成功");
			    json.accumulate("data",JSON.toJSONString(list));			    
			}else{
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "无数据");
			    json.accumulate("data","[]");	
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("访问出错: 频道tgetChannel?"+queryParams);
			json=new JSONObject();
		    json.accumulate("code", -1);
		    json.accumulate("message", "访问出错,请稍候访问!");
		    json.accumulate("data","[]");
		}
		response.getWriter().write(json.toString());
		return null;
		
	}

	/**
	 *  
	 * @param request level(必填) group_id=&proj_id=
	 * @param response 频道二级栏目  
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getChannel2")
	public String getsubchannel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		JSONObject json=new JSONObject();
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		String channel = queryParams.get("channel") == null ? "0" : queryParams.get("channel").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("status", 1);
		queryParams.put("levels", 2);
		queryParams.put("parentCode", channel);
		queryParams.put("limit", limit);
		queryParams.put("pagesize", pagesize);
		try{
			List<Map<String,Object>> list=commonService.getChannel(queryParams);
			if(list.size()>0){
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "成功");
			    json.accumulate("data",JSON.toJSONString(list));			    
			}else{
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "无数据");
			    json.accumulate("data","[]");	
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("访问出错: 频道tgetChannel2?"+queryParams);
			json=new JSONObject();
		    json.accumulate("code", -1);
		    json.accumulate("message", "访问出错,请稍候访问!");
		    json.accumulate("data","[]");
		}
		response.getWriter().write(json.toString());
		return null;
		
	}

	/**
	 *  
	 * @param request  频道id(必填) group_id=&proj_id=
	 * @param response 标签
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getLabel")
	public String getlabel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		JSONObject json=new JSONObject();
		Map<String,Object> queryParams=RequestUtil.asMap(request);	
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		String channel = queryParams.get("channel") == null ? "0" : queryParams.get("channel").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("status", 1);
		queryParams.put("limit", limit);
		queryParams.put("channelCode", channel);
		queryParams.put("pagesize", pagesize);
			try{
			
			List<Map<String,Object>> labeltype=commonService.getlabeltype(queryParams);
			List<Map<String,Object>> maplist=commonService.getlable(queryParams);
			if(maplist.size()>0){
				Map<String, Object> mapjson =new HashMap<>();
				for(int i=0;i<labeltype.size();i++){
					List<Map<String, Object>> list1 =new ArrayList<>();
				for (Map<String, Object> labelmap : maplist) {
					if(labeltype.get(i).get("id").equals(labelmap.get("type"))){
						labelmap.remove("type");
						list1.add(labelmap);
					}
					mapjson.put("level"+(i+1), list1);
				}
				}
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data",mapjson);			    
			}else{
				json=new JSONObject();
			    json.accumulate("code", 1);
			    json.accumulate("message", "无数据");
			    json.accumulate("data","{}");	
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("访问出错: 频道tgetLabel?"+queryParams);
			json=new JSONObject();
		    json.accumulate("code", -1);
		    json.accumulate("message", "访问出错,请稍候访问!");
		    json.accumulate("data","{}");
		}
		response.getWriter().write(json.toString());
		return null;
		
	}
	//专题 
		@RequestMapping(value = "/getSpecial")
		public String tgetspecial(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
			queryParams.put("status", 1);
			try {
				List<Map<String, Object>> list = commonService.getspecial(queryParams);
				if(list.size()>0){
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "成功");
					json.put("data", JSON.toJSONString(list));
				}else{
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "无数据");
					json.put("data", "[]");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("访问出错:专题  tgetSpecial?"+queryParams);
				json = new JSONObject();
				json.put("code", -1);
				json.put("message", "访问出错,请稍后访问！");
				json.put("data", "[]");
				
			}
			response.getWriter().write(json.toString());
			return null;
		}

		//专题详情 条件 专题id(必填)
		@RequestMapping(value = "/getSpecialVideo")
		public String tgetspecialvideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
			String specialid = queryParams.get("specialid") == null ? "" : queryParams.get("specialid").toString();
			if(specialid.equals("")){
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "访问参数有误！");
				json.put("data", "[]");
				response.getWriter().write(json.toString());
				return null;
			}
			queryParams.put("specialid", specialid);
			try {
				List<Map<String, Object>> list1=commonService.getspecial(queryParams);
				List<Map<String, Object>> list = commonService.getspecialvideo(queryParams);
				Map<String,Object> map=new HashMap<>();
				if(list.size()>0 || list1.size()>0){
					if(list1.size()>0){
					map.put("special", list1.get(0));
					}else{
					 map.put("special","{}");	
					}
					map.put("album", list);
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "成功");
					json.put("data", map);
				}else{
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "无数据");
					json.put("data", "[]");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("访问出错: tgetSpecialVideo专题详情?"+queryParams);
				json = new JSONObject();
				json.put("code", -1);
				json.put("message", "访问出错,请稍后访问！");
				json.put("data", "[]");
			}
			response.getWriter().write(json.toString());
			return null;
		}
}
