package com.pbtd.playclick.guoguang.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.albumstorage.mapper.AlbumStrategyMapper;
import com.pbtd.playclick.albumstorage.service.impl.AlbumStrategyService;
import com.pbtd.playclick.guoguang.mapper.CibnStorageMapper;
import com.pbtd.playclick.guoguang.service.ICibnStorageService;
import com.pbtd.playclick.integrate.controller.FileUpload;
import com.pbtd.playclick.integrate.mapper.VodAlbuminfoMapper;

@Service
public class CibnStorageService implements ICibnStorageService {
	@Autowired
	private  CibnStorageMapper cibnmapper;
	@Autowired
	private AlbumStrategyService strategyService;
	@Autowired
	private AlbumStrategyMapper  strategyMapper; 
	@Autowired
	private FileUpload fielupdate;
	@Autowired
	private VodAlbuminfoMapper vodalbummapper;
	@Override
	public int  albumStorage(Map<String, Object> map) {
		cibnmapper.deletealbumStorage(map);
		cibnmapper.deletealbumvideoStorage(map);
		cibnmapper.inseralbumStorage(map);
		cibnmapper.inseralbumvideoStorage(map);
		cibnmapper.updatealbumStorage(map);
		cibnmapper.updatealbumvideoStorage(map);
		return 1;
	}

	@Override
	public int albumStorageid(Map<String, Object> map) {
		//查询专辑是否存在
		int c=vodalbummapper.loadvodalbum(map);
		if(c<1){//不存在则添加 存在则更新当前剧集
			cibnmapper.inservodalbumid(map);
			//更新标签映射关系及导演演员id  
			updatevodmapping(map);
			//更新拼音
			strategyService.pinyinid(map);
			//爬取图片 
			fielupdate.imguid(map.get("seriescode").toString());
		}else{
			cibnmapper.updatevodalbumid(map);
		}
		//更新专辑为已入库状态
		cibnmapper.updatevodalbumstotageid(map);
		
		//增加剧集
		cibnmapper.insertvideoid(map);
		// 更新剧集 专辑唯一标识 
		cibnmapper.updatevideobycentralid(map);
		//更新剧集为已入库状态
		cibnmapper.updatevodalbumvideostotageid(map);
		return 0;
	}

	//更新映射关系及导演演员id  
	public void updatevodmapping(final Map<String, Object> queryParams) {
		queryParams.put("cpcode", "2");
		List<Map<String, Object>> maps=strategyMapper.findvodmapping(queryParams);
		for (Map<String, Object> map : maps) {
			try {
				map.put("seriescode",queryParams.get("seriescode").toString());
				cibnmapper.updatevodmapping(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			// 更新演员导演
			cibnmapper.updateactors(queryParams);
		} catch (IllegalArgumentException ec) {
			ec.printStackTrace();
		}
	}


}
