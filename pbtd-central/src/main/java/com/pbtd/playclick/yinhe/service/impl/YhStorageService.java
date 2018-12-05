package com.pbtd.playclick.yinhe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.albumstorage.mapper.AlbumStrategyMapper;
import com.pbtd.playclick.albumstorage.service.impl.AlbumStrategyService;
import com.pbtd.playclick.integrate.controller.FileUpload;
import com.pbtd.playclick.integrate.mapper.VodAlbuminfoMapper;
import com.pbtd.playclick.yinhe.mapper.YhStorageMapper;
import com.pbtd.playclick.yinhe.service.IYhStorageService;
@Service
public class YhStorageService implements IYhStorageService {
	@Autowired
	private YhStorageMapper yhmapper;
	@Autowired
	private AlbumStrategyService strategyService;
	@Autowired
	private AlbumStrategyMapper  strategyMapper; 
	@Autowired
	private FileUpload fielupdate;
	@Autowired
	private VodAlbuminfoMapper vodalbummapper;
	@Override
	public int albumStorage(Map<String, Object> map) {
		  yhmapper.deletealbumStorage(map);
		  yhmapper.deletealbumvideoStorage(map);
		  yhmapper.inseralbumStorage(map);
		  yhmapper.inseralbumvideoStorage(map);
		  yhmapper.updatealbumStorage(map);
		  yhmapper.updatealbumvideoStorage(map);
		 return 1;
	}

	@Override
	public int albumStorageid(Map<String, Object> map) {
		//查询专辑是否存在
		int c=vodalbummapper.loadvodalbum(map);
		if(c<1){//不存在则添加 存在则更新当前剧集
			yhmapper.inservodalbumid(map);
			//更新标签映射关系及导演演员id  
			updatevodmapping(map);
			//更新拼音
			strategyService.pinyin(map);
			//爬取图片 
			fielupdate.imguid(map.get("seriescode").toString());
		}else{
			yhmapper.updatevodalbumid(map);
		}
		//更新专辑为已入库状态
		yhmapper.updatevodalbumstotageid(map);
		
		//增加剧集
		yhmapper.insertvideoid(map);
		// 更新剧集 专辑唯一标识 
		yhmapper.updatevideobycentralid(map);
		//更新剧集为已入库状态
		yhmapper.updatevodalbumvideostotageid(map);
		return 0;
	}

	//更新映射关系及导演演员id  
	public void updatevodmapping(final Map<String, Object> queryParams) {
		queryParams.put("cpcode", "1");
		List<Map<String, Object>> maps=strategyMapper.findvodmapping(queryParams);
		for (Map<String, Object> map : maps) {
			map.put("seriescode",queryParams.get("seriescode").toString());
			try {
				yhmapper.updatevodmapping(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			// 更新演员导演
			yhmapper.updateactors(queryParams);
		} catch (IllegalArgumentException ec) {
			ec.printStackTrace();
		}
	}


}
