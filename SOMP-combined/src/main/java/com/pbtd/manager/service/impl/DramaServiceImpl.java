package com.pbtd.manager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.Drama;
import com.pbtd.manager.mapper.DramaMapper;
import com.pbtd.manager.service.DramaService;

@Service
public class DramaServiceImpl implements DramaService {
	@Autowired
	private DramaMapper dramaMapper;

	@Override
	public List<Drama> queryBySeriesCode(Long seriesCode) {
		return dramaMapper.queryBySeriesCode(seriesCode);
	}

	@Override
	public void insertList(List<HashMap> list) {
		for (int i = 0; i < list.size(); i++) {
			HashMap hashMap = list.get(i);
			Long seriesCode = Long.valueOf(hashMap.get("seriesCode") + "");
			Integer drama = (Integer) hashMap.get("drama");
			Integer isShow = (Integer) hashMap.get("isShow");
			//确实要上线
			if (isShow == 1) {
				continue;
			}
			//查询，有则更新，无则插入
			Drama dramaObj = dramaMapper.queryBySeriesCodeAndDrama(seriesCode, drama);
			if (dramaObj == null) {
				Drama dramaObj2 = new Drama();
				dramaObj2.setCreateTime(new Date());
				dramaObj2.setDescription((String) hashMap.get("description"));
				dramaObj2.setDrama(drama);
				dramaObj2.setDramaname((String) hashMap.get("dramaname"));
				dramaObj2.setDuration((String) hashMap.get("duration"));
				dramaObj2.setSeriesCode(seriesCode);
				dramaObj2.setType((String) hashMap.get("type"));
				dramaObj2.setUpdateTime(new Date());
				dramaMapper.insert(dramaObj2);
			} else {
				dramaObj.setDescription((String) hashMap.get("description"));
				dramaObj.setDramaname((String) hashMap.get("dramaname"));
				dramaObj.setDuration((String) hashMap.get("duration"));
				dramaObj.setSeriesCode(seriesCode);
				dramaObj.setType((String) hashMap.get("type"));
				dramaObj.setUpdateTime(new Date());
				dramaMapper.update(dramaObj);
			}
		}

	}
}
