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

import com.pbtd.vodinterface.config.RedisInterfaceBeanConfig;
import com.pbtd.vodinterface.util.RedisService;
import com.pbtd.vodinterface.util.RequestUtil;
import com.pbtd.vodinterface.web.service.face.IAlbumService;
import com.pbtd.vodinterface.web.service.face.ICommonService;

import ch.qos.logback.classic.Logger;
import net.sf.json.JSONObject;

/**
 * 节目相关接口
 * 
 * @author zr
 */
@Controller
@RequestMapping("/phone/vod")
public class AlbumController {
	@Autowired
	private IAlbumService albumservice;
	@Autowired
	private RedisService redisService;
	@Autowired
	private RedisInterfaceBeanConfig redisInterfaceBeanConfig;
	@Autowired
	private ICommonService commonservice;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(AlbumController.class);
	// 所有接口条件 默认参数 limit pagesize

	// 专辑列表 首页 （根据频道的二级栏目查询） 专辑列表 条件 频道id(必填)
	@RequestMapping(value = "/getChannel2Album")
	public String pgetchannel2Album(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		if (channel.equals("")) {
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "[]");
			response.getWriter().write(json.toString());
			return null;
		}
		String name = queryParams.get("name") == null ? "" : queryParams.get("name").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		List<Map<String, Object>> maplist = new ArrayList<>();// 拼接返回数据
		queryParams.put("status", 1);
		queryParams.put("name", name);
		queryParams.put("parentCode", channel);
		queryParams.put("levels", 2);
		try {
			List<Map<String, Object>> channel2list = commonservice.findChannel(queryParams); // 获取频道下的二级栏目
			if (channel2list.size() > 0) {
				for (Map<String, Object> map : channel2list) {
					Map<String, Object> channel2map = new HashMap<>();
					// map 二级栏目对象 得到模板类型va方法
					String count = map.get("count") == null ? "6" : map.get("count").toString();
					int count1 = Integer.parseInt(count);
					if (limit < 1) {
						limit = 1;
					}
					queryParams.put("limit", (limit - 1) * count1);
					queryParams.put("pagesize", count1);
					String channelCode = map.get("channelCode") == null ? "" : map.get("channelCode").toString();
					queryParams.put("channel_albuminfo", channelCode);
					List<Map<String, Object>> list = albumservice.pgetchannel2Album(queryParams);
					channel2map.put("channel", map.get("parentCode"));
					channel2map.put("channelName", map.get("parentName"));
					channel2map.put("title", map.get("channelCode"));
					channel2map.put("titleName", map.get("channelName"));
					channel2map.put("type", map.get("type"));
					channel2map.put("label", map.get("labels"));
					channel2map.put("isshow_left", map.get("isshow_left"));
					channel2map.put("isshow_right", map.get("isshow_right"));
					channel2map.put("data", list); 
					maplist.add(channel2map);
				}

				if (maplist.size() > 0) {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "成功");
					json.put("data", maplist);
				} else {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "无数据");
					json.put("data", "[]");
				}
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 频道二级栏目专辑列表pgetchannel2Album?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 二级栏目 换一批
	@RequestMapping(value = "/getChannel2AlbumPage")
	public String pgetchannel2Albumpage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		// 频道
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		// 二级栏目
		String channelnext = queryParams.get("channelnext") == null ? "" : queryParams.get("channelnext").toString();
		if (channel.equals("") || channelnext.equals("")) {
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
			List<Map<String, Object>> channel2list = commonservice.findChannel(queryParams); // 获取频道下的二级栏目
			queryParams.put("parentCode", "");
			queryParams.put("channelCode", "");
			if (channel2list.size() > 0) {
				Map<String, Object> channel2map = new HashMap<>();
				for (Map<String, Object> map : channel2list) {
					String count = map.get("count") == null ? "6" : map.get("count").toString();
					int count1 = Integer.parseInt(count);
					if (limit < 1) {
						limit = 1;
					}
					queryParams.put("limit", (limit - 1) * count1);
					queryParams.put("pagesize", count1);
					queryParams.put("channel_albuminfo", channelnext);
					List<Map<String, Object>> list = albumservice.pgetchannel2Album(queryParams);
					double totalAlbum = albumservice.findchannel2Albumcount(queryParams);// 查询专辑总条数
					double totalPage = Math.ceil(totalAlbum / count1);// 专辑总页数
					int condition = 1;
					if (limit > totalPage) {
						condition = 0;
					}
					channel2map.put("totalPage", (int) totalPage);
					channel2map.put("condition", condition);
					channel2map.put("channel", map.get("parentCode"));
					channel2map.put("channelName", map.get("parentName"));
					channel2map.put("title", map.get("channelCode"));
					channel2map.put("titleName", map.get("channelName"));
					channel2map.put("type", map.get("type"));
					channel2map.put("data", list);
				}

				if (channel2map.size() > 0) {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "成功");
					json.put("data", channel2map);
				} else {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "无数据");
					json.put("data", "{}");
				}
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "{}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 二级栏目换一批专辑列表 pgetChannel2AlbumPage?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 推荐页 根据频道查询专辑列表
	@RequestMapping(value = "/getChannelAlbum")
	public String pgetchannelAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		List<Map<String, Object>> maplist = new ArrayList<>();// 拼接返回数据
		queryParams.put("levels", 1);
		try {
			List<Map<String, Object>> channellist = commonservice.findChannel(queryParams); // 获取频道
			if (channellist.size() > 0) {			
				for (Map<String, Object> map : channellist) {
					Map<String, Object> queryParams1 = new HashMap<>();
					Map<String, Object> channel2map = new HashMap<>();
					String count = map.get("count") == null ? "6" : map.get("count").toString();
					int count1 = Integer.parseInt(count);
					if (limit < 1) {
						limit = 1;
					}
					queryParams1.put("limit", (limit - 1) * count1);
					queryParams1.put("pagesize", count1);
					String channelCode = map.get("channelCode") == null ? "" : map.get("channelCode").toString();
				    queryParams1.put("channel", channelCode);
					List<Map<String, Object>> list = albumservice.pgetchannelAlbum(queryParams1);
					channel2map.put("channel", channelCode);
					channel2map.put("channelName", map.get("channelName"));
					channel2map.put("type", map.get("type"));
					channel2map.put("data", list);
					maplist.add(channel2map);
				}

				if (maplist.size() > 0) {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "成功");
					json.put("data", maplist);
				} else {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "无数据");
					json.put("data", "[]");
				}
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 推荐页 根据频道查询专辑列表 pgetChannelAlbum?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 推荐页 根据频道查询专辑列表 换一批
	@RequestMapping(value = "/getChannelAlbumPage")
	public String pgetchannelAlbumpage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		// 频道
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		if (channel.equals("")) {
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
		queryParams.put("levels", 1);
		queryParams.put("channelCode", channel);
		try {
			List<Map<String, Object>> channellist = commonservice.findChannel(queryParams); // 获取频道
			if (channellist.size() > 0) {
				Map<String,Object> queryParams1=new HashMap<>();
				Map<String, Object> channel2map = new HashMap<>();
				for (Map<String, Object> map : channellist) {
					String count = map.get("count") == null ? "6" : map.get("count").toString();
					int count1 = Integer.parseInt(count);
					if (limit < 1) {
						limit = 1;
					}
					queryParams1.put("limit", (limit - 1) * count1);
					queryParams1.put("pagesize", count1);
					String channelCode = map.get("channelCode") == null ? "" : map.get("channelCode").toString();
					queryParams1.put("channel", channelCode);
					List<Map<String, Object>> list = albumservice.pgetchannelAlbum(queryParams1);
					double totalAlbum = albumservice.findchannelAlbumcount(queryParams1);// 查询专辑总条数
					double totalPage = Math.ceil(totalAlbum / count1);// 专辑总页数
					int condition = 1;
					if (limit > totalPage) {
						condition = 0;
					}
					channel2map.put("totalPage", (int) totalPage);
					channel2map.put("condition", condition);
					channel2map.put("channel", channel);
					channel2map.put("channelName", map.get("channelName"));
					channel2map.put("type", map.get("type"));
					channel2map.put("data", list);
				}
				if (channel2map.size() > 0) {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "成功");
					json.put("data", channel2map);
				} else {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "无数据");
					json.put("data", "{}");
				}
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "{}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 推荐页 根据频道查询专辑列表 换一批 pgetChannelAlbumPage?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 频道内标签 根据频道标签查询专辑列表
	@RequestMapping(value = "/getLabelAlbum")
	public String pgetlabelAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "21" : queryParams.get("pageSize").toString();
		String channel = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		if (channel.equals("")) {
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "[]");
			response.getWriter().write(json.toString());
			return null;
		}
		String label1 = queryParams.get("label1") == null ? "" : queryParams.get("label1").toString();
		String label2 = queryParams.get("label2") == null ? "" : queryParams.get("label2").toString();
		String label3 = queryParams.get("label3") == null ? "" : queryParams.get("label3").toString();
		String label4 = queryParams.get("label4") == null ? "" : queryParams.get("label4").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		queryParams.put("status", 1);
		queryParams.put("levels", 1);
		queryParams.put("channelCode", channel);
		try {
			List<Map<String, Object>> channellist = commonservice.findChannel(queryParams); // 获取频道
			if (channellist.size() > 0) {
				Map<String, Object> map = channellist.get(0);
				Map<String, Object> queryParams1 = new HashMap<>();
					queryParams1.put("channel", channel);
					queryParams1.put("label1", label1);
					queryParams1.put("label2", label2);
					queryParams1.put("label3", label3);
					queryParams1.put("label4", label4);
				    queryParams1.put("status", 1);
				// 获取条件
				if (limit < 1) {
					limit = 1;
				}
				queryParams1.put("limit", (limit - 1) * pagesize);
				queryParams1.put("pagesize", pagesize);
				List<Map<String, Object>> list = albumservice.pgetchannelAlbum(queryParams1);
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

			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 频道内标签  根据频道标签查询专辑列表  pgetLabelAlbum?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 专辑详情 条件 专辑id(必填) （角标pic，付费包）
	@RequestMapping(value = "/getAlbuminfo")
	public String pgetAlbuminfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String seriesCode = queryParams.get("seriesCode") == null ? "" : queryParams.get("seriesCode").toString();
		if (seriesCode.equals("")) {
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "{}");
			response.getWriter().write(json.toString());
			return null;
		}
		queryParams.put("seriesCode", seriesCode);
		try {

			Map<String, Object> map = albumservice.pgetAlbuminfo(queryParams);
			if (map != null) {
				json = new JSONObject();
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
			logger.info("访问出错:专辑详情 pgetAlbuminfo?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 专辑关联推荐 专辑id(必填)
	@RequestMapping(value = "/getAlbuminfoRecommend") // ----------------------------------------------------------------------------------------------
	public String pgetAlbuminforecommend(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			 redisAlbum = redisService.get("p"+seriesCode);
		} catch (Exception e) {
			redisAlbum = "";
			logger.error("Redis取数据失败phone:"+e);
		}
		try {
			if (redisAlbum != null && !"".equals(redisAlbum)) {

				response.getWriter().write(redisAlbum);
				return null;
			} else {
				queryParams.put("seriesCode", seriesCode);
				List<Map<String, Object>> list = albumservice.pgetAlbuminforecommend(queryParams);
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
					redisService.setEx("p"+seriesCode, Long.parseLong(redisInterfaceBeanConfig.redis_second),json.toString());
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

	// 剧集列表 条件 专辑id(必填)
	@RequestMapping(value = "/getAlbuminfoVideo")
	public String pgetAlbuminfovideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		queryParams.put("seriesCode", seriesCode);
		try {
			List<Map<String, Object>> listmovieurl = albumservice.pfindmovieurl(queryParams);//查询返回播放地址字段	
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
			List<Map<String, Object>> list = albumservice.pgetAlbuminfovideo(queryParams);
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
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:剧集列表  pgetAlbuminfoVideo?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 播 放详情 条件 专辑id(必填)
	@RequestMapping(value = "/getAlbumDetail")
	public String pgetAlbumDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "500" : queryParams.get("pageSize").toString();
		String seriesCode = queryParams.get("seriesCode") == null ? "" : queryParams.get("seriesCode").toString();
		int limit = Integer.parseInt(limitstring);
		int pagesize = Integer.parseInt(pagesizestring);
		if (seriesCode.equals("")) {
			json = new JSONObject();
			json.put("code", 1);
			json.put("message", "访问参数有误");
			json.put("data", "[]");
			response.getWriter().write(json.toString());
			return null;
		}
		// 获取条件
		if (limit < 1) {
			limit = 1;
		}
		queryParams.put("limit", (limit - 1) * pagesize);
		queryParams.put("pagesize", pagesize);
		queryParams.put("seriesCode", seriesCode);
		try {
		//	int count = albumservice.findAlbuminfovideocount(queryParams);
			Map<String, Object> album = albumservice.pgetAlbuminfo(queryParams);
			Map<String, Object> map = new HashMap<>();
			
			//-------------------------------------------------------------------------------------
			List<Map<String, Object>> listmovieurl = albumservice.pfindmovieurl(queryParams);//查询返回播放地址字段	
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
			List<Map<String, Object>> list = albumservice.pgetAlbuminfovideo(queryParams);
			if (album != null) {
					album.put("count", list.size());
                	album.put("pageSize", pagesize);
					map.put("album", album);
					
				}
				
			if (list.size() > 0 && album != null) {
				int i=1;
				for (Map<String, Object> mapv : list) {
					String m1=mapv.get(name1)==null?"":mapv.get(name1).toString();
					String m2=mapv.get(name2)==null?"":mapv.get(name2).toString();
					String m3=mapv.get(name3)==null?"":mapv.get(name3).toString();
					if(!m1.equals("")){
						mapv.put("movieUrl", m1);						
					}else if(!m2.equals("")){
						mapv.put("movieUrl", m2);
					}else if(!m3.equals("")){
						mapv.put("movieUrl", m3);
					}else{
						mapv.put("movieUrl", "");
					}
					int seq=i++;
					mapv.put("seq",seq);
					mapv.put("channel", album.get("Channel"));
					mapv.put("seriesCode",album.get("seriesCode"));
				}
				map.put("video", list);
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", map);
			} else {
				json = new JSONObject();
				json.put("code", 0);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:剧集列表  pgetAlbuminfoVideo?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 热搜vod_phone_hotsearch
	@RequestMapping(value = "/getAlbumHotSearch")
	public String pgetAlbumhotsearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "10" : queryParams.get("pageSize").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		if (limit < 1) {
			limit = 1;
		}
		queryParams.put("limit", (limit - 1) * pagesize);
		queryParams.put("pagesize", pagesize);
		try {
			List<Map<String, Object>> list = albumservice.pgetAlbumhotsearch(queryParams);
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
			logger.info("访问出错:热搜  pgetAlbumHotSearch?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 热播vod_phone_hotseries
	@RequestMapping(value = "/getAlbumHotSeries")
	public String pgetAlbumhotseries(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("pageNo") == null ? "0" : queryParams.get("pageNo").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "10" : queryParams.get("pageSize").toString();
		String channelstring = queryParams.get("channel") == null ? "" : queryParams.get("channel").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		if (!"".equals(channelstring)) {
			queryParams.put("channel", Integer.parseInt(channelstring));
		}
		if (limit < 1) {
			limit = 1;
		}
		queryParams.put("limit", (limit - 1) * pagesize);
		queryParams.put("pagesize", pagesize);
		try {
			List<Map<String, Object>> counts = albumservice.findcount(queryParams);// 查询显示返回条数
			int count = 6;
			if (counts.size() > 0) {
				String countstring = counts.get(0).get("count").toString();
				count = Integer.parseInt(countstring);
			}
			if (limit < 1) {
				limit = 1;
			}
			queryParams.put("limit", (limit - 1) * count);
			queryParams.put("pagesize", count);
			List<Map<String, Object>> list = albumservice.findhotseriescode(queryParams);
			double totalAlbum = albumservice.findhotseriescount(queryParams);// 查询专辑总条数
			double totalPage = Math.ceil(totalAlbum / count);// 专辑总页数
			if (list.size() > 0) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", list);
				json.put("totalPage", (int) totalPage);
			} else {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:热播推荐 pgetAlbumHotSeries?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	// 用户收藏预约对接接口
	@RequestMapping("/findpalbumforuser")
	public String findpalbumforuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> map = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String seriesCode = map.get("seriesCode") == null ? "" : map.get("seriesCode").toString();
		if ("".equals(seriesCode)) {
			json = new JSONObject();
			json.put("code", 0);
			json.put("message", "参数有误");
			response.getWriter().write(json.toString());
			return null;
		}
		try {
			int code = Integer.parseInt(seriesCode);
			map.put("code", code);
			Map<String, Object> obj = albumservice.findpalbumforuser(map);
			if (obj != null) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", obj);
			} else {
				json = new JSONObject();
				json.put("code", 0);
				json.put("message", "无数据");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错:用户收藏预约  findpalbumforuser?" + map);
			json = new JSONObject();
			json.put("code", 0);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "{}");
		}
		response.getWriter().write(json.toString());
		return null;
	}
 
	
	//优酷使用 搜索
	@RequestMapping(value = "/search")
	public String search(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String limitstring = queryParams.get("page") == null ? "1" : queryParams.get("page").toString();
		String name = queryParams.get("queryString") == null ? "" : queryParams.get("queryString").toString();
		String channelName = queryParams.get("channelName") == null ? "" : queryParams.get("channelName").toString();
		String pagesizestring = queryParams.get("pageSize") == null ? "15" : queryParams.get("pageSize").toString();
		int pagesize = Integer.parseInt(pagesizestring);
		int limit = Integer.parseInt(limitstring);
		try {
			queryParams.put("name", name);
			queryParams.put("channelName", channelName);
			queryParams.put("pagesize", pagesize);
			queryParams.put("limit", (limit - 1) * pagesize);
			queryParams.put("pagesize", pagesize);
			int pagecount=albumservice.findsearchalbumcount(queryParams);
			if (pagecount> 0) {			
				List<Map<String, Object>> list = albumservice.findsearchalbum(queryParams);
				Map<String, Object> map = new HashMap<>();// 拼接返回数据
				   map.put("itemList", list);
				   map.put("page", limit);
				   map.put("pageCount", Math.ceil(pagecount/pagesize)<=1?1:Math.ceil(pagecount/pagesize));
				   map.put("queryString", name);
				   map.put("recordCount", pagecount);
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
			logger.info("访问出错: 推荐页 根据频道查询专辑列表 pgetChannelAlbum?" + queryParams);
			json = new JSONObject();
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", "[]");
		}
		response.getWriter().write(json.toString());
		return null;
	}
	
	
		// 专辑推送权限 条件 专辑id(必填)
		//@RequestMapping(value = "/getAlbuminfAuth")
		public String pgetAlbuminfoAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject json = new JSONObject();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			response.setContentType("text/html;charset=utf-8");
			String seriesCode = queryParams.get("seriesCode") == null ? "" : queryParams.get("seriesCode").toString();
			if (seriesCode.equals("")) {
				json = new JSONObject();
				json.put("code", 1);
				json.put("message", "访问参数有误");
				json.put("data", "{}");
				response.getWriter().write(json.toString());
				return null;
			}
			queryParams.put("seriesCode", seriesCode);
			try {

				Map<String, Object> map = albumservice.pgetAlbuminfo(queryParams);
				if (map != null) {
					Map<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("seriesCode", map.get("seriesCode"));
					hashMap.put("cpCode", map.get("cpCode"));
					hashMap.put("cpName", map.get("cpName"));
					hashMap.put("cpseriescode", map.get("cpseriescode"));
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "成功");
					json.put("data", hashMap);
				} else {
					json = new JSONObject();
					json.put("code", 1);
					json.put("message", "无数据");
					json.put("data", "{}");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("访问出错:专辑详情 pgetAlbuminfo?" + queryParams);
				json = new JSONObject();
				json.put("code", -1);
				json.put("message", "访问出错,请稍后访问！");
				json.put("data", "{}");
			}
			response.getWriter().write(json.toString());
			return null;
		}
	
}
