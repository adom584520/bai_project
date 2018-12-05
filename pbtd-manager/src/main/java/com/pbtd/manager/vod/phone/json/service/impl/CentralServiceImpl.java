package com.pbtd.manager.vod.phone.json.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.util.URLSend;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.common.mapper.VodChannelMapper;
import com.pbtd.manager.vod.phone.common.mapper.VodLabelMapper;
import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfo;
import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfovideo;
import com.pbtd.manager.vod.phone.json.mapper.VodjsonPhoneAlbuminfoMapper;
import com.pbtd.manager.vod.phone.json.mapper.VodjsonPhoneAlbuminfovideoMapper;
import com.pbtd.manager.vod.phone.json.service.face.ICentralService;

import net.sf.json.JSONObject;

/**
 * 汇聚下发运营平台 手机
 * 
 * @author 程
 *
 */
@Service
public class CentralServiceImpl implements ICentralService {
	public static Logger log = Logger.getLogger(CentralServiceImpl.class);

	@Value("${central_video}")
	private String centralVideo;
	@Autowired
	private VodjsonPhoneAlbuminfoMapper vodPhoneAlbuminfoMapper;
	@Autowired
	private VodjsonPhoneAlbuminfovideoMapper VodPhonevideoMapper;
	@Autowired
	private VodChannelMapper vodChannelMapper;
	@Autowired
	private VodLabelMapper vodLabelMapper;

	@Autowired
	private IDictionaryService dictionaryService;

	// 添加或更新专辑详情
	@Override
	public void getInsertUpdate(VodjsonPhoneAlbuminfo vodPhoneAlbuminfo, int type) throws Exception {
		// 查询当前剧集是否存在 存在更改 不存在添加
		VodjsonPhoneAlbuminfo mapalb = vodPhoneAlbuminfoMapper.selectByPrimaryKey(vodPhoneAlbuminfo.getCentralcode());
		
		if (mapalb == null) {
			// 专辑为空时添加专辑
			vodPhoneAlbuminfoMapper.insert(vodPhoneAlbuminfo);
			if(type!=1){
				// 添加完专辑后添加子集
				insertVideo(vodPhoneAlbuminfo.getCpseriescode(), type, vodPhoneAlbuminfo.getSeriescode());
			}
		} else {
			// 专辑不为空时,更新集数和时间
			vodPhoneAlbuminfoMapper.updateByPrimaryKey(vodPhoneAlbuminfo);
			if(type!=1){
			// 更新完专辑后添加子集
			insertVideo(vodPhoneAlbuminfo.getCpseriescode(), type, vodPhoneAlbuminfo.getSeriescode());
			}
		}

	}

	// 添加子集
	private void insertVideo(String Cpseriescode, int type, String seriescode) {
		log.info("下发子集更新数据开始");
		String rtsp = "";
		JSONObject json = new JSONObject();
		// id = "1000042,245607,245608,258845,258847,25890,25904,25908";
		// type等于1为自动下发
		if (type == 1) {
			// insertVideooffset( Cpseriescode, type, seriescode);
		} else {
			Integer pageNo = 1,  count = 1;
			while (true) {
				rtsp = URLSend.sendHttpClientGet(centralVideo + "?id=" + seriescode+"&start=" + pageNo );
				json = JSONObject.fromObject(rtsp);
				log.info("更新专辑ID：" + seriescode + "\t总记录数：" + json.get("size"));
				// 获取总页数
				count = Integer.parseInt(json.get("count").toString());
				List<VodjsonPhoneAlbuminfovideo> testList = JSON.parseArray(json.getJSONArray("body").toString(),
						VodjsonPhoneAlbuminfovideo.class);
				for (VodjsonPhoneAlbuminfovideo vodPhonevideo : testList) {
					try {
						vodPhonevideo.setType(Cpseriescode);
						vodPhonevideo.setZxversionlist(vodPhonevideo.getVersion());
						vodPhonevideo.setSeriescode(seriescode + "");
						// 没有做剧集去重
						VodPhonevideoMapper.insert(vodPhonevideo);
					} catch (Exception e) {
						log.info("子集添加异常: " + vodPhonevideo.getDramacode());
						continue;
					}
				}
				// 当分页记录数大于等于总页数时，直接跳出循环
				if (pageNo >= count) {
					break;
				} else {
					pageNo = pageNo + 1;
					// break; //测试记录
				}
			}
		}

	}

	// 添加子集
	public void insertVideooffset() {
		log.info("下发子集自动更新数据开始");
		String rtsp = "";
		JSONObject json = new JSONObject();
		Integer pageNo = 1, count = 1;
		Map<String, Object> offsetmap = dictionaryService.getoffset(null);
		int albumvideo_offset = offsetmap.get("albumvideo_offset") == null ? 0
				: Integer.parseInt(offsetmap.get("albumvideo_offset").toString());
		while (true) {
			// 自动下发
			rtsp = URLSend.sendHttpClientGet(centralVideo + "?&start=" + pageNo + "&offset=" + albumvideo_offset);
			json = JSONObject.fromObject(rtsp);
			log.info("\t总记录数：" + json.get("size"));
			// 获取总页数
			count = Integer.parseInt(json.get("count").toString());

			List<VodjsonPhoneAlbuminfovideo> testList = JSON.parseArray(json.getJSONArray("body").toString(),
					VodjsonPhoneAlbuminfovideo.class);
			for (VodjsonPhoneAlbuminfovideo vodPhonevideo : testList) {
				if (albumvideo_offset < vodPhonevideo.getOffset()) {
					albumvideo_offset = vodPhonevideo.getOffset();
				}
				try {
					// vodPhonevideo.setType(Cpseriescode);
					vodPhonevideo.setZxversionlist(vodPhonevideo.getVersion());
					vodPhonevideo.setSeriescode(vodPhonevideo.getCentralcode());
					// 没有做剧集去重
					VodPhonevideoMapper.insert(vodPhonevideo);
					
				} catch (Exception e) {
					log.error("子集添加异常: " + vodPhonevideo.getDramacode()+ e);
					continue;
				}
			}
			// 更新剧集爬取序号值
			Map<String, Object> mapalbumoffset = new HashMap<>();
			mapalbumoffset.put("albumvideo_offset", albumvideo_offset);
			dictionaryService.updateoffset(mapalbumoffset);
			// 当分页记录数等于总页数时，直接跳出循环
			if (count == 1) {
				break;
			}
		}
	}

	// 手机频道添加
	@Override
	public void Addphonechannel(Integer levels, String channelName, Integer channelCode) throws Exception {
		Map<String, Object> m = new HashMap<>();
		m.put("levels", 1);
		Map<String, Object> maps = vodChannelMapper.findsequence(m);
		String maxnum = "0";
		if (maps != null) {
			maxnum = maps.get("max") == null ? "1" : Integer.parseInt(maps.get("max").toString()) + 1 + "";
		}
		// 频道添加
		vodChannelMapper.insertPrimary(levels, channelName, channelCode, maxnum);
	}

	@Override
	public void Addphonelabel(Integer id, String name, Integer channelCode, Integer level) throws Exception {
		// 标签添加
		Map<String, Object> maps = vodLabelMapper.findsequence(null);
		String maxnum = "0";
		if (maps != null) {
			maxnum = maps.get("max") == null ? "1" : Integer.parseInt(maps.get("max").toString()) + 1 + "";
		}
		vodLabelMapper.insertPrimary(id, name, channelCode, level, maxnum);
	}

	@Override
	public int UpdateSeriesCode() {

		return VodPhonevideoMapper.updateSeriesCode();
	}

}
