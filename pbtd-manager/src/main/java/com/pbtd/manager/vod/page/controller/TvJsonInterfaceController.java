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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.vod.buss.domain.Vodbussinfo;
import com.pbtd.manager.vod.buss.service.face.IVodbussinfoService;
import com.pbtd.manager.vod.common.mould.domain.vodMasterplate;
import com.pbtd.manager.vod.common.mould.domain.vodMasterplateSon;
import com.pbtd.manager.vod.common.mould.service.face.IvodMasterplateInterface;
import com.pbtd.manager.vod.common.mould.service.face.IvodMasterplateSonInterface;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.tv.album.domain.VodTvAlbuminfo;
import com.pbtd.manager.vod.tv.album.service.face.IVodTvAlbuminfoService;
import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;
import com.pbtd.manager.vod.tv.common.domain.VodTvlabel;
import com.pbtd.manager.vod.tv.common.service.face.IVodTvChannelService;
import com.pbtd.manager.vod.tv.common.service.face.IVodTvLabelService;
import com.pbtd.manager.vod.tv.special.domain.VodTvSpecial;
import com.pbtd.manager.vod.tv.special.service.face.IVodTvSpecialService;

import net.sf.json.JSONObject;

@Controller
@PropertySource(value = { "classpath:config/tvJsonInterface.properties" })
@RequestMapping("/integrate/outputjson/tv")
public class TvJsonInterfaceController {
	@Value("${tv_albumUrl}")
	private String tvalbumUrl;
	@Value("${tv_channelUrl}")
	private String tvchannelUrl;
	@Value("${tv_labelUrl}")
	private String tvlabelUrl;
	@Value("${tv_specialUrl}")
	private String tvspecialUrl;
	@Value("${tv_moduleUrl}")
	private String tvmoduleUrl; 

	@Autowired
	private IVodbussinfoService vodbussinfoservice;
	@Autowired
	private IVodTvAlbuminfoService vodTvAlbuminfoService;
	@Autowired
	private IVodTvChannelService vodTvChannelService;
	@Autowired
	private IVodTvLabelService vodTvLabelService;
	@Autowired
	private IVodTvSpecialService vodTvSpecialService;
	@Autowired
	private IDictionaryService dictionaryService;
	@Autowired
	private IvodMasterplateInterface vodMasterplateInterface;
	@Autowired
	private IvodMasterplateSonInterface vodMasterplateSonInterface;
	
	 
	/**
	 * 分平台专辑下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/tvalbumip")
	public List<Map<String, Object>> getUrl(HttpServletRequest request) {
		System.out.println("下发专辑更新数据开始");
		//当前日期
		 Date d = new Date();  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	      String dateNowStr = sdf.format(d);  
		Map<String, Object> queryParams = new HashMap<String, Object>();
		 String time="";
			if(request==null){
				   time=dateNowStr;
			}else{
				  time=request.getParameter("curtime");
			}
	    final String curtime=time.substring(0,10);
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
							String url =address +tvalbumUrl+"?"+"curtime="+curtime;
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
	 * 获取下发专辑信息接口
	 * 
	 * @param curtime 日期 & limit 页数
	 * @return 专辑详情list           
	 */
	@ResponseBody
	@RequestMapping("/getAlbum")
	public String getAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			queryParams.put("proj", proj);
		/*	List<Map<String, Object>> busschannel=dictionaryService.findbusschannel(queryParams);
			if(busschannel.size()>0){
			}*/
			queryParams.put("busschannel", proj);
			double total = vodTvAlbuminfoService.count(queryParams);
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
				List<VodTvAlbuminfo> list = vodTvAlbuminfoService.page(queryParams);
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
	 * @param curtime 日期 & limit 页数
	 * @return 剧集list     
	 */
	@ResponseBody
	@RequestMapping("/getalbumvideo")
	public String getAlbumVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime")==null?"2017-11-27":request.getParameter("curtime").toString();
		int seriesCode=Integer.parseInt(request.getParameter("seriesCode"));
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();//项目名称
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("proj", proj);
			queryParams.put("create_time", curtime);
			queryParams.put("seriesCode", seriesCode);
			double total = vodTvAlbuminfoService.countAlbumsinfovideo(queryParams);
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
				
				List<Map<String, Object>> list = vodTvAlbuminfoService.pageAlbumsinfovideo(queryParams);
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
	 * @param curtime 日期 & limit 页数
	 * @return 关联推荐剧集list     
	 */
	@ResponseBody
	@RequestMapping("/getrecommandalbum")
	public String getrecommandalbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime")==null?"2017-11-27":request.getParameter("curtime").toString();
		int id=Integer.parseInt(request.getParameter("seriesCode"));
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();//项目名称
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("proj", proj);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			queryParams.put("id", id);
			double total = vodTvAlbuminfoService.countalbum(queryParams);
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
				
				List<Map<String, Object>> list = vodTvAlbuminfoService.recommandalbum(queryParams);
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
	@RequestMapping("/tvchannelip")
	public List<Map<String, Object>> getUrl2(HttpServletRequest request) {
		System.out.println("下发频道更新数据开始");
		String time = request.getParameter("curtime")==null?"":request.getParameter("curtime").substring(0,10);
		final String curtime=time;
		final String channelcode = request.getParameter("ids")==null?"":request.getParameter("ids").toString();
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
							String url =address +tvchannelUrl+"?curtime="+curtime+"&channelcode="+channelcode;
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
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();
		String curtime = request.getParameter("curtime")==null?"": request.getParameter("curtime").toString();
		String channelcode = request.getParameter("channelcode")==null?"":request.getParameter("channelcode");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			if(channelcode!=""){
				queryParams.put("ids", channelcode);
			}else{
				queryParams.put("create_time", curtime);
			}
			queryParams.put("proj", proj);
			queryParams.put("status", 1);
			/*List<Map<String, Object>> busschannel=dictionaryService.findbusschannel(queryParams);
			if(busschannel.size()>0){
				queryParams.put("busschannel", proj);
			}*/
			queryParams.put("busschannel", proj);
			double total = vodTvChannelService.count(queryParams);
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
				List<VodTvchannel> list = vodTvChannelService.page(queryParams);
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
			double total = vodTvChannelService.countlabelforchannel(queryParams);
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
				List<Map<String, Object>> list = vodTvChannelService.pagelabelforchannel(queryParams);
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
		String channelCode=request.getParameter("channelCode");
		String curtime = request.getParameter("curtime")==null?"2017-11-27":request.getParameter("curtime").toString();
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();//项目名称
		try {
			int limit = Integer.parseInt(size);
			queryParams.put("proj", proj);
			queryParams.put("create_time", curtime);
			queryParams.put("channelCode", channelCode);
			queryParams.put("status", 1);
			double total = vodTvChannelService.countalbum(queryParams);
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
				List<Map<String,Object>> list = vodTvChannelService.channelalbum(queryParams);
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
	@RequestMapping("/labelip")
	public List<Map<String, Object>> getUrl3(HttpServletRequest request) {
		System.out.println("下发标签更新数据开始");
		 String time=request.getParameter("curtime");
		 final String curtime=time.substring(0,10);
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
							String url =address +tvlabelUrl+"?curtime="+curtime;
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
		String curtime = request.getParameter("curtime");//时间
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();//页数
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();//项目名称
		try {
			int limit =Integer.parseInt(size);
			queryParams.put("create_time", curtime);
			queryParams.put("proj", proj);
			queryParams.put("status", 1);
		/*	List<Map<String, Object>> busschannel=dictionaryService.findbusschannel(queryParams);//获取项目下的频道
			if(busschannel.size()>0){
				queryParams.put("busschannel", busschannel.get(0).get("name"));
			}*/
			queryParams.put("busschannel", proj);
			double total = vodTvLabelService.count(queryParams);
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
				List<VodTvlabel> list = vodTvLabelService.page(queryParams);
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
	 * 分平台专题下发
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/specialip")
	public List<Map<String, Object>> getUrl8(HttpServletRequest request) {
		System.out.println("下发频道更新数据开始");
		String time=request.getParameter("curtime");
		final String curtime=time.substring(0,10);
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
							String url =address + tvspecialUrl+"?curtime="+curtime;
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
		List<VodTvSpecial> list = new ArrayList<>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();//项目名称
		try {
			int limit =Integer.parseInt(size);
			queryParams.put("proj", proj);
			queryParams.put("busschannel", proj);
			queryParams.put("create_time", curtime);
			queryParams.put("status", 1);
			
			double total = vodTvSpecialService.count(queryParams);
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
				queryParams.put("pageSize",qo.getPageSize());
				list = vodTvSpecialService.page(queryParams);
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
		String curtime = request.getParameter("curtime")==null?"2017-11-24" :request.getParameter("curtime").toString();
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		int special_id = Integer.parseInt(request.getParameter("special_id"));
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();//项目名称
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("special_id", special_id);
		try {
			int limit =Integer.parseInt(size);
			queryParams.put("proj", proj);
			/*List<Map<String, Object>> busschannel=dictionaryService.findbusschannel(queryParams);//获取项目下的频道
			if(busschannel.size()>0){
				queryParams.put("busschannel", busschannel.get(0).get("name"));
			}*/
			queryParams.put("busschannel", proj);
		/*	List<Map<String, Object>> codelist=vodTvAlbuminfoService.findIdByChannel(queryParams);
			if(codelist.size()>0){
				queryParams.put("codelist", codelist.get(0).get("code"));
				
			}	*/
			double total = vodTvSpecialService.countalbum(queryParams);
			if (total == 0) {
				json.accumulate("code", 0);
				json.accumulate("data", "{}");
			} else {
				int numsum = (int) Math.ceil(total / 100);
				QueryObject qo = new QueryObject();
				qo.setPage(limit);
				qo.setRows(100);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize",qo.getPageSize());
				
				List<Map<String, Object>> list = vodTvSpecialService.findpagealbum(queryParams);
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
	 * 下发模块分平台
	 * @author shenjr
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/tvmoduleip")
	public Map<String, Object> getUrl9(HttpServletRequest request) {
		System.out.println("下发模块数据开始");
		final String moduleId = request.getParameter("ids")==null?"":request.getParameter("ids").toString();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<Vodbussinfo> list = vodbussinfoservice.find(queryParams);
		for (Vodbussinfo m : list) {
			final String address = m.getAddress();
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 手动下发 到运营库接口
							String url =address +tvmoduleUrl+"?moduleId="+moduleId;
							notice(url);
						} catch (IllegalArgumentException ec) {
							interrupted();
						}
					}
				};
				hth.start();
				Thread.sleep(200);
				jsonMap.put("code", 1);
				jsonMap.put("message", "下发成功");
			} catch (InterruptedException e) {
				e.printStackTrace();
				jsonMap.put("code", 0);
				jsonMap.put("message", "下发失败");
			}
		}
		return jsonMap;
	}
	
	
	/**
	 * 返回模块数据&模块运营位
	 * @author shenjr
	 * @param moduleId,proj
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getModule")
	public String getModule(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> dataMap=new HashMap<String,Object>();
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String proj=request.getParameter("proj")==null?"1":request.getParameter("proj").toString();
		String moduleId = request.getParameter("moduleId")==null?"":request.getParameter("moduleId");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		try {
			int limit = Integer.parseInt(size);
			if(moduleId!=""){
				queryParams.put("ids", moduleId.split(","));
			}
			queryParams.put("proj", proj);
			queryParams.put("busschannel", proj);
			int total = this.vodMasterplateInterface.count(queryParams);
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
				List<vodMasterplate> list = this.vodMasterplateInterface.page(queryParams);
				dataMap.put("parent", list);
				List<vodMasterplateSon> listSon=this.vodMasterplateSonInterface.findSon(queryParams);
				dataMap.put("son", listSon);
				
				json.accumulate("code", 1);
				json.accumulate("data", dataMap);
				json.accumulate("numsum", numsum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
		}
		response.getWriter().write(json.toString());
		return null;
	}
	
	

}
