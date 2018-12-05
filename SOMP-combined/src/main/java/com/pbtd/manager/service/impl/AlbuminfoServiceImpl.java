package com.pbtd.manager.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.Albuminfo;
import com.pbtd.manager.mapper.AlbuminfoMapper;
import com.pbtd.manager.query.AlbuminfoQueryObject;
import com.pbtd.manager.service.AlbuminfoService;
import com.pbtd.manager.util.AlbuminfoConstant;
import com.pbtd.manager.util.PageResult;

@Service
public class AlbuminfoServiceImpl implements AlbuminfoService {
	@Autowired
	private AlbuminfoMapper albuminfoMapper;

	@Override
	public PageResult queryList(AlbuminfoQueryObject qo) {
		Long count = albuminfoMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Albuminfo> data = albuminfoMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	public void insertList(List<HashMap> list, Integer type) {
		for (int i = 0; i < list.size(); i++) {
			HashMap hashMap = list.get(i);
			Long seriesCode = ((Integer) hashMap.get("seriesCode")) + 0L;
			Integer status = null;
			if(hashMap.get("status") instanceof Integer){
				status = (Integer) hashMap.get("status");
			}else{
				status = Integer.valueOf((String) hashMap.get("status"));
			}
			Albuminfo albuminfo = albuminfoMapper.queryBySeriesCode(seriesCode);
			if (albuminfo == null) {
				Albuminfo albuminfo2 = new Albuminfo();
				albuminfo2.setActorName((String) hashMap.get("actorName"));
				albuminfo2.setDeviceType(type);
				albuminfo2.setCreateTime(new Date());
				albuminfo2.setCurrentNum((Integer) hashMap.get("currentNum"));
				albuminfo2.setDescription((String) hashMap.get("description"));
				albuminfo2.setDuration((String) hashMap.get("duration"));
				albuminfo2.setOrgairDate((String) hashMap.get("orgairDate"));
				albuminfo2.setOriginalCountry((String) hashMap.get("originalCountry"));
				albuminfo2.setOriginalName((String) hashMap.get("originalName"));
				albuminfo2.setReleaseYear((String) hashMap.get("releaseYear"));
				albuminfo2.setSeriesCode(seriesCode);
				albuminfo2.setSeriesName((String) hashMap.get("seriesName"));
				albuminfo2.setTag((String) hashMap.get("tag"));
				albuminfo2.setUpdateTime(new Date());
				albuminfo2.setViewPoint((String) hashMap.get("viewPoint"));
				albuminfo2.setVolumnCount((Integer) hashMap.get("volumnCount"));
				albuminfo2.setWriterName((String) hashMap.get("writerName"));
				albuminfoMapper.insert(albuminfo2);
			} else {
				if(status!=1){
					//当专辑同步为下线状态，修改专辑下线
					if(AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_ALL.equals(albuminfo.getDeviceType())){
						albuminfo.setDeviceType(AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_PHONE.equals(type)?AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_TV:AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_PHONE);
					}else if(type.equals(albuminfo.getDeviceType())||AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_NOT.equals(albuminfo.getDeviceType())){
						/**
						 * albumninfo中，Tv在线或都不在线，那么下线
						 * 其他不处理
						 */
						albuminfo.setDeviceType(AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_NOT);
					}
				}else{
					//都不在线   或者当前在线的自己
					if (AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_NOT.equals(albuminfo.getDeviceType())||type.equals(albuminfo.getDeviceType())) {
						albuminfo.setDeviceType(type);
					} else {
						albuminfo.setDeviceType(AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_ALL);
					}
				}
				albuminfo.setActorName((String) hashMap.get("actorName"));
				albuminfo.setCurrentNum((Integer) hashMap.get("currentNum"));
				albuminfo.setDescription((String) hashMap.get("description"));
				albuminfo.setDuration((String) hashMap.get("duration"));
				albuminfo.setOrgairDate((String) hashMap.get("orgairDate"));
				albuminfo.setOriginalCountry((String) hashMap.get("originalCountry"));
				albuminfo.setOriginalName((String) hashMap.get("originalName"));
				albuminfo.setReleaseYear((String) hashMap.get("releaseYear"));
				albuminfo.setSeriesName((String) hashMap.get("seriesName"));
				albuminfo.setTag((String) hashMap.get("tag"));
				albuminfo.setUpdateTime(new Date());
				albuminfo.setViewPoint((String) hashMap.get("viewPoint"));
				albuminfo.setVolumnCount((Integer) hashMap.get("volumnCount"));
				albuminfo.setWriterName((String) hashMap.get("writerName"));
				albuminfoMapper.update(albuminfo);
			}
		}
	}

	@Override
	public Albuminfo queryBySeriesCode(Long seriesCode) {
		return albuminfoMapper.queryBySeriesCode(seriesCode);
	}

	@Override
	public List<Albuminfo> queryBySeriesName(String seriesName) {
		return albuminfoMapper.queryBySeriesName(seriesName);
	}
}
