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

import com.pbtd.vodinterface.config.RedisInterfaceBeanConfig;
import com.pbtd.vodinterface.util.RedisService;
import com.pbtd.vodinterface.util.RequestUtil;
import com.pbtd.vodinterface.web.service.face.IAlbumService;
import com.pbtd.vodinterface.web.service.face.ICommonService;

import net.sf.json.JSONObject;


/**节目相关接口
 * @author zr
 */
@Controller 
@RequestMapping("/tv/vod")
public class AlbumController {

	@Autowired
	private IAlbumService albumservice;
	@Autowired
	private RedisService redisService;
	@Autowired
	private RedisInterfaceBeanConfig redisInterfaceBeanConfig;
	
	@Autowired
	private ICommonService commonService;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(AlbumController.class); 
	
	
	// 专辑列表  首页 （根据频道的二级栏目查询） 专辑列表 条件 频道id(必填)    
		@RequestMapping(value = "/getChannel2Album")
		public String tgetchannel2Album(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
			String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
			String pagesizestring = queryParams.get("pageSize") == null ? "50" : queryParams.get("pageSize").toString();
			String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
			if(channel.equals("")){
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "访问参数有误");
				json.put("data", "{}");
				response.getWriter().write(json.toString());
				return null;
			}
			String name = queryParams.get("name") == null ? "" : queryParams.get("name").toString();
			int pagesize = Integer.parseInt(pagesizestring);
			int limit = Integer.parseInt(limitstring);
			List<Map<String, Object>>   maplist=new ArrayList<>();//拼接返回数据
			queryParams.put("status", 1);
			queryParams.put("name", name);
			queryParams.put("parentCode", channel);
			queryParams.put("levels", 2);
			try {
				List<Map<String, Object>>  channel2list = commonService.findChannel(queryParams);	//获取频道下的二级栏目
						if(channel2list.size()>0){
							for (Map<String, Object> map : channel2list) {
								Map<String, Object>  channel2map=new HashMap<>();
								String count=map.get("count")==null?"6":map.get("count").toString();
								if(limit<1){
									limit=1;
								}
								int count1=Integer.parseInt(count);
								queryParams.put("limit",(limit-1)*count1);
								queryParams.put("pagesize", count1);
								String channelCode=map.get("channelCode")==null?"":map.get("channelCode").toString();
								queryParams.put("channel_albuminfo", channelCode);
								List<Map<String, Object>> list = albumservice.tgetchannel2Album(queryParams);
								channel2map.put("channel", map.get("parentCode"));
								channel2map.put("channelName", map.get("parentName"));
								channel2map.put("title", map.get("channelCode"));
								channel2map.put("titleName", map.get("channelName"));
								channel2map.put("type", map.get("type"));
/*								channel2map.put("isshow_left", map.get("isshow_left"));
								channel2map.put("isshow_right", map.get("isshow_right"));*/
								channel2map.put("data", list);
								maplist.add(channel2map);
							}
							
							if(maplist.size()>0){
								json = new JSONObject();
								json.put("code", 1);
								json.put("message", "成功");
								json.put("data", maplist);
							}else{
								json = new JSONObject();
								json.put("code", 1);
								json.put("message", "无数据");
								json.put("data", "[]");
							}
						}
					 else{
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "无数据");
							json.put("data", "[]");
					 }
			 
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("访问出错: 频道二级栏目专辑列表 tgetChannel2Album?"+queryParams);
				json = new JSONObject();
				json.put("code", -1);
				json.put("message", "访问出错,请稍后访问！");
				json.put("data", "{}");
			}
			response.getWriter().write(json.toString());
			return null;
		}
		//二级栏目 换一批
		@RequestMapping(value = "/getChannel2AlbumPage")
		public String tgetchannel2Albumpage(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
			String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
			String pagesizestring = queryParams.get("pageSize") == null ? "50" : queryParams.get("pageSize").toString();
			//频道
			String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
			//二级栏目
			String channelnext = queryParams.get("channelnext") == null ? "" : queryParams.get("channelnext").toString();
			if(channel.equals("") ||channelnext.equals("")){
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "访问参数有误");
				json.put("data", "{}");
				response.getWriter().write(json.toString());
				return null;
			}
			int pagesize = Integer.parseInt(pagesizestring);
			int limit = Integer.parseInt(limitstring);
			queryParams.put("status", 1);
			queryParams.put("levels", 2);
			try {
				queryParams.put("parentCode", channel);
				queryParams.put("channelCode", channelnext);
				List<Map<String, Object>>  channel2list = commonService.findChannel(queryParams);	//获取频道下的二级栏目
				queryParams.put("parentCode", "");
				queryParams.put("channelCode", "");
						if(channel2list.size()>0){
							Map<String, Object>  channel2map=new HashMap<>();
							List<Map<String, Object>> list=new ArrayList<>();
							   if(limit<1){
								   limit=1;
							   }
								queryParams.put("limit", (limit-1)*pagesize);
								queryParams.put("pagesize", pagesize);
								queryParams.put("channel_albuminfo", channelnext);
								list = albumservice.tgetchannel2Album(queryParams);	
								int albNum=albumservice.findchannel2Albumcount(queryParams);
							if(list.size()>0){
								json = new JSONObject();
								json.put("code", 1);
								json.put("message", "成功");
								json.put("albNum", albNum);
								json.put("data", list);
							}else{
								json = new JSONObject();
								json.put("code", 1);
								json.put("message", "无数据");
								json.put("data", "[]");
							}
						}
					 else{
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

		
		// 	推荐页 根据频道查询专辑列表
		@RequestMapping(value = "/getChannelAlbum")
		public String tgetchannelAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
			String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
			String pagesizestring = queryParams.get("pageSize") == null ? "50" : queryParams.get("pageSize").toString();
			int pagesize = Integer.parseInt(pagesizestring);
			int limit = Integer.parseInt(limitstring);
			List<Map<String, Object>>   maplist=new ArrayList<>();//拼接返回数据
			queryParams.put("status", 1);
			queryParams.put("levels", 1);
			try {
				List<Map<String, Object>>  channellist = commonService.findChannel(queryParams);	//获取频道 
						if(channellist.size()>0){							
							for (Map<String, Object> map : channellist) {
								Map<String,Object> queryParams1=new HashMap<>();
								Map<String, Object>  channel2map=new HashMap<>();
								String count=map.get("count")==null?"6":map.get("count").toString();
								int count1=Integer.parseInt(count);
								if(limit<1){
									limit=1;
								}
								queryParams1.put("limit", (limit-1)*count1);
								queryParams1.put("pagesize", count1);
								String channelCode=map.get("channelCode")==null?"":map.get("channelCode").toString();
								queryParams1.put("channel", channelCode);
								List<Map<String, Object>> list = albumservice.tgetchannelAlbum(queryParams1);
								channel2map.put("channel", channelCode);
								channel2map.put("channelName", map.get("channelName"));
								channel2map.put("type", map.get("type"));
								channel2map.put("data", list);
								maplist.add(channel2map);
							}
							
							if(maplist.size()>0){
								json = new JSONObject();
								json.put("code", 1);
								json.put("message", "成功");
								json.put("data", maplist);
							}else{
								json = new JSONObject();
								json.put("code", 1);
								json.put("message", "无数据");
								json.put("data", "[]");
							}
						}
					 else{
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "无数据");
							json.put("data", "[]");
					 }
			 
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("访问出错: 推荐页 根据频道查询专辑列表tgetChannelAlbum?"+queryParams);
				json = new JSONObject();
				json.put("code", -1);
				json.put("message", "访问出错,请稍后访问！");
				json.put("data", "[]");
			}
			response.getWriter().write(json.toString());
			return null;
		}
		//推荐页 根据频道查询专辑列表 换一批
		@RequestMapping(value = "/getChannelAlbumPage")
		public String tgetchannelAlbumpage(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
			String pagesizestring = queryParams.get("pageSize") == null ? "50" : queryParams.get("pageSize").toString();
			//频道
			String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
			if(channel.equals("")  ){
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "访问参数有误");
				json.put("data", "{}");
				response.getWriter().write(json.toString());
				return null;
			}
			int pagesize = Integer.parseInt(pagesizestring);
			int limit = Integer.parseInt(limitstring);
			queryParams.put("status", 1);
			queryParams.put("levels",1);
			queryParams.put("channelCode", channel);
			try {
				List<Map<String, Object>>  channellist = commonService.findChannel(queryParams);	//获取频道 
				if(channellist.size()>0){
						Map<String, Object>  channel2map=new HashMap<>();
								for (Map<String, Object> map : channellist) {
									String count=map.get("count")==null?"6":map.get("count").toString();
									int count1=Integer.parseInt(count);
									if(limit<1){
										limit=1;
									}
									queryParams.put("limit", (limit-1)*count1);
									queryParams.put("pagesize", count1);
									String channelCode=map.get("channelCode")==null?"":map.get("channelCode").toString();
									queryParams.put("channel", channelCode);
									List<Map<String, Object>> list = albumservice.tgetchannelAlbum(queryParams);
									channel2map.put("channel", channel);
									channel2map.put("channelName", map.get("channelName"));
									channel2map.put("type", map.get("type"));
									channel2map.put("data", list);
								}
								if(channel2map.size()>0){
									json = new JSONObject();
									json.put("code", 1);
									json.put("message", "成功");
									json.put("data", channel2map);
								}else{
									json = new JSONObject();
									json.put("code", 1);
									json.put("message", "无数据");
									json.put("data", "{}");
								}
							}
					 else{
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "无数据");
							json.put("data", "{}");
					 }
			 
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("访问出错: 推荐页 根据频道查询专辑列表 换一批 tgetchannel2Albumpage?"+queryParams);
				json = new JSONObject();
				json.put("code", -1);
				json.put("message", "访问出错,请稍后访问！");
				json.put("data", "{}");
			}
			response.getWriter().write(json.toString());
			return null;
		}
		
		//频道内标签  根据频道标签查询专辑列表
		@RequestMapping(value = "/getLabelAlbum")
		public String tgetlabelAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
			String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "50" : queryParams.get("pageSize").toString();
			String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
			if(channel.equals("") ){
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "访问参数有误");
				json.put("data", "[]");
				response.getWriter().write(json.toString());
				return null;
			}
			
			String label = queryParams.get("labelId") == null ? "" : queryParams.get("labelId").toString();
			int pagesize = Integer.parseInt(pagesizestring);
			int limit = Integer.parseInt(limitstring);
			queryParams.put("status", 1);
			queryParams.put("levels", 1);
			queryParams.put("channelCode", channel);
			try {
				List<Map<String, Object>>  channellist = commonService.findChannel(queryParams);	//获取频道 
						if(channellist.size()>0){
							 Map<String, Object> map= channellist.get(0);
						     Map<String,Object> queryParams1=new HashMap<>();
							 queryParams1.put("channel", channel);
						 	if(!"".equals(label)){
										String[] labels=label.split(",");
									 for(int i=0;i<labels.length;i++){
										 queryParams1.put("label"+(i+1), labels[i]);
									 }
								} 
							 queryParams1.put("status", 1);
								//获取条件
								if(limit<1){
									limit=1;
								}
								queryParams1.put("limit", (limit-1)*pagesize);
								queryParams1.put("pagesize", pagesize);
							List<Map<String, Object>> list = albumservice.tgetchannelAlbum(queryParams1);
							int albNum=albumservice.findchannelAlbumcount(queryParams1);
							if(list.size()>0){
								json = new JSONObject();
								json.put("code", 1);
								json.put("message", "成功");
								json.put("albNum", albNum);
								json.put("data", list);
							}else{
								json = new JSONObject();
								json.put("code", 1);
								json.put("message", "无数据");
								json.put("data", "[]");
							}
							
						}
					 else{
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "无数据");
							json.put("data", "[]");
					 }
			 
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("访问出错: 频道内标签  根据频道标签查询专辑列表  tgetLabelAlbum?"+queryParams);
				json = new JSONObject();
				json.put("code", -1);
				json.put("message", "访问出错,请稍后访问！");
				json.put("data", "[]");
			}
			response.getWriter().write(json.toString());
			return null;
		}

		
		
		//专辑详情 条件 专辑id(必填)   （角标pic，付费包）
		@RequestMapping(value = "/getAlbuminfo")
		public String tgetAlbuminfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String seriesCode = queryParams.get("seriesCode") == null ? "" : queryParams.get("seriesCode").toString();
		if(seriesCode.equals("")){
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "{}");
			response.getWriter().write(json.toString());
			return null;
		}
		queryParams.put("seriesCode", seriesCode);
		try {
				 
					 Map<String, Object>  map = albumservice.tgetAlbuminfo(queryParams);
						if(map!=null){
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "成功");
							json.put("data", map);
						}else{
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "无数据");
							json.put("data", "{}");
						}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:专辑详情 tgetAlbuminfo?"+queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}
		// 专辑关联推荐 专辑id(必填)
		@RequestMapping(value = "/getAlbuminfoRecommend")//----------------------------------------------------------------------------------------------
		public String tgetAlbuminforecommend(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
			String seriesCode = queryParams.get("seriesCode") == null ? "" : queryParams.get("seriesCode").toString();
			if (seriesCode.equals("")) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "访问参数有误");
				json.put("data", "[]");
				response.getWriter().write(json.toString());
				return null;
			}
			String redisAlbum = null;
			try {
				//从Redis中取数据
				 redisAlbum = redisService.get("t"+seriesCode);
			} catch (Exception e) {
				redisAlbum = "";
				logger.error("Redis取数据失败tv:"+e);
			}
			try {
				if (redisAlbum != null && !"".equals(redisAlbum)) {

					response.getWriter().write(redisAlbum);
					return null;
				} else {
					queryParams.put("seriesCode", seriesCode);
					List<Map<String, Object>> list = albumservice.tgetAlbuminforecommend(queryParams);
					if (list.size() >= 9) {
						json = new JSONObject();
						json.put("code", 1);
						json.put("message", "成功");
						json.put("data", list);
					} else {
						List<Map<String, Object>> itemList = albumservice.findalbum(seriesCode, list);
						if (itemList.size() <= 0) {
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "无数据");
							json.put("data", "[]");
						} else {
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "成功");
							json.put("data", itemList);
						}
					}
					if (redisAlbum == null) {
						//保存到Redis里并设置key过期时间setEx
						redisService.setEx("t"+seriesCode, Long.parseLong(redisInterfaceBeanConfig.redis_second),json.toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("访问出错:专辑关联推荐  pgetAlbuminfoRecommend?" + queryParams);
				json = new JSONObject();
				json.put("code", -1);
				json.put("message", "访问出错,请稍后访问！");
				json.put("data", "[]");
			}
			response.getWriter().write(json.toString());
			return null;
		}
		
		//剧集列表  条件 专辑id(必填)
		@RequestMapping(value = "/getAlbuminfoVideo")
		public String tgetAlbuminfovideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String seriesCode = queryParams.get("seriesCode") == null ? "" : queryParams.get("seriesCode").toString();
		if(seriesCode.equals("")){
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "[]");
			response.getWriter().write(json.toString());
			return null;
		}
		queryParams.put("seriesCode", seriesCode);
		try {
			List<Map<String, Object>> listmovieurl = albumservice.findmovieurl(queryParams);//查询返回播放地址字段	
			String name1 = "";String name2= "";String name3= "";
			for (int i=0;i<listmovieurl.size();i++) {
				if(i==0){
					name1=listmovieurl.get(i).get("name").toString();
				}else if(i==1){
					name2=listmovieurl.get(i).get("name").toString();
				}else{
					name3=listmovieurl.get(i).get("name").toString();
				}
				 
			}
			List<Map<String, Object>> list = albumservice.tgetAlbuminfovideo(queryParams);
			if (list.size() > 0) {
				for (Map<String, Object> map : list) {
					String m1=map.get(name1)==null?"":map.get(name1).toString();
					String m2=map.get(name2)==null?"":map.get(name2).toString();
					String m3=map.get(name3)==null?"":map.get(name3).toString();
					if(!m1.equals("")){
						map.put("movieUrl", m1);
					}else if(!m2.equals("")){
						map.put("movieUrl", m2);
					}else if(!m3.equals("")){
						map.put("movieUrl", m3);
					}else{
						map.put("movieUrl", "");
					}
				}
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "成功");
							json.put("data", list);
						}else{
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "无数据");
							json.put("data", "[]");
						}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:剧集列表  tgetAlbuminfoVideo?"+queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}
		//通过演员id查询所有相关专辑  演员id必填
				@RequestMapping(value = "/getActorAlbuminfo")
				public String tgetActorAlbuminfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
				JSONObject json = new JSONObject();
				Map<String, Object> queryParams = RequestUtil.asMap(request, false);
				response.setContentType("text/html;charset=utf-8");
				String actorId = queryParams.get("actorId") == null ? "" : queryParams.get("actorId").toString();
				if(actorId.equals("")){
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "访问参数有误");
					json.put("data", "[]");
					response.getWriter().write(json.toString());
					return null;
				}
				queryParams.put("actorId", actorId);
				try {
							 List<Map<String, Object>>  list = albumservice.tgetAlbuminfobyactor(queryParams);
								if(list.size()>0){
									json = new JSONObject();
									json.put("code", 1);
									json.put("message", "成功");
									json.put("data", list);
								}else{
									json = new JSONObject();
									json.put("code", 1);
									json.put("message", "无数据");
									json.put("data", "[]");
								}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("访问出错:通过演员id查询所有相关专辑  tgetActorAlbuminfo?"+queryParams);
					json = new JSONObject();
					json.put("code", -1);
					json.put("message", "访问出错,请稍后访问！");
					json.put("data", "[]");
				}
				response.getWriter().write(json.toString());
				return null;
			}
		
		//用户收藏预约
		@RequestMapping("/findtalbumforuser")
		public String findpalbumforuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();		
		Map<String, Object> map = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");        
		String seriesCode=map.get("seriesCode")==null?"":map.get("seriesCode").toString();
					if("".equals(seriesCode)){
						json = new JSONObject();
						json.put("code", 0);
						json.put("message", "参数有误");
						response.getWriter().write(json.toString());
						return null;
					}
		try {       
			        int code=Integer.parseInt(seriesCode);
			        map.put("code", code);
					Map<String, Object> obj = albumservice.findtalbumforuser(map);
						if(obj!=null){
							json = new JSONObject();
							json.put("code", 1);
							json.put("message", "成功");
							json.put("data", obj);
						}else{
							json = new JSONObject();
							json.put("code", 0);
							json.put("message", "无数据");
							json.put("data", "{}");
						}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:用户收藏预约  findtalbumforuser?"+map);
			json = new JSONObject();
			json.put("code", 0);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

}
