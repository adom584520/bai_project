package com.pbtd.manager.vod.phone.hotseries.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.hotseries.domain.VodHotseries;
import com.pbtd.manager.vod.phone.hotseries.mapper.VodHotseriesMapper;


@Service
public class VodHotseriesService implements IVodHotseriesService{

	@Autowired
	private VodHotseriesMapper hotseriesMapper;
	
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> queryParams) {
		return hotseriesMapper.find(queryParams);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return hotseriesMapper.count(queryParams);
	}

	@Override
	public int add(Map<String, Object> queryParams) {
		return hotseriesMapper.add(queryParams);
	}

	@Override
	public int modify(Map<String, Object> queryParams) {
		return hotseriesMapper.modify(queryParams);
	}

	@Override
	public int deletes(Map<String, Object> queryParams) {
		return hotseriesMapper.deletes(queryParams);
	}


	@Override
	public VodHotseries load(int id) {
		return hotseriesMapper.load(id);
	}


	@Override
	public int addalbuminfo(Map<String, Object> queryParams) {
		return hotseriesMapper.addalbuminfo(queryParams);
	}


	@Override
	public int updatesequence(Map<String, Object> queryParams) {
		return hotseriesMapper.updatesequence(queryParams);
	}


	@Override
	public int deletealbuminfo(Map<String, Object> queryParams) {
		return hotseriesMapper.deletealbuminfo(queryParams);
	}


	@Override
	public int updateStatus(Map<String, Object> queryParams) {
		return hotseriesMapper.updateStatus(queryParams);
	}


	@Override
	public List<Map<String, Object>> pagealbum(Map<String, Object> queryParams) {
		return hotseriesMapper.pagealbum(queryParams);
	}


	@Override
	public int countalbum(Map<String, Object> queryParams) {
		return hotseriesMapper.countalbum(queryParams);
	}


	@Override
	public int insertjson(Map<String, Object> queryParams) {
		return hotseriesMapper.insertjson(queryParams);
	}


	@Override
	public Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map) {
		return hotseriesMapper.findalbummaxVSminsequence(map);
	}


	@Override
	public List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams) {
		return hotseriesMapper.findalbumsequencesum(queryParams);
	}


	@Override
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return hotseriesMapper.findalbumsequence(map);
	}
}
