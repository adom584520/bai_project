package com.pbtd.manager.vod.tv.json.serice.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.util.URLSend;
import com.pbtd.manager.vod.tv.common.mapper.VodTvChannelMapper;
import com.pbtd.manager.vod.tv.common.mapper.VodTvLabelMapper;
import com.pbtd.manager.vod.tv.json.domain.VodjsonTvAlbuminfo;
import com.pbtd.manager.vod.tv.json.domain.VodjsonTvAlbuminfovideo;
import com.pbtd.manager.vod.tv.json.mapper.VodjsonTvAlbuminfoMapper;
import com.pbtd.manager.vod.tv.json.mapper.VodjsonTvAlbuminfovideoMapper;
import com.pbtd.manager.vod.tv.json.serice.face.ICentralTvService;

import net.sf.json.JSONObject;

/**
 * 汇聚下发运营平台
 * 
 * @author 程
 *
 */
@Service
public class CentralTvServiceImpl implements ICentralTvService {
	public static Logger log = Logger.getLogger(CentralTvServiceImpl.class);

	@Value("${centraltv_video}")
	private String centralTvVideo;
	@Autowired
	private VodjsonTvAlbuminfoMapper vodTvAlbuminfoMapper;
	@Autowired
	private VodjsonTvAlbuminfovideoMapper VodTvvideoMapper;
	@Autowired
	private VodTvChannelMapper vodTvChannelMapper;
	@Autowired
	private VodTvLabelMapper vodTvLabelMapper;

	// 添加或更新专辑详情
	@Override
	public void getInsertUpdate(VodjsonTvAlbuminfo vodTvAlbuminfo, int type) throws Exception {
		// 查询当前剧集是否存在 存在更改 不存在添加
		VodjsonTvAlbuminfo mapalb = vodTvAlbuminfoMapper.selectByPrimaryKey(vodTvAlbuminfo.getCpseriescode());
		if (mapalb == null) {// 专辑为空时添加专辑
			String description = vodTvAlbuminfo.getDescription();
			// 内容简介超长截取
			if (description.length() >= 650) {
				vodTvAlbuminfo.setDescription(description.substring(0, 650) + "...");
			}
			vodTvAlbuminfo.setSequence(151);
			vodTvAlbuminfo.setSeriescode(vodTvAlbuminfo.getCentralcode());
			vodTvAlbuminfoMapper.insert(vodTvAlbuminfo);
			// 添加完专辑后添加子集
			insertVideo(vodTvAlbuminfo.getCpseriescode(), type,vodTvAlbuminfo.getCentralcode());
		} else {
			// 专辑不为空时,更新集数和时间
			vodTvAlbuminfoMapper.updateByPrimaryKey(vodTvAlbuminfo.getCurrentnum(), vodTvAlbuminfo.getCentralcode());
			// 更新完专辑后添加子集
			insertVideo(vodTvAlbuminfo.getCpseriescode(), type,vodTvAlbuminfo.getCentralcode());
		}

	}

	// 添加子集
	private void insertVideo(String Cpseriescode, int type,String seriescode) {
		log.info("下发子集更新数据开始");
		String rtsp = "";
		JSONObject json = new JSONObject();
		// id = "1000042,245607,245608,258845,258847,25890,25904,25908";
		if (type == 1) {
			rtsp = URLSend.sendHttpClientGet(centralTvVideo + "?id=" + seriescode + "&automatic=1");
		} else {
			rtsp = URLSend.sendHttpClientGet(centralTvVideo + "?id=" + seriescode);
		}
		json = JSONObject.fromObject(rtsp);
		log.info("更新专辑ID：" + Cpseriescode + "\t总记录数：" + json.get("size"));
		List<VodjsonTvAlbuminfovideo> testList = JSON.parseArray(json.getJSONArray("body").toString(),
				VodjsonTvAlbuminfovideo.class);
		for (VodjsonTvAlbuminfovideo vodTvvideo : testList) {
			try {
				vodTvvideo.setType(vodTvvideo.getSeriescode());
				vodTvvideo.setZxversionlist(vodTvvideo.getVersion());
				vodTvvideo.setSeriescode(seriescode+"");
				// 没有做剧集去重
				VodTvvideoMapper.insert(vodTvvideo);
			} catch (Exception e) {
				log.info("子集添加异常: " + vodTvvideo.getId());
				continue;
			}
		}
	}

	// 手机频道添加
	@Override
	public void Addtvchannel(Integer levels, String channelName, Integer channelCode) throws Exception {
		Map<String, Object> m=new HashMap<>();
		m.put("levels", 1);
		 Map<String, Object>  maps=vodTvChannelMapper.findsequence(m);
		 String maxnum="0";
		 if(maps!=null){
			   maxnum=maps.get("max")==null?"1":Integer.parseInt(maps.get("max").toString())+1+"";
		 }
		// 频道添加
		vodTvChannelMapper.insertPrimary(levels, channelName, channelCode,maxnum);
	}

	@Override
	public void Addtvlabel(Integer id, String name, Integer channelCode, Integer level) throws Exception {
		// 标签添加
		vodTvLabelMapper.insertPrimary(id, name, channelCode, level);
	}

	@Override
	public int UpdateSeriesCode() {

		return VodTvvideoMapper.updateSeriesCode();
	}
}
