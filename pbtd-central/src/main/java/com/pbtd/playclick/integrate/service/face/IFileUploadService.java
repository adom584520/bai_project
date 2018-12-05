package com.pbtd.playclick.integrate.service.face;

import java.util.Map;

public interface IFileUploadService {
	//国广银河 合一 自动入库 专辑和剧集图片爬取
	int importvod_album_strategy(Map<String,Object> params);

	//优酷 自动入库 专辑和剧集图片爬取
	int importyouku_album_strategy(Map<String,Object> params);

	//入库后的数据根据id爬取图片
	int importvod_albumid(Map<String,Object> params);

	void importalbumvideo(Map<String, Object> params, String path);


}
