package com.pbtd.vodinterface.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.pbtd.vodinterface.util.RequestUtil;
import com.pbtd.vodinterface.web.service.face.ICommonService;

import ch.qos.logback.classic.Logger;
import net.sf.json.JSONObject;

/**
 * 辅助接口
 * 
 * @author zr
 */
@Controller
@RequestMapping("/phone/vod")
public class CommonController {
	@Autowired
	private ICommonService commonservice;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CommonController.class);
	// 演员 名称
	// 演员列表 演员id(必填)

	// 频道
	@RequestMapping(value = "/getChannel")
	public String pgetChannel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("limit") == null ? "0" : queryParams.get("limit").toString();
		String pagesizestring = queryParams.get("pageNum") == null ? "15" : queryParams.get("pageNum").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("status", 1);
		queryParams.put("limit", limit);
		queryParams.put("pagesize", pagesize);
		queryParams.put("levels", 1);
		try {
			List<Map<String, Object>> list = commonservice.getChannel(queryParams);
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", JSON.toJSONString(list));
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 频道 pgetChannel?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 频道二级栏目 频道id(必填)
	@RequestMapping(value = "/getChannel2")
	public String pgetChannel2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("limit") == null ? "0" : queryParams.get("limit").toString();
		String pagesizestring = queryParams.get("pageNum") == null ? "15" : queryParams.get("pageNum").toString();
		String channel = queryParams.get("channel") == null ? "0" : queryParams.get("channel").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("status", 1);
		queryParams.put("levels", 2);
		queryParams.put("parentCode", channel);

		queryParams.put("limit", limit);
		queryParams.put("pagesize", pagesize);
		try {
			List<Map<String, Object>> list = commonservice.getChannel(queryParams);
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", JSON.toJSONString(list));
			} else {
				json = new JSONObject();
				json.put("code", 0);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 频道二级栏目 pgetChannel2?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 标签 频道id(必填)
	@RequestMapping(value = "/getLabel")
	public String pgetLabel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("limit") == null ? "0" : queryParams.get("limit").toString();
		String pagesizestring = queryParams.get("pageNum") == null ? "15" : queryParams.get("pageNum").toString();
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		if("".equals(channel)){
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问参数有误");
			json.put("data", "{}");
			response.getWriter().write(json.toString());
			return null;
		}
		if(limit<1){
			limit=1;
		}
		queryParams.put("status", 1);
		queryParams.put("channelCode", channel);
		queryParams.put("limit", (limit-1)*pagesize);
		queryParams.put("pagesize", pagesize);
		try {
			List<Map<String, Object>> labeltype = commonservice.getlabeltype(queryParams);
			List<Map<String, Object>> maplist = commonservice.getLabel(queryParams);
			if(maplist.size()>0){

				Map<String, Object> map=new HashMap<>();
				for ( int i=0;i<labeltype.size();i++) {
					List<Map<String, Object>> list1=new ArrayList<>();
					for (Map<String, Object> labelmap : maplist) {
						String n=labeltype.get(i).get("id").toString().trim();
						String typei=labelmap.get("type").toString();
						if(n.equals(typei)){ 
							list1.add(labelmap);
						}
						map.put("level"+(i+1), list1);
					}
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", " 成功");
					json.put("data", map);
				} 
			}else {
				json = new JSONObject();
				json.put("code", 0);
				json.put("message", "无数据");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 标签 pgetLabel?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 专题
	@RequestMapping(value = "/getSpecial")
	public String pgetspecial(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		queryParams.put("status", 1);
		try {
			List<Map<String, Object>> list = commonservice.getpgetspecial(queryParams);
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", JSON.toJSONString(list));
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:专题  pgetSpecial?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");

		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 专题详情 条件 专题id(必填)
	@RequestMapping(value = "/getSpecialVideo")
	public String pgetspecialvideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String specialid = queryParams.get("specialid") == null ? "" : queryParams.get("specialid").toString();
		if (specialid.equals("")) {
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误！");
			json.put("data", "[]");
			response.getWriter().write(json.toString());
			return null;
		}
		queryParams.put("special_id", specialid);
		try {
			List<Map<String,Object>> specialist=commonservice.getpgetspecial(queryParams);
			List<Map<String, Object>> list = commonservice.getpgetspecialvideo(queryParams);
			Map<String,Object> map=new HashMap<>();
			map.put("special", specialist.get(0));
			map.put("specialVideo", list);
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", map);
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: pgetSpecialVideo专题详情?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 专区推荐页图片 （channel必填）
	@RequestMapping(value = "/getRecommendPic")
	public String pgetrecommandpic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		if (channel.equals("")) {
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误！");
			json.put("data", "{}");
			response.getWriter().write(json.toString());
			return null;
		}
		queryParams.put("channel", channel);
		try {
			List<Map<String, Object>> list = commonservice.recommandpic(queryParams);
			queryParams.put("level", 1);
			List<Map<String, Object>> labellist = commonservice.getLabel(queryParams);
			List<Map<String, Object>> labeltype = commonservice.getlabeltype(queryParams);
			List<Map<String, Object>> list1 =new ArrayList<>();
			if (list.size() > 0) {
				json = new JSONObject();
				Map<String, Object> map = new HashMap<>();
				map.put("pics", list);
				for (Map<String, Object> map2 : labellist) {
					String level = map2.get("type").toString();
					String n=labeltype.get(0).get("id").toString().trim();
					if (level.trim().equals(n)) {
						list1.add(map2);
					}
				}
				if(list1.size()>6){
					map.put("labels", list1.subList(0, 6));
				}else{
					map.put("labels", list1);
				}
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", map);
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:getRecommandPic 专区推荐页图片?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 底部导航栏
	@RequestMapping(value = "/getBottomNavigation")
	public String pgetbottomnavigation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		try {
			List<Map<String, Object>> list = commonservice.bottomnavigation(queryParams);
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", list);
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:pgetBottomnavigation 底部导航栏?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 获取LOGO
	@RequestMapping(value = "/getLogo")
	public String pgetlogo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> map = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, Object> map1 = commonservice.getlogo(map);
			if (map != null) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", map1);
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:pgetLogo 获取LOGO?" + map);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 文字推荐
	@RequestMapping(value = "/getTextRecommendation")
	public String pgettextrecommendation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		try {
			List<Map<String, Object>> list = commonservice.textrecommendation(queryParams);
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", list.get(0));
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:pgetTextRecommendation 文字推荐?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 专区推荐轮播图
	@RequestMapping(value = "/getSlideShow")
	public String pgetSlideshow(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		try {
			queryParams.put("status", 1);
			List<Map<String, Object>> list = commonservice.slideshow(queryParams);
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", list);
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:pgetSlideShow 专区推荐轮播图?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}
	//手机开机图片
	@RequestMapping(value = "/getStartSlideShow")
	public String pgetStartSlideshow(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		try {
			queryParams.put("status", 1);
			List<Map<String, Object>> list = commonservice.startSlideshow(queryParams);
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", list.get(0));
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("访问出错:pgetSartSlideShow 开机轮播图?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 获取指定频道下的标签列表信息    频道id(必填)
	@RequestMapping(value = "/getLabelsOfChannel")
	public String getLabelsOfChannel (HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("limit") == null ? "0" : queryParams.get("limit").toString();
		String pagesizestring = queryParams.get("pageNum") == null ? "15" : queryParams.get("pageNum").toString();
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		if("".equals(channel)){
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问参数有误!");
			json.put("data", "[]");
			response.getWriter().write(json.toString());
			return null;
		}
		if(limit<1){
			limit=1;
		}
		queryParams.put("status", 1);
		queryParams.put("channelCode", channel);
		queryParams.put("limit", (limit-1)*pagesize);
		queryParams.put("pagesize", pagesize);
		List<Map<String, Object>> list1 = new ArrayList<>();
		List<Map<String, Object>> list2 = new ArrayList<>();
		List<Map<String, Object>> list3 = new ArrayList<>();
		List<Map<String, Object>> list4 = new ArrayList<>();
		try {
			List<Map<String, Object>> maplist = commonservice.getLabel(queryParams);
			List<Map<String, Object>> labeltype = commonservice.getlabeltype(queryParams);
			if (maplist.size() > 0) {
				for (Map<String, Object> map : maplist) {
					for(int ii=0;ii<labeltype.size();ii++){
						String n=labeltype.get(ii).get("id").toString().trim();
						if(ii==0 && map.get("type").equals(n) ){
							list1.add(map);
						}else if(ii==1 && map.get("type").equals(n) ){
							list2.add(map);
						} else if(ii==2 && map.get("type").equals(n) ){
							list3.add(map);
						} else if(ii==3 && map.get("type").equals(n) ){
							list4.add(map);
						} else {
							//	list1.add(map);
						} 
					}
				}
				List<Object> listmapjson = new ArrayList<>();
				listmapjson.add(list1);
				listmapjson.add(list2);
				listmapjson.add(list3);
				listmapjson.add(list4);
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", listmapjson);
			} else {
				json = new JSONObject();
				json.put("code", 0);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 标签 pgetLabel?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}
	//获取用户自制频道接口  用户ID(userId)为必填 channel为选填(多个频道id用逗号隔开拼接)
	@RequestMapping(value = "/getChannelForUser")
	public String  pgetchannelforuser (HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		String userId=queryParams.get("userId")==null?"":queryParams.get("userId").toString();		
		if("".equals(userId)){
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问参数有误!");
			json.put("data", "[]");
			response.getWriter().write(json.toString());
			return null;
		}
		List<Map<String,Object>> maplist=new ArrayList<>();
		try{
			queryParams.put("levels", 1);
			Map<String,Object> channelmap= commonservice.findUser(queryParams);
			if(!"".equals(channel)){
				String[] channelCode=channel.split(",");
				List<Map<String,Object>> channellist=commonservice.findbychannel(queryParams);
				if(channelmap!=null){
					if(channelCode.length==channellist.size()){
						commonservice.deletechannelforuser(queryParams);
					}else{
						json = new JSONObject();
						json.put("code", -1);
						json.put("message", "访问参数有误");
						json.put("data", "[]");
						response.getWriter().write(json.toString());
						return null;
					}
				}
				for(String num:channelCode){
					queryParams.put("channelId", Integer.parseInt(num));
					commonservice.addchannelforuser(queryParams);
				}				        
				maplist=commonservice.pgetchannelforuser(queryParams);
			}else{	
				if(channelmap!=null){
					maplist=commonservice.pgetchannelforuser(queryParams);
				}else{
					maplist=commonservice.getChannel(queryParams);
				}
			}
			if(maplist.size()>0){
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", maplist);
			} else {
				json = new JSONObject();
				json.put("code", 0);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 标签getchannelforuser?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	//获取用户自制删除频道接口  用户ID(userId)为必填 channel为选填(多个频道id用逗号隔开拼接)
	@RequestMapping(value = "/getChannelNotForUser")
	public String  pgetchannelnotforuser (HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		String userId=queryParams.get("userId")==null?"":queryParams.get("userId").toString();		
		if("".equals(userId)){
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问参数有误!");
			json.put("data", "[]");
			response.getWriter().write(json.toString());
			return null;
		}
		List<Map<String,Object>> maplist=new ArrayList<>();
		try{
			queryParams.put("levels", 1);
			Map<String,Object> channellist= commonservice.findUser(queryParams);
			if(channellist==null){
				json = new JSONObject();
				json.put("code", 0);
				json.put("message", "无数据");
				json.put("data", "[]");
			}else{
				queryParams.put("channellist", channellist.get("channelId"));
				maplist=commonservice.pgetchannelnotforuser(queryParams);
				if(maplist.size()>0){
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "成功");
					json.put("data", maplist);
				} else {
					json = new JSONObject();
					json.put("code", 0);
					json.put("message", "无数据");
					json.put("data", "[]");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 标签pgetchannelnotforuser?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}
}

