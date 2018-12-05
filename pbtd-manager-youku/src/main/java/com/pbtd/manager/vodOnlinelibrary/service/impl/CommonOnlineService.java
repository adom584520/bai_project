package com.pbtd.manager.vodOnlinelibrary.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.common.actors.domain.Vodactors;
import com.pbtd.manager.vod.common.corner.domain.VodCollectfeesbag;
import com.pbtd.manager.vod.common.corner.domain.VodCorner;
import com.pbtd.manager.vod.page.mapper.ResetMapper;
import com.pbtd.manager.vodOnlinelibrary.mapper.CommonOnlineMapper;
import com.pbtd.manager.vodOnlinelibrary.service.face.ICommonOnlineService;
@Service
public class CommonOnlineService implements ICommonOnlineService {
	@Autowired
	private ResetMapper resetmpper;
	
	@Autowired
	private  CommonOnlineMapper commonMapper;
	

	Date d = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String curtime = sdf.format(d);

	@Override
	public int resetactors(Map<String, Object> map) {
		map.put("updatetime", curtime);
		List<Map<String, Object>> list=resetmpper.findactors(map);
		if(list.size()>0){
			for (Map<String, Object> curmap : list) {
				    commonMapper.deletesactors(curmap);
					commonMapper.insertactors(curmap);
			}
		}
		return 1;
	}

	@Override
	public int resetcorner(Map<String, Object> map) {
		map.put("updatetime", curtime);
		List<Map<String, Object>> list=resetmpper.findcorner(map);
		if(list.size()>0){
			for (Map<String, Object> curmap : list) {
				commonMapper.deletescorner(curmap);
				if(curmap.get("status").toString().equals("1")){
					commonMapper.insertcorner(curmap);
				} 
				
			}
		}
		return 1;
	}

	@Override
	public int restcollectfeesbag(Map<String, Object> map) {
		map.put("updatetime", curtime);
		List<Map<String, Object>> list=resetmpper.findcollectfeesbag(map);
		if(list.size()>0){
			for (Map<String, Object> curmap : list) {
				commonMapper.deletesCollectfeesbag(curmap);
				if(curmap.get("status").toString().equals("1")){
					commonMapper.insertCollectfeesbag(curmap);
				} 
				
			}
		}
		return 1;
	}
	 
}
