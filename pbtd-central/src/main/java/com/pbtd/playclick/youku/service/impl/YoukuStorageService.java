package com.pbtd.playclick.youku.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.albumstorage.mapper.AlbumStrategyMapper;
import com.pbtd.playclick.albumstorage.service.impl.AlbumStrategyService;
import com.pbtd.playclick.integrate.controller.FileUpload;
import com.pbtd.playclick.integrate.domain.VodAlbuminfo;
import com.pbtd.playclick.integrate.mapper.StrategyMapper;
import com.pbtd.playclick.integrate.mapper.VodAlbuminfoMapper;
import com.pbtd.playclick.pinyin.service.PinYinService;
import com.pbtd.playclick.util.PinYinUtil;
import com.pbtd.playclick.youku.mapper.YoukuStorageMapper;
import com.pbtd.playclick.youku.service.face.IYoukuStorageService;
@Service
public class YoukuStorageService implements IYoukuStorageService {


	@Autowired
	private YoukuStorageMapper youkustotagemapper;
	@Autowired
	private StrategyMapper vodstrategyMapper;
	@Autowired
	private VodAlbuminfoMapper  vodalbuminfoMapper ;
	@Autowired
	private AlbumStrategyService strategyService;
	@Autowired
	private FileUpload fielupdate;
	//根据id入库 
	@Override
	public void insertsisStorageid(Map<String, Object> queryParams) {
		//查询是否存在
		int m=youkustotagemapper.loadvodalbum(queryParams);
		if(m<1){
			youkustotagemapper.insertsisStorageid(queryParams);
			youkustotagemapper.updateisStorageyouku(queryParams);
			//标签映射
			updatevodmappingid(queryParams);
			//更改优酷地区年代标签
			updatealbumareaYear(queryParams);
			//更新拼音
			strategyService.pinyinid(queryParams);
		} 
		try {
			youkustotagemapper.insertisStoragevideoyoukid(queryParams);
			youkustotagemapper.updatevideoisStorageyouku(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//爬取图片 
		fielupdate.imguid(queryParams.get("seriescode").toString());
		youkustotagemapper.updatealbuminfovideobycentral(queryParams);
	}
	//根据id标签映射
	public void updatevodmappingid(Map<String, Object> queryParams) {
		queryParams.put("cpcode", "5");
		List<Map<String, Object>> maps=vodstrategyMapper.findvodmapping(queryParams);
		youkustotagemapper.updateactorsid(queryParams);
		youkustotagemapper.updatealbuminfocurrentNum(queryParams);
		for (Map<String, Object> map : maps) {
			map.put("id", queryParams.get("id"));
			youkustotagemapper.updatevodmappingid(map);
		}

	}
	
	
	 //根据id更新 地区年代标签关联
	public void updatealbumareaYear(Map<String, Object> queryParams) {
		queryParams.put("cpcode", "5");
		List<Map<String, Object>> maps=youkustotagemapper.findyouk_areaYear(queryParams);
		for (Map<String, Object> map : maps) {
			try {
				map.put("id", queryParams.get("id"));
				youkustotagemapper.updatealbumareaYear(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//自动更新入库
	@Override
	public int saveStrategyyouku(Map<String, Object> queryParams) {
		//自动更新优酷数据入临时库youk_vod_albuminfo_strategy
		youkustotagemapper.deleteyoukualbum_strategy(queryParams);
		youkustotagemapper.deleteyoukualbumvideo_strategy(queryParams);
		youkustotagemapper.insertyoukualbum_strategy(queryParams);
		youkustotagemapper.insertyoukualbumvideo_strategy(queryParams);
		//更新映射关系及最新集数
		updatevodmapping(queryParams);
		//更新地区年代标签
		updateyouk_areaYear(queryParams);
		//更新汇聚拼音
		pinyin(queryParams); 
		//下载cp源图片数据 更新至汇聚中
	    fielupdate.importyouku_album_strategy();
       // 开始入正式库 专辑
		youkustotagemapper.insertyoukualbum(queryParams);
		//更改最新剧集数
		youkustotagemapper.updatealbumcurnum (queryParams);
		//更新入库状态
		youkustotagemapper.updateisStorageyouku(queryParams);
		//剧集入正式库
		youkustotagemapper.insertyoukualbumvideo(queryParams);
		//剧集入库状态
		youkustotagemapper.updatevideoisStorageyouku(queryParams);
		//剧集填充己方专辑id
		youkustotagemapper.updatealbuminfovideobycentral(null);
		return 1;
	}
	 
	 //自动更新 标签映射关系 演员id
	public void updatevodmapping(Map<String, Object> queryParams) {
		queryParams.put("cpcode", "5");
		List<Map<String, Object>> maps=vodstrategyMapper.findvodmapping(queryParams);
		for (Map<String, Object> map : maps) {
			try {
				youkustotagemapper.updatevodmapping_strategy(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			//更新演员id
			youkustotagemapper.updateactors_strategy(queryParams);
		} catch (IllegalArgumentException ec) {
			ec.printStackTrace();
		}
	}
	 //自动更新 地区年代标签关联
		public void updateyouk_areaYear(Map<String, Object> queryParams) {
			queryParams.put("cpcode", "5");
			List<Map<String, Object>> maps=youkustotagemapper.findyouk_areaYear(queryParams);
			for (Map<String, Object> map : maps) {
				try {
					youkustotagemapper.updateyouk_areaYear(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	//自动更新 拼音转换
	public void pinyin(Map<String, Object> queryParams ){
		List<Map<String, Object>> maps=	youkustotagemapper.findvodalbum_strategy(queryParams);
		for (Map<String, Object> map : maps) {
			if (map.get("PINYIN") == null || "".equals(map.get("PINYIN"))) {
				String name = map.get("SERIESNAME").toString();
				// 清除掉所有特殊字符
				String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name);
				name = m.replaceAll("").trim();
				String pingYin = PinYinUtil.getPingYin(name).toLowerCase();
				String headChar = PinYinUtil.getPinYinHeadChar(name).toLowerCase();
				map.put("pinyin", pingYin);
				map.put("pinyinsuoxie",headChar);
				try {
					youkustotagemapper.updatevodalbumpinyin_strategy(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public int updatevod_albumissue(Map<String, Object> queryParams) {
		youkustotagemapper.updatevod_albumissue(queryParams);
		return 0;
	}

}
