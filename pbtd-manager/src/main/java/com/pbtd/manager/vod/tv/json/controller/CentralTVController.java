package com.pbtd.manager.vod.tv.json.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.util.URLSend;
import com.pbtd.manager.vod.page.controller.TvJsonInterfaceController;
import com.pbtd.manager.vod.tv.json.domain.VodjsonTvAlbuminfo;
import com.pbtd.manager.vod.tv.json.serice.face.ICentralTvService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *汇聚下发运营平台
 * @author 程
 *
 */
@Controller
@RequestMapping("/integrate/outputjson")
@PropertySource(value = { "classpath:config/central.properties" })
public class CentralTVController {
	public static Logger log = Logger.getLogger(CentralTVController.class);

	@Value("${centraltv_url}")
	private String centraltvUrl;
	@Value("${centraltv_channel}")
	private String centraltvChannel;
	@Value("${centraltv_label}")
	private String centraltvLabel;
	@Autowired
	private ICentralTvService centralTvService;
	@Autowired
	private TvJsonInterfaceController tvjson;
	HttpServletRequest request=null;
	// integrate/outputjson/tvalbumsinfo 专辑详情
	// integrate/outputjson/tvalbumsinfovideo 子集
	// integrate/outputjson/gettvchannel TV频道
	// integrate/outputjson/gettvlabel TV标签
	// integrate/outputjson/getactors 演员
	//汇聚下发时触发这个接口
	@RequestMapping("/tvcentral/Insertupdata")
	public void Insertupdata(HttpServletRequest request) {
		String start = request.getParameter("start");
		String id = request.getParameter("id");
		log.info("TV下发专辑更新 "+start);
		log.info("TV下发增量数据"+id);
		if ("1".equals(start)) {
			InsertCentral();//zd
		}
		if (id != null && !"".equals(id)) {
			 updataIDCentral(id);//sd
		}
	}
	
	// TV频道添加
		@RequestMapping("/tvcentral/gettvchannel")
		public void phonechannel(HttpServletRequest request) {
			log.info("从汇聚平台获取TV频道...");
			JSONObject json = new JSONObject();
			// 请求地址
			String rtsp = URLSend.ishttp(centraltvChannel);
			json = JSONObject.fromObject(rtsp);
			JSONArray result = JSONArray.fromObject(json.getJSONArray("body"));
			for (int ii = 0; ii < result.size(); ii++) {
				JSONObject job = result.getJSONObject(ii);
				Integer levels = Integer.parseInt(job.get("levels").toString());
				String channelName = job.get("name").toString();
				Integer channelCode = Integer.parseInt(job.get("categorycode").toString());
				try {
					log.info("频道添加: "+channelName);
					centralTvService.Addtvchannel(levels,channelName,channelCode);
				} catch (Exception e) {
					log.info("频道添加异常: " + job.get("categorycode").toString());
					continue;
				}
			}

		}

		// TV标签添加
		@RequestMapping("/tvcentral/gettvlabel")
		public void phonelabel(HttpServletRequest request) {
			log.info("从汇聚平台获取TV标签...");
			JSONObject json = new JSONObject();
			// 请求地址
			String rtsp = URLSend.ishttp(centraltvLabel);
			json = JSONObject.fromObject(rtsp);
			JSONArray result = JSONArray.fromObject(json.getJSONArray("body"));
			for (int ii = 0; ii < result.size(); ii++) {
				JSONObject job = result.getJSONObject(ii);
				Integer id = Integer.parseInt(job.get("categorycode").toString());
				String name = job.get("name").toString();
				Integer channelCode = Integer.parseInt(job.get("parentcode").toString());
				Integer level = Integer.parseInt(job.get("levels").toString());
				try {
					log.info("标签添加:"+name);
					centralTvService.Addtvlabel(id,name,channelCode,level);
				} catch (Exception e) {
					log.info("标签添加异常: " + job.get("categorycode").toString());
					continue;
				}
			}
		}

	/**
	 * 获取全部节目列表 20171101改成新增节目时先处于下线状态 等全部剧集导入完毕后再上线
	 */
	public void InsertCentral() {
		log.info("TV汇聚下发增量数据开始");
		String rtsp = "";
		JSONObject json = new JSONObject();
		Integer pageNo = 1, pageCapacity = 1000, total = 0;
		while (true) {
			// 入库
			rtsp = URLSend.sendHttpClientGet(centraltvUrl + "?start=" + pageNo);
			json = JSONObject.fromObject(rtsp);
			log.info("分页码：" + pageNo + "\t总记录数：" + json.get("size"));
			 // 获取总记录数
			total = Integer.parseInt(json.get("size").toString());
			List<VodjsonTvAlbuminfo> testList = JSON.parseArray(json.getJSONArray("body").toString(),
					VodjsonTvAlbuminfo.class);
			for (VodjsonTvAlbuminfo vodTvAlbuminfo : testList) {
				vodTvAlbuminfo.setCpseriescode(vodTvAlbuminfo.getSeriescode().toString());
				vodTvAlbuminfo.setStatus(0);
				try {
					centralTvService.getInsertUpdate(vodTvAlbuminfo,1);
				} catch (Exception e) {
					log.info("专辑添加异常: " + vodTvAlbuminfo.getSeriescode().toString());
					continue;
				}
			}
			// 当分页记录数大于等于总记录数时，直接跳出循环
			if (pageNo * pageCapacity >= total) {
				break;
			} else {
				pageNo = pageNo + 1;
				// break; //测试记录
			}
		}
		//int CodeCount = centralTvService.UpdateSeriesCode();
		//log.info("更新剧集code总数:"+CodeCount);
		//通知分平台
		//tvjson.getUrl(request);
		log.info("TV汇聚下发增量数据结束");

	}

	public void updataIDCentral(String id) {
		log.info("TV下发专辑更新数据开始ID");
		String rtsp = "";
		JSONObject json = new JSONObject();
		rtsp = URLSend.sendHttpClientGet(centraltvUrl + "?id=" + id);
		json = JSONObject.fromObject(rtsp);
		log.info("更新专辑ID：" + id + "\t总记录数：" + json.get("size"));
		List<VodjsonTvAlbuminfo> testList = JSON.parseArray(json.getJSONArray("body").toString(),
				VodjsonTvAlbuminfo.class);
		for (VodjsonTvAlbuminfo vodTvAlbuminfo : testList) {
			vodTvAlbuminfo.setCpseriescode(vodTvAlbuminfo.getSeriescode().toString());
			vodTvAlbuminfo.setStatus(0);
			try {
				centralTvService.getInsertUpdate(vodTvAlbuminfo,2);
			} catch (Exception e) {
				log.info("专辑添加异常: " + vodTvAlbuminfo.getSeriescode().toString());
				continue;
			}
		}
		//int CodeCount = centralTvService.UpdateSeriesCode();
		//log.info("更新剧集code总数:"+CodeCount);
		//通知分平台
		//tvjson.getUrl(request);
		log.info("TV下发专辑更新数据结束ID");
	}
}
