package com.pbtd.playclick.albumstorage.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.albumstorage.mapper.AlbumStrategyMapper;
import com.pbtd.playclick.albumstorage.service.face.IAlbumStrategyService;
import com.pbtd.playclick.integrate.controller.FileUpload;
import com.pbtd.playclick.integrate.domain.Strategy;
import com.pbtd.playclick.pinyin.service.PinYinService;
import com.pbtd.playclick.util.PinYinUtil;
@Service
public class AlbumStrategyService implements IAlbumStrategyService {
	@Autowired
	private PinYinService pinYinService;
	@Autowired
	private FileUpload fielupdate;
	@Autowired
	private AlbumStrategyMapper strategyMapper;



	@Override
	public int updateStrategalbum(Map<String, Object> queryParams) {
		//更新标签映射关系 演员id
		updatevodmapping(queryParams);
		//pinyin 拼音转换
		pinyin(queryParams);
		//下载cp源图片数据 更新至汇聚中
		fielupdate.imgalbuminfo_strategy();
		//根据规则
		List<Strategy>   list=strategyMapper.findstrategy(queryParams);
		if(list.size()>0){ 
			String name="";
			for (Strategy st : list) {
				name="";
				if(st.getField1()!=null && st.getField1()!="" && !st.getField1().isEmpty())	name+=","+st.getField1();
				if(st.getField2()!=null && st.getField2()!="" && !st.getField2().isEmpty())  name+=","+st.getField2();
				if(st.getField3()!=null && st.getField3()!="" && !st.getField3().isEmpty()) name+=","+st.getField3();
				if(st.getField4()!=null && st.getField4()!="" && !st.getField4().isEmpty()) name+=","+st.getField4();
				queryParams.put("name", name.substring(1, name.length()));
				queryParams.put("cpcode", st.getCpcode());
				//非唯一汇聚数据
				strategyMapper.insertonlyonefalse(queryParams);
				strategyMapper.updateonlyonefalsestatus1(queryParams);
				strategyMapper.updateonlyonefalsestatus3(queryParams);
			}
		}
		//唯一汇聚数据
		strategyMapper.insertonlyone(queryParams);
		strategyMapper.updateonlyonestatus(queryParams);
		//增量添加播放剧集到汇聚库
		strategyMapper.insertvodalbuminfovideo(queryParams);
		//更新更改数据的剧集数
		strategyMapper.updatealbuminfocurrentNum(queryParams);
		 //更新剧集中的自有专辑id
		strategyMapper.updatevideobycentralid(queryParams);
		return 1;
	}	
	//更新映射关系及导演演员id  
	public void updatevodmapping(final Map<String, Object> queryParams) {

		List<Map<String, Object>> maps=strategyMapper.findvodmapping(queryParams);
		for (Map<String, Object> map : maps) {
			try {
				strategyMapper.updatevodmappingphone(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			// 更新演员导演
			strategyMapper.updateactors(queryParams);
		} catch (IllegalArgumentException ec) {
			ec.printStackTrace();
		}
	}
	//拼音转换
	public void pinyin(Map<String, Object> queryParams ){
		List<Map<String, Object>> maps=	strategyMapper.findalbum(queryParams);
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
					strategyMapper.updatealbumpinyin(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	//根据id拼音转换
	public void pinyinid(Map<String, Object> queryParams ){
		List<Map<String, Object>> maps=	strategyMapper.findvodalbumid(queryParams);
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
					strategyMapper.updatevodalbumpinyinid(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
