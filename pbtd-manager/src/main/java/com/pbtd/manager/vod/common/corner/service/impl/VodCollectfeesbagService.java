package com.pbtd.manager.vod.common.corner.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.common.corner.domain.VodCollectfeesbag;
import com.pbtd.manager.vod.common.corner.mapper.VodCollectfeesbagMapper;
import com.pbtd.manager.vod.common.corner.service.face.IVodCollectfeesbagService;
@Service
public class VodCollectfeesbagService implements IVodCollectfeesbagService {

	@Autowired
	private VodCollectfeesbagMapper vodCollectfeesbagMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodCollectfeesbagMapper.count(queryParams);
	}

	@Override
	public List<VodCollectfeesbag> page(Map<String, Object> queryParams) {
		
		return vodCollectfeesbagMapper.page(queryParams);
	}

	@Override
	public List<VodCollectfeesbag> find(Map<String, Object> queryParams) {
		
		return vodCollectfeesbagMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodCollectfeesbagMapper.generatePosition(queryParams);
	}

	@Override
	public VodCollectfeesbag load(int id) {
		
		return vodCollectfeesbagMapper.load(id);
	}

	@Override
	public int insert(VodCollectfeesbag vodCollectfeesbag) {
		
		return vodCollectfeesbagMapper.insert(vodCollectfeesbag);
	}

	@Override
	public int update(VodCollectfeesbag vodCollectfeesbag) {
		
		return vodCollectfeesbagMapper.update(vodCollectfeesbag);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return vodCollectfeesbagMapper.deletes(ids);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return vodCollectfeesbagMapper.updateStatus(map);
	}

	@Override
	public int countCollectfeesbag(Map<String, Object> queryParams) {
		
		return vodCollectfeesbagMapper.countCollectfeesbag(queryParams);
	}
	
	@Override
	public List<VodCollectfeesbag> findCollectfeesbag(Map<String, Object> queryParams) {
		
		return vodCollectfeesbagMapper.findCollectfeesbag(queryParams);
	}
}
