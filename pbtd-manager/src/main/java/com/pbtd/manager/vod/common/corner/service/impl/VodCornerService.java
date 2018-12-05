package com.pbtd.manager.vod.common.corner.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.common.corner.domain.VodCorner;
import com.pbtd.manager.vod.common.corner.mapper.VodCornerMapper;
import com.pbtd.manager.vod.common.corner.service.face.IVodCornerService;
@Service
public class VodCornerService implements IVodCornerService {

	
	@Autowired
	private VodCornerMapper vodCornerMapper;
	@Override
	public int count(Map<String, Object> queryParams) {
	
		return vodCornerMapper.count(queryParams);
	}

	@Override
	public List<VodCorner> page(Map<String, Object> queryParams) {
	
		return vodCornerMapper.page(queryParams);
	}

	@Override
	public List<VodCorner> find(Map<String, Object> queryParams) {
	
		return vodCornerMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
	
		return vodCornerMapper.generatePosition(queryParams);
	}

	@Override
	public VodCorner load(int id) {
	
		return vodCornerMapper.load(id);
	}

	@Override
	public int insert(VodCorner vodCorner) {
	
		return vodCornerMapper.insert(vodCorner);
	}

	@Override
	public int update(VodCorner vodCorner) {
	
		return vodCornerMapper.update(vodCorner);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
	
		return vodCornerMapper.deletes(ids);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return vodCornerMapper.updateStatus(map);
	}

	@Override // 角标count
	public int countCorner(Map<String, Object> queryParams) {

		return vodCornerMapper.countCorner(queryParams);
	}

	@Override
	public List<VodCorner> findCorner(Map<String, Object> queryParams) {

		return vodCornerMapper.findCorner(queryParams);
	}
}
