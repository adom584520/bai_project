package com.pbtd.manager.vod.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.system.domain.Textrecommendation;
import com.pbtd.manager.vod.system.mapper.TextrecommendationMapper;
import com.pbtd.manager.vod.system.service.face.ITextrecommendationService;

@Service
public class TextrecommendationImpl implements ITextrecommendationService {

	@Autowired
	private TextrecommendationMapper mapper;

	@Override
	public int insert(Textrecommendation map) {
		
		return mapper.insert(map);
	}

	@Override
	public int update(Textrecommendation map) {
		
		return mapper.update(map);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		
		return mapper.updateStatus(map);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return mapper.deletes(ids);
	}

	@Override
	public List<Textrecommendation> page(Map<String, Object> queryParams) {
		
		return mapper.page(queryParams);
	}

	@Override
	public List<Textrecommendation> find(Map<String, Object> queryParams) {
		
		return mapper.find(queryParams);
	}

	@Override
	public Textrecommendation load(int id) {
		
		return mapper.load(id);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		
		return mapper.count(queryParams);
	}

	@Override
	public int insertjson(Textrecommendation map) {
		return mapper.insertjson(map);
	}

	@Override
	public int updateimg(Map<String, Object> Map) {
		return mapper.updateimg(Map);
	}
 

}
