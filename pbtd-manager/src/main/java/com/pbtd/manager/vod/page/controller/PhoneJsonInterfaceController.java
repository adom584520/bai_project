package com.pbtd.manager.vod.page.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.vod.buss.domain.Vodbussinfo;
import com.pbtd.manager.vod.buss.service.face.IVodbussinfoService;
import com.pbtd.manager.vod.common.actors.domain.Vodactors;
import com.pbtd.manager.vod.common.actors.service.face.IVodactorsService;
import com.pbtd.manager.vod.common.corner.domain.VodCollectfeesbag;
import com.pbtd.manager.vod.common.corner.domain.VodCorner;
import com.pbtd.manager.vod.common.corner.service.face.IVodCollectfeesbagService;
import com.pbtd.manager.vod.common.corner.service.face.IVodCornerService;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.phone.album.service.face.IVodAlbuminfoService;
import com.pbtd.manager.vod.phone.common.domain.VodLabeltype;
import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.common.domain.Vodlabel;
import com.pbtd.manager.vod.phone.common.service.face.IVodChannelService;
import com.pbtd.manager.vod.phone.common.service.face.IVodLabelService;
import com.pbtd.manager.vod.phone.common.service.face.IVodLabeltypeService;
import com.pbtd.manager.vod.phone.hotsearch.service.face.IVodHotSearchService;
import com.pbtd.manager.vod.phone.hotseries.service.IVodHotseriesService;
import com.pbtd.manager.vod.phone.slideshow.domain.StartSlideshow;
import com.pbtd.manager.vod.phone.slideshow.query.SlideshowQueryObject;
import com.pbtd.manager.vod.phone.slideshow.query.StartSlideshowQueryObject;
import com.pbtd.manager.vod.phone.slideshow.service.SlideshowService;
import com.pbtd.manager.vod.phone.slideshow.service.StartSlideshowService;
import com.pbtd.manager.vod.phone.special.domain.VodSpecial;
import com.pbtd.manager.vod.phone.special.service.face.IVodSpecialService;
import com.pbtd.manager.vod.system.domain.Cpsource;
import com.pbtd.manager.vod.system.domain.Textrecommendation;
import com.pbtd.manager.vod.system.service.face.ICpsourceService;
import com.pbtd.manager.vod.system.service.face.ITextrecommendationService;
import com.pbtd.manager.vod.system.service.face.RecommandPicService;

import net.sf.json.JSONObject;

@Controller
@PropertySource(value = { "classpath:config/phoneJsonInterface.properties" })
@RequestMapping("/integrate/outputjson/phone")
public class PhoneJsonInterfaceController {
	public static Logger log = Logger.getLogger(PhoneJsonInterfaceController.class);
	@Value("${phone_albumUrl}")
	private String phonealbumUrl;
	@Value("${phone_channelUrl}")
	private String phonechannelUrl;
	@Value("${phone_labelUrl}")
	private String phonelabelUrl;
	@Value("${phone_actorUrl}")
	private String phoneactorUrl;
	@Value("${phone_cornerUrl}")
	private String phonecornerUrl;
	@Value("${phone_collectfeesbagUrl}")
	private String phonecollectfeesbagUrl;
	@Value("${phone_hotsearchUrl}")
	private String phonehotsearchUrl;
	@Value("${phone_specialUrl}")
	private String phonespecialUrl;
	@Value("${phone_recommandpicUrl}")
	private String phonerecommandpicUrl;
	@Value("${phone_slideshowUrl}")
	private String phoneslideshowUrl;
	@Value("${phone_textrecommendationUrl}")
	private String phonetextrecommendationUrl;
	@Value("${phone_startshowUrl}")
	private String phonestartshowUrl;
	@Value("${phone_hotseriesUrl}")
	private String phonehotseriesUrl;
	@Value("${phone_labelchannelUrl}")
	private String phonelabelchannelUrl;
	@Value("${phone_labeltypeUrl}")
	private  String phonelabeltypeUrl;
	@Value("${phone_cpsourceUrl}")
	private  String phonecpsourceUrl;

	@Autowired
	private IVodbussinfoService vodbussinfoservice;
	@Autowired
	private IVodAlbuminfoService vodAlbuminfoService;
	@Autowired
	private IVodChannelService vodChannelService;
	@Autowired
	private IVodLabelService vodLabelService;
	@Autowired
	private IVodactorsService vodactorsService;
	@Autowired
	private IVodCollectfeesbagService vodcollectfeesService;
	@Autowired
	private IVodCornerService vodcornerService;
	@Autowired
	private IVodHotSearchService vodHotSearchService;
	@Autowired
	private IVodSpecialService vodSpecialService;
	@Autowired
	private RecommandPicService recommandService;
	@Autowired
	private ITextrecommendationService textrecommendationService;
	@Autowired
	private IVodHotseriesService vodHotseriesService;
	@Autowired
	private SlideshowService slideshowService;
	@Autowired
	private StartSlideshowService startSlideshowService;
    @Autowired
	private IVodLabeltypeService vodlabeltypeService;
    @Autowired
    private ICpsourceService cpsourceService;
	/**
	 * 分平台专辑下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonealbumip")
	public List<Map<String, Object>> getUrl(HttpServletRequest request) {
		// 当前日期
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		log.warn("下发专辑更新数据开始");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String time = "";
		if (request == null) {
			time = dateNowStr;
		} else {
			time = request.getParameter("curtime");
		}
		final String curtime = time.substring(0, 10);
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonealbumUrl + "?" + "curtime=" + curtime;
							notice(url);
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
		return null;

	}

	/**
	 * 分平台选中专辑下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phoneAlbumIp/{ids}")
	public List<Map<String, Object>> getUrlx(@PathVariable("ids")  String ids, HttpServletRequest request) {

		log.warn("下发专辑更新数据开始");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		log.warn(ids +"...................");
		if (ids != null && !",".equals(ids) && !"".equals(ids)) {
			queryParams.put("status", 1);
			List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
			for (Vodbussinfo m : list) {
				final String address = m.getAddress();
				final String idz = ids;
				try {
					Thread hth = new Thread() {
						@Override
						public void run() {
							try {
								// 手动下发 到运营库接口
								String url = address + phonealbumUrl + "?" + "ids=" + idz;
								notice(url);
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
		}
		return null;

	}

	/**
	 * 获取下发专辑信息接口
	 * 
	 * @param curtime
	 *            日期 & limit 页数
	 * @return 专辑详情list
	 */
	@ResponseBody
	@RequestMapping("/getAlbum")
	public String getAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		//按照时间
		String curtime = request.getParameter("curtime");
		//按照选中
		String ids = request.getParameter("ids");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();//项目名称
		String offset=request.getParameter("offset")==null?"1":request.getParameter("offset").toString();//offset
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("proj", proj);
			queryParams.put("status", 1);
		  if (ids != null && !",".equals(ids) && !"".equals(ids)) {
				queryParams.put("seriesCode_", ids.split(","));
			}else{
				queryParams.put("offset", Integer.parseInt(offset));
			}
			double total = vodAlbuminfoService.count(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List<Vodalbuminfo> list = vodAlbuminfoService.page(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 获取下发剧集接口
	 * 
	 * @param curtime
	 *            日期 & limit 页数
	 * @return 剧集list
	 */
	@ResponseBody
	@RequestMapping("/getalbumvideo")
	public String getAlbumVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		int seriesCode = request.getParameter("seriesCode")==null?0:Integer.parseInt(request.getParameter("seriesCode"));
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String offset=request.getParameter("offset")==null?"":request.getParameter("offset").toString();//offset
		try {
			int limit = Integer.parseInt(size);
			//queryParams.put("create_time", curtime);
			if(offset!="" && offset!=null){
				queryParams.put("offset",Integer.parseInt( offset));
			}else{
				queryParams.put("seriesCode", seriesCode);
			}
			double total = vodAlbuminfoService.countAlbumsinfovideo(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());

				List<Map<String, Object>> list = vodAlbuminfoService.pageAlbumsinfovideo(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 获取下发关联推荐接口
	 * 
	 * @param curtime
	 *            日期 & limit 页数
	 * @return 关联推荐剧集list
	 */
	@ResponseBody
	@RequestMapping("/getrecommandalbum")
	public String getrecommandalbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime") == null ? "2017-11-27"
				: request.getParameter("curtime").toString();
		int id = Integer.parseInt(request.getParameter("seriesCode"));
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("id", id);
			queryParams.put("status", 1);
			double total = vodAlbuminfoService.countalbum(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());

				List<Map<String, Object>> list = vodAlbuminfoService.recommandalbum(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 分平台频道下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonechannelip")
	public List<Map<String, Object>> getUrl2(HttpServletRequest request) {
		log.warn("下发频道更新数据开始");
		String time = request.getParameter("curtime")==null?"":request.getParameter("curtime").substring(0,10);
		final String channelcode = request.getParameter("ids")==null?"":request.getParameter("ids").toString();
		final String curtime = time;
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonechannelUrl + "?curtime=" + curtime+"&channelcode="+channelcode;
							log.info("中心平台频道下发访问分平台接口："+url);
							notice(url);
						} catch (IllegalArgumentException ec) {
							log.info("中心平台频道下发访问分平台接口抛出异常："+ec);
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
		return null;

	}

	/**
	 * 获取下发频道接口 param curtime 日期 & limit 页数
	 * 
	 * @return 频道list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getchannel")
	public String getChannel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime")==null?"": request.getParameter("curtime").toString();
		String channelcode = request.getParameter("channelcode")==null?"":request.getParameter("channelcode");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {

			if(channelcode!=""){
				queryParams.put("ids", channelcode);
			}else{
				queryParams.put("create_time", curtime);
			}
			int limit = Integer.parseInt(size);
			queryParams.put("status", 1);
			double total = vodChannelService.count(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List<Vodchannel> list = vodChannelService.page(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 获取下发频道绑定的专辑接口 param curtime 日期 & limit 页数
	 * 
	 * @return 频道list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getchannelalbum")
	public String getchannelalbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String channelCode = request.getParameter("channelCode");
		String curtime = request.getParameter("curtime") == null ? "2017-11-27"
				: request.getParameter("curtime").toString();
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("channelCode", channelCode);
			queryParams.put("status", 1);
			double total = vodChannelService.countalbum(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List<Map<String, Object>> list = vodChannelService.channelalbum(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	
	/**
	 * 获取下发频道绑定的标签接口 param curtime 日期 & limit 页数
	 * @return 频道list
	 */
	@ResponseBody
	@RequestMapping("/getchannellabel")
	public String getchannellabel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String channelCode = request.getParameter("channelCode");
		String curtime = request.getParameter("curtime") == null ? "2017-11-27"
				: request.getParameter("curtime").toString();
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("channel", channelCode);
			queryParams.put("status", 1);
			double total = vodChannelService.countlabelforchannel(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List<Map<String, Object>> list = vodChannelService.pagelabelforchannel(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	
	
	/**
	 * 分平台标签下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonelabelip")
	public List<Map<String, Object>> getUrl3(HttpServletRequest request) {
		log.warn("下发标签更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonelabelUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}

	/**
	 * 下发标签绑定频道
	 * phonelabelchannelUrl
	 */
	@ResponseBody
	@RequestMapping("/phonelabelchannel")
	public List<Map<String, Object>> phonelabelchannel(HttpServletRequest request) {
		log.warn("下发标签频道更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonelabelchannelUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}

	/**
	 * 获取下发标签接口 param curtime 日期 & limit 页数
	 * 
	 * @return 标签list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getlabel")
	public String getlabel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			double total = vodLabelService.count(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List<Vodlabel> list = vodLabelService.page(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	/**
	 * 获取下发标签绑定频道接口 param curtime 日期 & limit 页数
	 * @return 标签list
	 */
	@ResponseBody
	@RequestMapping("/getlabelchannel")
	public String getlabelchannel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String label = request.getParameter("label");
		queryParams.put("label", label);
		try {
			List<Map<String, Object>> list = vodLabelService.pagelabelchannel(queryParams);
			if (list.size() == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				json.accumulate("code", 1);
				json.accumulate("data", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	/**
	 * 分平台演员下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phoneactorip")
	public List<Map<String, Object>> getUrl4(HttpServletRequest request) {
		log.warn("下发演员更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phoneactorUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}

	/**
	 * 获取下发演员接口 param curtime 日期 & limit 页数
	 * 
	 * @return 演员list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getactor")
	public String getactor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		queryParams.put("updatetime", curtime);
		queryParams.put("status", 1);
		double total = vodactorsService.count(queryParams);
		try {
			int limit = Integer.parseInt(size);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List<Vodactors> list = vodactorsService.page(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 分平台角标下发通知接口
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonecornerip")
	public List<Map<String, Object>> getUrl5(HttpServletRequest request) {
		log.warn("下发频道更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonecornerUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;
	}

	/**
	 * 获取下发角标接口 param curtime 日期 & limit 页数
	 * 
	 * @return 角标list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getcorner")
	public String getcorner(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			double total = vodcornerService.count(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List<VodCorner> list = vodcornerService.page(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 分平台付费包下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonecollectfeesbagip")
	public List<Map<String, Object>> getUrl6(HttpServletRequest request) {
		log.warn("下发频道更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonecollectfeesbagUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}

	/**
	 * 获取下发付费包接口 param curtime 日期 & limit 页数
	 * 
	 * @return 付费包list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getcollectfeesbag")
	public String getcollectfeesbag(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			double total = vodcollectfeesService.count(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List<VodCollectfeesbag> list = vodcollectfeesService.page(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 分平台热搜下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonehotsearchip")
	public List<Map<String, Object>> getUrl7(HttpServletRequest request) {
		log.warn("下发热搜更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonehotsearchUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}

	/**
	 * 获取下发热搜接口
	 * 
	 * @return 热搜list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/gethotsearch")
	public String gethotsearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String curtime = request.getParameter("curtime") == null ? "2017-11-24"
				: request.getParameter("curtime").toString();
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		try {
			int limit = Integer.parseInt(size);
			double total = vodHotSearchService.count(queryParams);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("create_time", curtime);
				queryParams.put("status", 1);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());

				List<Map<String, Object>> list = vodHotSearchService.find(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 分平台专题下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonespecialip")
	public List<Map<String, Object>> getUrl8(HttpServletRequest request) {
		log.warn("下发频道更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonespecialUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}

	/**
	 * 获取下发专题接口 param curtime 日期 & limit 页数
	 * 
	 * @return 专题list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getspecial")
	public String getspecial(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		List<VodSpecial> list = new ArrayList<>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			double total = vodSpecialService.count(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				list = null;
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				list = vodSpecialService.page(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 获取下发专题绑定专辑接口 param curtime 日期 & limit 页数
	 * 
	 * @return 专题绑定list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getspecialvideo")
	public String getspecialvideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		String curtime = request.getParameter("curtime") == null ? "2017-11-24"
				: request.getParameter("curtime").toString();
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		int special_id = Integer.parseInt(request.getParameter("special_id"));
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("special_id", special_id);
		try {
			int limit = Integer.parseInt(size);
			double total = vodSpecialService.countalbum(queryParams);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				int numsum = (int) Math.ceil(total / 100);
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());

				List<Map<String, Object>> list = vodSpecialService.findpagealbum(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}



	/**
	 * 分平台频道轮播图下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonerecommandpicip")
	public List<Map<String, Object>> getUrl9(HttpServletRequest request) {
		log.warn("下发频道轮播图更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonerecommandpicUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}
	
	/**
	 * 获取下发频道轮播图接口 param curtime 日期 & limit 页数
	 * 
	 * @return 专题list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getrecommandpic")
	public String getrecommandpic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("update_time", curtime);
			queryParams.put("status", 1);
			double total = recommandService.count(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				list = null;
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				list = recommandService.page(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	/**
	 * 分平台文字推荐下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonetextrecommendationip")
	public List<Map<String, Object>> getUrl10(HttpServletRequest request) {
		log.warn("下发文字推荐更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonetextrecommendationUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}
	
	/**
	 * 获取下发文字推荐接口 param curtime 日期 & limit 页数
	 * 
	 * @return 专题list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/gettextrecommendation")
	public String gettextrecommendation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		List<Textrecommendation> list = new ArrayList<>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			double total = textrecommendationService.count(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				list = null;
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				list = textrecommendationService.findforinterface(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	
	/**
	 * 分平台热播推荐下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonehotseriesip")
	public List<Map<String, Object>> getUrl11(HttpServletRequest request) {
		log.warn("下发热播推荐更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonehotseriesUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}
	
	/**
	 * 获取下发热播推荐接口 param curtime 日期 & limit 页数
	 * 
	 * @return 专题list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/gethotseries")
	public String gethotseries(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			double total = vodHotseriesService.count(queryParams);
			if (total == 0) {
				list = null;
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				list = vodHotseriesService.findforinterface(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	/**
	 * 分平台专区推荐轮播图下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phoneslideshowip")
	public List<Map<String, Object>> getUrl12(HttpServletRequest request) {
		log.warn("下发热播推荐更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phoneslideshowUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}
	
	/**
	 * 获取下发专区推荐轮播图接口 param curtime 日期 & limit 页数
	 * 
	 * @return 专题list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getslideshow")
	public String getslideshow(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		List<?> list = new ArrayList<>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			SlideshowQueryObject queryObject=new SlideshowQueryObject();
			queryObject.setStatus(1);
			queryObject.setUpdate_time(curtime);
			double total = slideshowService.queryCount(queryObject);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				list = null;
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryObject.setStart(qo.getStart());
				queryObject.setPageSize(qo.getPageSize());
				list =  slideshowService.queryListforinterface(queryObject);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	

	/**
	 * 分平台开机轮播图下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/phonestartshowip")
	public List<Map<String, Object>> getUrl13(HttpServletRequest request) {
		log.warn("下发开机轮播图更新数据开始");
		String time = request.getParameter("curtime");
		final String curtime = time.substring(0, 10);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("status", 1);
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url = address + phonestartshowUrl + "?curtime=" + curtime;
							notice(url);
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
		return null;

	}
	
	/**
	 * 获取下发开机轮播图接口 param curtime 日期 & limit 页数
	 * 
	 * @return 专题list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/getstartshow")
	public String getstartshow(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		List<StartSlideshow> list = new ArrayList<>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			StartSlideshowQueryObject queryObject=new StartSlideshowQueryObject();
			queryObject.setStatus(1);
			queryObject.setUpdate_time(curtime);
			double total = startSlideshowService.queryCount(queryObject);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				list = null;
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryObject.setStart(qo.getStart());
				queryObject.setPageSize(qo.getPageSize());
				list =  startSlideshowService.queryListforinterface(queryObject);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	
	/**
	 * 获取下发热播下绑定的专辑接口 param curtime 日期 & limit 页数&hot_id  热播id
	 * 
	 * @return 专题list
	 * 
	 */
	@ResponseBody
	@RequestMapping("/gethotseriesalbum")
	public String gethotseriesalbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String hot_id=request.getParameter("hot_id")==null?"":request.getParameter("hot_id").toString();
		if("".equals(hot_id)){
			json.accumulate("code", 0);
			response.getWriter().write(json.toString());
			return null;
		}
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			queryParams.put("hot_id", hot_id);
			double total = vodHotseriesService.countalbum(queryParams);
			int numsum = (int) Math.ceil(total / 100);
			if (total == 0) {
				list = null;
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				list = vodHotseriesService.findhotseriesalbum(queryParams);
				json.accumulate("code", 1);
				json.accumulate("data", list);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	
	//通知下发
	protected int notice(String requestUrl) {
		// String requestUrl =central.actors;
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
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = buffer.toString();
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

	
	/**
	 * 标签分类下发
	 */
	    @ResponseBody
		@RequestMapping("/phonelabeltype")
		public List<Map<String, Object>> phonelabeltype(HttpServletRequest request) {
			log.warn("下发 标签分类数据开始");
			String time = request.getParameter("curtime");
			final String curtime = time.substring(0, 10);
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("status", 1);
			List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
			for (Vodbussinfo m : list) {
				final String address = m.getAddress();
				try {
					Thread hth = new Thread() {
						@Override
						public void run() {
							try {
								// 手动下发 到运营库接口
								String url = address + phonelabeltypeUrl + "?curtime=" + curtime;
								notice(url);
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
			return null;

		}
		
		/**
		 * 获取标签分类下发接口param curtime 日期 & limit 页数
		 * 
		 */
		@ResponseBody
		@RequestMapping("/getlabeltype")
		public String getlabeltype(HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setContentType("text/html;charset=utf-8");
			JSONObject json = new JSONObject();
			 List<VodLabeltype> list = new ArrayList<>();
			String curtime = request.getParameter("curtime");
			try {
				Map<String, Object> m=new HashMap<>();
				m.put("update_time", curtime);
				list =  vodlabeltypeService.page(m);
				if (list.size()== 0) {
					list = null;
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				} else {
					json.accumulate("code", 1);
					json.accumulate("data", list);
				}
			} catch (Exception e) {
				e.printStackTrace();
				json.accumulate("code", 0);
			}
			response.getWriter().write(json.toString());
			return null;
		}
		

	
		
		/**
		 * cp源分类下发
		 */
		    @ResponseBody
			@RequestMapping("/phonesourcetype")
			public List<Map<String, Object>> phonesourcetype(HttpServletRequest request) {
				log.warn("下发 cp源分类数据开始");
			  final	String curtime="";
				Map<String, Object> queryParams = new HashMap<String, Object>();
				queryParams.put("status", 1);
				List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
				for (Vodbussinfo m : list) {
					final String address = m.getAddress();
					try {
						Thread hth = new Thread() {
							@Override
							public void run() {
								try {
									// 手动下发 到运营库接口
									String url = address +phonecpsourceUrl + "?curtime=" + curtime;
									notice(url);
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
				return null;

			}
		    
			/**
			 * 获取标签分类下发接口param curtime 日期 & limit 页数
			 * 
			 */
			@ResponseBody
			@RequestMapping("/getsourcetype")
			public String getsourcetype(HttpServletRequest request, HttpServletResponse response) throws IOException {
				response.setContentType("text/html;charset=utf-8");
				JSONObject json = new JSONObject();
				  List<Cpsource> list = new ArrayList<>();
				String curtime = request.getParameter("curtime");
				try {
					Map<String, Object> m=new HashMap<>();
					m.put("update_time", curtime);
					list =  cpsourceService.find(m);
					if (list.size()== 0) {
						list = null;
						json.accumulate("code", 0);
						json.accumulate("data", "{}");
					} else {
						json.accumulate("code", 1);
						json.accumulate("data", list);
					}
				} catch (Exception e) {
					e.printStackTrace();
					json.accumulate("code", 0);
				}
				response.getWriter().write(json.toString());
				return null;
			}
			
		
}
