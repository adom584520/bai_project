package com.pbtd.manager.inject.page.controller;

import java.io.IOException;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.inject.page.service.face.InjectPhoneOutPutService;
import com.pbtd.manager.inject.phone.service.face.IInjectPhoneBehindPriorityService;
import com.pbtd.manager.inject.phone.service.face.IInjectPhoneZxService;
import com.pbtd.manager.util.URLSend;
import com.pbtd.manager.vod.page.QueryObject;

import net.sf.json.JSONObject;

/**
 * 分平台下注入数据到华为,中兴
 *  
 * @author 程
 *
 */
@Controller
@RequestMapping("/inject/outputjson")
@PropertySource(value = { "classpath:config/inject.properties" })
public class InjectPhoneOutPutController {

	public static Logger log = Logger.getLogger(InjectPhoneOutPutController.class);

	@Value("${phoneHwInject}")
	private String phoneHwInject;
	@Value("${phoneHwInjectId}")
	private String phoneHwInjectId;
	@Value("${phoneHwInjectCurtime}")
	private String phoneHwInjectCurtime;
	@Value("${phoneZxInject}")
	private String phoneZxInject;
	@Value("${phoneZxInjectId}")
	private String phoneZxInjectId;
	@Value("${phoneZxInjectCurtime}")
	private String phoneZxInjectCurtime;
	@Autowired
	private InjectPhoneOutPutService injectPhoneOutPutService;
	@Autowired
	private IInjectPhoneBehindPriorityService iInjectPhone;
	@Autowired
	private IInjectPhoneZxService injectPhoneZxService;
	
	// 手机端注入到华为,中兴
	@RequestMapping("/phoneInject")
	public void phoneInject() {
		log.info("inject_phone: 准备数据");
		iInjectPhone.dealInjection();
		// 请求地址
		/*
		log.info("inject_phone: 通知HwInjecter");
		URLSend.ishttp(phoneHwInject);
		
		log.info("inject_phone: 通知ZxInjecter");
		URLSend.ishttp(phoneZxInject);
		log.info("inject_phone: 通知结束");
		*/
		// URLSend.ishttp(phoneZxInjectId+"11978,11979");
		// URLSend.ishttp(phoneZxInjectCurtime+"2017-11-29");
	}

	
	@RequestMapping("/phoneHwjectId/{ids}")
	public String phoneInjectId(@PathVariable("ids") String ids, Model model) {
		log.info("inject_phone: 通知HwInjecter-剧集Id");
		URLSend.ishttp(phoneHwInjectId + ids);
		return null;
	}

	@RequestMapping("/phoneHwInjectCurtime/{curtime}")
	public String phoneInjectCurtime(@PathVariable("curtime") String curtime, Model model) {
		log.info("inject_phone: 通知HwInjecter-剧集创建时间");
		URLSend.ishttp(phoneHwInjectCurtime + curtime);
		return null;
	}

	@RequestMapping("/phoneZxInjectId/{ids}")
	public String phoneZxInjectId(@PathVariable("ids") String ids, Model model) {
		log.info("inject_phone: 通知ZxInjecter-剧集Id");
		URLSend.ishttp(phoneHwInjectId + ids);
		return null;
	}

	@RequestMapping("/phoneZxInjectCurtime/{curtime}")
	public String phoneZxInjectCurtime(@PathVariable("curtime") String curtime, Model model) {
		log.info("inject_phone: 通知ZxInjecter-剧集创建时间");
		URLSend.ishttp(phoneHwInjectCurtime + curtime);
		return null;
	}

	/**
	 * 获取注入剧集接口-华为
	 * @param curtime  日期 & limit 页数
	 * @return 剧集list
	 */
	@ResponseBody
	@RequestMapping("/phoneHw/getalbumvideo")
	public String getHwInjectAlbumVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String ids = request.getParameter("ids");
		log.info("inject_phone: 收到HwInjecter请求albumvideoList");
		log.info("inject_phone: HwInjecter size:"+size);
		log.info("inject_phone: HwInjecter ids:"+ids);
		log.info("inject_phone: HwInjecter curtime:"+curtime);
		queryParams.put("hwInjectState",0); //默认发0的
		try {
			int limit = Integer.parseInt(size);
			if (curtime != null && !"null".equals(curtime) && !"".equals(curtime)) {
				queryParams.put("create_time", curtime);
			} // 未结束
			if (ids != null && !"null".equals(ids) && !"".equals(ids)) {
				queryParams.put("seriescode_", ids.split(","));
				queryParams.put("hwInjectState",-1); //重注入 发-1
			}
			double total = injectPhoneOutPutService.countHwAlbumsinfovideo(queryParams);
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

				List<Map<String, Object>> list = injectPhoneOutPutService.findHwAlbumsinfovideo(queryParams);
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
	 * 获取注入剧集接口-中兴
	 * 
	 * @param curtime 日期 & limit 页数
	 * @return 剧集list
	 */
	@ResponseBody
	@RequestMapping("/phoneZx/getalbumvideo")
	public String getZxInjectAlbumVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String curtime = request.getParameter("curtime");
		String size = request.getParameter("limit") == null ? "1" : request.getParameter("limit").toString();
		String ids = request.getParameter("ids");
		log.info("inject_phone: 收到ZxInjecter请求albumvideoList");
		log.info("inject_phone: ZxInjecter size:"+size);
		log.info("inject_phone: ZxInjecter ids:"+ids);
		log.info("inject_phone: ZxInjecter curtime:"+curtime);  
		queryParams.put("zxInjectState",0); //默认发0的
		try {
			int limit = Integer.parseInt(size);
			if (curtime != null && !"null".equals(curtime) && !"".equals(curtime)) {
				queryParams.put("create_time", curtime);
			} // 未结束
			if (ids != null && !"null".equals(ids) && !"".equals(ids)) {
				queryParams.put("seriescode_", ids.split(","));
				queryParams.put("zxInjectState",-1);  //重注入，下发-1的
			}
			double total = injectPhoneOutPutService.countZxAlbumsinfovideo(queryParams);
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

				List<Map<String, Object>> list = injectPhoneOutPutService.findZxAlbumsinfovideo(queryParams);
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
	 * 华为,中兴注入返回 调度地址
	 * 
	 * @param seriesCode
	 *            专辑ID& id 剧集ID& drama 剧集序列 & dispatchUrl 调度地址 & type 平台 &
	 *            version 码率
	 * @return null
	 */
	@RequestMapping("/updatePhoneInject")
	public String updatePhoneInject(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		String seriesCode = request.getParameter("seriesCode") == null ? ""
				: request.getParameter("seriesCode").toString();
		String id = request.getParameter("id") == null ? "" : request.getParameter("id").toString();
		String drama = request.getParameter("drama") == null ? "" : request.getParameter("drama").toString();
		String dispatchUrl = request.getParameter("dispatchUrl") == null ? ""
				: request.getParameter("dispatchUrl").toString();
		String type = request.getParameter("type") == null ? "" : request.getParameter("type").toString();
		String version = request.getParameter("version") == null ? "4" : request.getParameter("version").toString();
		log.info("inject_phone: 更新调度地址-"+type);
		log.info("inject_phone: type:"+type);
		log.info("inject_phone: id:"+id);
		log.info("inject_phone: seriesCode:"+seriesCode);
		log.info("inject_phone: drama:"+drama);
		log.info("inject_phone: dispatchUrl:"+dispatchUrl);
		log.info("inject_phone: version:"+version);
		// String
		// url="http://vod.dispatcher.cibnhz.com/cibn_vod/"+jmid+"/"+mcode+"/?";//调度地址
		Map<String, Object> queryParams = new HashMap<String, Object>();
		try {
			if ("".equals(type) || "null".equals(type) || "null".equals(id) || "".equals(id)) {
				json.accumulate("code", 0);
				json.accumulate("message", "参数有误");
				response.getWriter().write(json.toString());
				return null;
			}
			if ("hw".equals(type)) {
				if ("null".equals(version) || "".equals(version)) {
					version = "4";
				}
				queryParams.put("id", id);
				queryParams.put("types", type);
				queryParams.put("hwversion", version);
				queryParams.put("hwdispatchurl", dispatchUrl);
			} else if ("zx".equals(type)) {
				if ("null".equals(version) || "".equals(version)) {
					version = "4";
				}
				queryParams.put("id", id);
				queryParams.put("types", type);
				queryParams.put("zxversion", version);
				queryParams.put("zxdispatchurl", dispatchUrl);
			}
			injectPhoneOutPutService.updatePhoneInject(queryParams);
			json.accumulate("code", 1);
			json.accumulate("message", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
			json.accumulate("message", "失败");
		}
		response.getWriter().write(json.toString());
		return null;
	}

	
	
	/**
	 * 手机injection接口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updatePhoneInjectState")
	public String updatePhoneInjectState(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		String type = request.getParameter("type") == null ? "" : request.getParameter("type").toString();
		String seriesCode = request.getParameter("seriesCode") == null ? "":request.getParameter("seriesCode").toString();
		String drama = request.getParameter("drama") == null ? "" : request.getParameter("drama").toString();
		String version=request.getParameter("version")==null?"":request.getParameter("version").toString();
		String injectState=request.getParameter("injectState")==null?"":request.getParameter("injectState");
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("inject_phone: 更新注入状态-"+type);
		log.info("inject_phone: type:"+type);
		log.info("inject_phone: seriesCode:"+seriesCode);
		log.info("inject_phone: drama:"+drama);
		log.info("inject_phone: version:"+version);
		log.info("inject_phone: injectState:"+injectState);
		try {
			if ("".equals(type) || "null".equals(type) || "".equals(seriesCode) || "null".equals(seriesCode) 
					|| "".equals(drama) || "null".equals(injectState)){
				json.accumulate("code", 0);
				json.accumulate("message", "参数有误");
				response.getWriter().write(json.toString());
				return null;
			}
			if ("hw".equals(type)) {
				map.put("seriesCode", seriesCode);
				map.put("drama", drama);
				map.put("version", version);
				map.put("hwInjectState", injectState);
				//Map<String,Object> dbVideoMap=this.injectPhoneZxService.findAlbumVideoBySeriesAndDrama(map);
				Map<String,Object> videoVersionMap=this.injectPhoneZxService.findVersionBySeriesAndDramaAndVersion(map);
				if(videoVersionMap!=null){
					videoVersionMap.put("hwInjectState", injectState);
					//更新
					this.injectPhoneZxService.updateHwVideoVersion(videoVersionMap);
					
				}else{
					this.injectPhoneZxService.saveHwVideoVersion(map);
				}
				//this.injectPhoneZxService.updateAlbumVideoHwInjectState(map);
				//更新华为剧集状态
				this.injectPhoneZxService.updateHwVideoStateFromVersion(map);
				//更新专辑状态
				this.injectPhoneZxService.updateSingleAlbumInjectState(map);
				
			} else if ("zx".equals(type)) {
				map.put("seriesCode", seriesCode);
				map.put("drama", drama);
				map.put("version", version);
				map.put("zxInjectState", injectState);
				Map<String,Object> videoVersionMap=this.injectPhoneZxService.findVersionBySeriesAndDramaAndVersion(map);
				if(videoVersionMap!=null){
					videoVersionMap.put("zxInjectState", injectState);
					//更新
					this.injectPhoneZxService.updateZxVideoVersion(videoVersionMap);
					
				}else{
					this.injectPhoneZxService.saveZxVideoVersion(map);
				}
				//this.injectPhoneZxService.updateAlbumVideoZxInjectState(map);
				//更新中兴剧集状态
				this.injectPhoneZxService.updateZxVideoStateFromVersion(map);
				//更新专辑状态
				this.injectPhoneZxService.updateSingleAlbumInjectState(map);
			}
			json.accumulate("code", 1);
			json.accumulate("message", "成功");
			
		} catch (Exception e) {
			log.info("更新注入状态异常", e);
			json.accumulate("code", 0);
			json.accumulate("message", "失败");
		}
		response.getWriter().write(json.toString());
		return null;
	}
	
}
