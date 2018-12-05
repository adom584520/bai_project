package com.pbtd.playclick.heyi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.albumstorage.mapper.AlbumStrategyMapper;
import com.pbtd.playclick.albumstorage.service.impl.AlbumStrategyService;
import com.pbtd.playclick.heyi.mapper.GuttvStorageMapper;
import com.pbtd.playclick.heyi.service.IGuttvStorageService;
import com.pbtd.playclick.integrate.controller.FileUpload;
import com.pbtd.playclick.integrate.mapper.VodAlbuminfoMapper;
@Service
public class GuttvStorageService implements IGuttvStorageService {
	@Autowired
	private GuttvStorageMapper guttvmapper;
	@Autowired
	private VodAlbuminfoMapper vodalbummapper;
	@Autowired
	private AlbumStrategyService strategyService;
	@Autowired
	private AlbumStrategyMapper  strategyMapper; 
	@Autowired
	private FileUpload fielupdate;

	@Override
	public int albumStorage(Map<String, Object> map) {
		guttvmapper.deletealbumStorage(map);
		guttvmapper.deletealbumvideoStorage(map);
		guttvmapper.inseralbumStorage(map);
		guttvmapper.inseralbumvideoStorage(map);
		guttvmapper.updatealbumStorage(map);
		guttvmapper.updatealbumvideoStorage(map);
		return 1;
	}

	@Override
	public int albumStorageid(Map<String, Object> map) {
		//查询专辑是否存在
		int c=vodalbummapper.loadvodalbum(map);
		if(c<1){//不存在则添加 存在则更新当前剧集
			guttvmapper.inservodalbumid(map);
			//更新标签映射关系及导演演员id  
			updatevodmapping(map);
			//更新拼音
			strategyService.pinyin(map);
			//爬取图片 
			fielupdate.imguid(map.get("seriescode").toString());
		}else{
			guttvmapper.updatevodalbumid(map);
		}
		//更新专辑为已入库状态
		guttvmapper.updatevodalbumstotageid(map);
		
		//增加剧集
		guttvmapper.insertvideoid(map);
		// 更新剧集 专辑唯一标识 
		guttvmapper.updatevideobycentralid(map);
		//更新剧集为已入库状态
		guttvmapper.updatevodalbumvideostotageid(map);
		return 0;
	}

	//更新映射关系及导演演员id  
	public void updatevodmapping(final Map<String, Object> queryParams) {
		queryParams.put("cpcode", "4");
		List<Map<String, Object>> maps=strategyMapper.findvodmapping(queryParams);
		for (Map<String, Object> map : maps) {
			map.put("seriescode",queryParams.get("seriescode").toString());
			try {
				guttvmapper.updatevodmapping(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			// 更新演员导演
			guttvmapper.updateactors(queryParams);
		} catch (IllegalArgumentException ec) {
			ec.printStackTrace();
		}
	}


}
