package com.pbtd.manager.vod.tv.album.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.vod.tv.album.domain.JSONEmptyObject;
import com.pbtd.manager.vod.tv.album.domain.VodTvAlbuminfoReturn;
import com.pbtd.manager.vod.tv.album.service.face.IVodTvAlbuminfoService;

@RestController
@RequestMapping("/tv/vod/albuminfo")
public class AlbuminfoFuseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlbuminfoFuseController.class);
	@Autowired
	private IVodTvAlbuminfoService vodTvAlbuminfoService;

	@RequestMapping("validateSeriesCode")
	public String validateSeriesCode(String seriesCode) {
		LOGGER.info(seriesCode);
		HashMap<String, Object> map = new HashMap<>();
		VodTvAlbuminfoReturn ar = null;
		try {
			ar = vodTvAlbuminfoService.querySeriesCodeAndName(seriesCode);
			if (ar != null) {
				map.put("code", 1);
				map.put("message", "查询成功！");
				map.put("data", ar);
			} else {
				map.put("code", 0);
				map.put("message", "该专辑不存在中心数据库中！");
				map.put("data", JSONEmptyObject.empty);
			}
		} catch (Exception e) {
			LOGGER.error("中心平台对接移固融合校验专辑接口出错！", e);
			map.put("code", 0);
			map.put("message", "中心平台对接移固融合校验专辑接口出错！");
			map.put("data", JSONEmptyObject.empty);
		}
		return JSON.toJSONString(map);
	}
}
