package com.pbtd.manager.vod.phone.json.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.util.URLSend;
import com.pbtd.manager.vod.phone.common.mapper.VodChannelMapper;
import com.pbtd.manager.vod.phone.common.mapper.VodLabelMapper;
import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfo;
import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfovideo;
import com.pbtd.manager.vod.phone.json.mapper.VodjsonPhoneAlbuminfoMapper;
import com.pbtd.manager.vod.phone.json.mapper.VodjsonPhoneAlbuminfovideoMapper;
import com.pbtd.manager.vod.phone.json.service.face.ICentralService;

import net.sf.json.JSONObject;

/**
 * 汇聚下发运营平台
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

	// 添加或更新专辑详情	
	@Override
	public void getInsertUpdate(VodjsonPhoneAlbuminfo vodPhoneAlbuminfo) throws Exception {
		// 查询当前剧集是否存在 存在更改 不存在添加
		VodjsonPhoneAlbuminfo mapalb = vodPhoneAlbuminfoMapper.selectByPrimaryKey(vodPhoneAlbuminfo.getCpseriescode());
		if (mapalb == null) {// 专辑为空时添加专辑
			String description = vodPhoneAlbuminfo.getDescription();
			// 内容简介超长截取
			if (description.length() >= 650) {
				vodPhoneAlbuminfo.setDescription(description.substring(0, 650) + "...");
			}
			vodPhoneAlbuminfoMapper.insert(vodPhoneAlbuminfo);
			//添加完专辑后添加子集
			insertVideo(vodPhoneAlbuminfo.getCpseriescode());
		} else {
			// 专辑不为空时,更新集数和时间
			vodPhoneAlbuminfoMapper.updateByPrimaryKey(vodPhoneAlbuminfo.getCurrentnum(),
					vodPhoneAlbuminfo.getCpseriescode());
			//更新完专辑后添加子集
			insertVideo(vodPhoneAlbuminfo.getCpseriescode());
		}

	}
	
	//添加子集
	private void insertVideo(String Cpseriescode) {
		log.info("下发子集更新数据开始");
		String rtsp = "";
		JSONObject json = new JSONObject();
		// id = "1000042,245607,245608,258845,258847,25890,25904,25908";
		rtsp = URLSend.sendHttpClientGet(centralVideo + "?id=" + Cpseriescode);
		json = JSONObject.fromObject(rtsp);
		log.info("更新专辑ID：" + Cpseriescode + "\t总记录数：" + json.get("size"));
		List<VodjsonPhoneAlbuminfovideo> testList = JSON.parseArray(json.getJSONArray("body").toString(),
				VodjsonPhoneAlbuminfovideo.class);
		for (VodjsonPhoneAlbuminfovideo vodPhonevideo : testList) {
			System.out.println(vodPhonevideo.getDramaname() + "......");
			try {
				VodPhonevideoMapper.insert(vodPhonevideo);
			} catch (Exception e) {
				log.info("子集添加异常: " + vodPhonevideo.getId());
				continue;
			}
		}
	}
	
	//手机频道添加
	@Override
	public void Addphonechannel(Integer id, String channelName, Integer channelCode) throws Exception {
		//频道添加
		vodChannelMapper.insertPrimary(id,channelName,channelCode);
	}

	@Override
	public void Addphonelabel(Integer id, String name, Integer channelCode, Integer level) throws Exception {
		//标签添加
		vodLabelMapper.insertPrimary(id, name, channelCode, level);
	}

	@Override
	public int UpdateSeriesCode() {
		
		return VodPhonevideoMapper.updateSeriesCode();	
	}

}
