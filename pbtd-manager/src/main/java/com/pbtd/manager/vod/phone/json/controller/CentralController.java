package com.pbtd.manager.vod.phone.json.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.util.URLSend;
import com.pbtd.manager.vod.page.controller.PhoneJsonInterfaceController;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfo;
import com.pbtd.manager.vod.phone.json.service.face.ICentralService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 汇聚下发运营平台
 * 
 * @author 程 phone
 */
@Controller
@RequestMapping("/integrate/outputjson")
@PropertySource(value = { "classpath:config/central.properties" })
public class CentralController {
	public static Logger log = Logger.getLogger(CentralController.class);

	@Value("${central_url}")
	private String centralUrl;
	@Value("${central_channel}")
	private String centralChannel;
	@Value("${central_label}")
	private String centralLabel;
	@Autowired
	private ICentralService centralService;
	@Autowired
	private PhoneJsonInterfaceController phonejson;
	@Autowired
	private IDictionaryService dictionaryService;
	HttpServletRequest request = null;

	// integrate/outputjson/phonealbumsinfo 专辑详情
	// integrate/outputjson/phonealbumsinfovideo 子集
	// integrate/outputjson/getphonechannel 手机频道
	// integrate/outputjson/getphonelabel 手机标签
	// integrate/outputjson/getactors 演员
	// 汇聚下发时触发这个接口
	@RequestMapping("/central/Insertupdata")
	public void Insertupdata(HttpServletRequest request) {
		String start = request.getParameter("start");
		String id = request.getParameter("id") == null ? "" : request.getParameter("id").toString();
		log.info("下发专辑更新 " + start);
		log.info("下发增量数据" + id);
		if ("1".equals(start)) {
			InsertCentral();// zd
		}
		if (id != null && !"".equals(id)) {
			updataIDCentral(id);// sd
		}
	}

	// 手机频道添加
	@RequestMapping("/central/getphonechannel")
	public String phonechannel(HttpServletRequest request) {
		log.info("从汇聚平台获取手机频道...");
		JSONObject json = new JSONObject();
		// 请求地址
		String rtsp = URLSend.ishttp(centralChannel);
		json = JSONObject.fromObject(rtsp);
		JSONArray result = JSONArray.fromObject(json.getJSONArray("body"));
		for (int ii = 0; ii < result.size(); ii++) {
			JSONObject job = result.getJSONObject(ii);
			Integer levels = Integer.parseInt(job.get("levels").toString());
			String channelName = job.get("name").toString();
			Integer channelCode = Integer.parseInt(job.get("categorycode").toString());
			try {
				log.info("频道添加: " + channelName);
				centralService.Addphonechannel(levels, channelName, channelCode);
			} catch (Exception e) {
				log.error("频道添加异常: " + job.get("categorycode").toString());
				continue;
			}
		}
		return "1";

	}

	// 手机标签添加
	@RequestMapping("/central/getphonelabel")
	public void phonelabel(HttpServletRequest request) {
		log.info("从汇聚平台获取手机标签...");
		JSONObject json = new JSONObject();
		// 请求地址
		String rtsp = URLSend.ishttp(centralLabel);
		json = JSONObject.fromObject(rtsp);
		JSONArray result = JSONArray.fromObject(json.getJSONArray("body"));
		for (int ii = 0; ii < result.size(); ii++) {
			JSONObject job = result.getJSONObject(ii);
			Integer id = Integer.parseInt(job.get("categorycode").toString());
			String name = job.get("name").toString();
			Integer channelCode = Integer.parseInt(job.get("parentcode").toString());
			Integer level = Integer.parseInt(job.get("type").toString());
			try {
				log.info("标签添加:" + name);
				centralService.Addphonelabel(id, name, channelCode, level);
			} catch (Exception e) {
				log.error("标签添加异常: " + job.get("categorycode").toString() + e);

				continue;
			}
		}
	}

	/**
	 * 获取全部节目列表 20171101改成新增节目时先处于下线状态 等全部剧集导入完毕后再上线
	 */
	public void InsertCentral() {// zd
		log.info("汇聚下发增量数据开始");
		String rtsp = "";
		JSONObject json = new JSONObject();
		Integer pageNo = 1, count = 1;
		int offsetalbum = 0;
		while (true) {
			Map<String, Object> offsetmap = dictionaryService.getoffset(null);
			offsetalbum = offsetmap.get("album_offset") == null ? 0
					: Integer.parseInt(offsetmap.get("album_offset").toString());
			// 请求地址
			rtsp = URLSend.sendHttpClientGet(centralUrl + "?start=" + pageNo + "&offset=" + offsetalbum);
			json = JSONObject.fromObject(rtsp);
			log.info("分页码：" + pageNo + "\t总页数：" + json.get("size"));
			// 获取总记录数
			count = Integer.parseInt(json.get("count").toString());
			List<VodjsonPhoneAlbuminfo> testList = JSON.parseArray(json.getJSONArray("body").toString(),
					VodjsonPhoneAlbuminfo.class);
			for (VodjsonPhoneAlbuminfo vodPhoneAlbuminfo : testList) {
				String description = vodPhoneAlbuminfo.getDescription();
				// 内容简介超长截取
				try {
					if (!"".equals(description) && description != null) {
						if (description.length() >= 650) {
							vodPhoneAlbuminfo.setDescription(description.substring(0, 650) + "...");
						}
					}
					// cp源id
					vodPhoneAlbuminfo.setCpseriescode(vodPhoneAlbuminfo.getSeriescode().toString());
					// 汇聚源id
					vodPhoneAlbuminfo.setSeriescode(vodPhoneAlbuminfo.getCentralcode());
					vodPhoneAlbuminfo.setSequence(151);
					vodPhoneAlbuminfo.setStatus(0);

					centralService.getInsertUpdate(vodPhoneAlbuminfo, 1);
					if (offsetalbum < vodPhoneAlbuminfo.getOffset()) {
						offsetalbum = vodPhoneAlbuminfo.getOffset();
					}
				} catch (Exception e) {
					log.error("专辑添加异常: " + vodPhoneAlbuminfo.getSeriescode().toString()+e);
					continue;
				}
			}
			Map<String, Object> mapalbumoffset = new HashMap<>();
			mapalbumoffset.put("album_offset", offsetalbum);
			dictionaryService.updateoffset(mapalbumoffset);
			// 当分页记录数大于等于总记录数时，直接跳出循环
			if (count == 1) {
				break;
			}
		}

		try {
			centralService.insertVideooffset();
		} catch (Exception e) {
			log.error("偏移值添加子集异常: " + e);
		}
		/*
		 * int CodeCount = centralService.UpdateSeriesCode();
		 * log.info("更新剧集code总数:"+CodeCount);
		 */
		// 通知分平台
		phonejson.getUrl(request);
		log.info("汇聚下发增量数据结束");
	}

	public void updataIDCentral(String id) {
		log.info("下发专辑更新数据开始ID");
		String rtsp = "";
		JSONObject json = new JSONObject();
		rtsp = URLSend.sendHttpClientGet(centralUrl + "?id=" + id);
		json = JSONObject.fromObject(rtsp);
		log.info("更新专辑ID：" + id + "\t总记录数：" + json.get("size"));
		List<VodjsonPhoneAlbuminfo> testList = JSON.parseArray(json.getJSONArray("body").toString(),
				VodjsonPhoneAlbuminfo.class);
		for (VodjsonPhoneAlbuminfo vodPhoneAlbuminfo : testList) {
			String description = vodPhoneAlbuminfo.getDescription();
			// 内容简介超长截取
			if (description.length() >= 650) {
				vodPhoneAlbuminfo.setDescription(description.substring(0, 650) + "...");
			}
			// cp源id
			vodPhoneAlbuminfo.setCpseriescode(vodPhoneAlbuminfo.getSeriescode().toString());
			// 汇聚源id
			vodPhoneAlbuminfo.setSeriescode(vodPhoneAlbuminfo.getCentralcode());
			vodPhoneAlbuminfo.setSequence(151);
			vodPhoneAlbuminfo.setStatus(0);
			try {
				centralService.getInsertUpdate(vodPhoneAlbuminfo, 2);
			} catch (Exception e) {
				log.error("专辑添加异常: " + vodPhoneAlbuminfo.getSeriescode().toString());
				continue;
			}
		}
		/*
		 * int CodeCount = centralService.UpdateSeriesCode();
		 * log.info("更新剧集code总数:"+CodeCount);
		 */
		// 通知分平台
		phonejson.getUrlx(id,request);
		log.info("汇聚下发增量数据结束ID");
	}
}
