package com.pbtd.manager.vod.tv.special.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.tv.special.domain.VodTvSpecial;
import com.pbtd.manager.vod.tv.special.mapper.VodTvSpecialMapper;
import com.pbtd.manager.vod.tv.special.service.face.IVodTvSpecialService;

@Service
public class VodTvSpecialService implements IVodTvSpecialService {

	@Autowired
	private VodTvSpecialMapper vodSpecialMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.count(queryParams);
	}

	@Override
	public List<VodTvSpecial> page(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.page(queryParams);
	}

	@Override
	public List<VodTvSpecial> find(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.generatePosition(queryParams);
	}

	@Override
	public VodTvSpecial load(int id) {
		
		return vodSpecialMapper.load(id);
	}

	@Override
	public int insert(VodTvSpecial vodSpecial) {
		return vodSpecialMapper.insert(vodSpecial);
	}

	@Override
	public int update(VodTvSpecial vodSpecial) {
		return vodSpecialMapper.update(vodSpecial);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return vodSpecialMapper.deletes(ids);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return vodSpecialMapper.updateStatus(map);
	}

	@Override
	public int insertalbum(Map<String, Object> map) {
		
		return vodSpecialMapper.insertalbum(map);
	}

	@Override
	public int updatealbumsequence(Map<String, Object> map) {
		
		return vodSpecialMapper.updatealbumsequence(map);
	}

	@Override
	public int deletesalbum(Map<String, Object> map) {
		
		return vodSpecialMapper.deletesalbum(map);
	}

	@Override
	public int countalbum(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.countalbum(queryParams);
	}

	@Override
	public List<Map<String, Object>> pagealbum(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.pagealbum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findpagealbum(Map<String, Object> queryParams) {
		return vodSpecialMapper.findpagealbum(queryParams);
	}

	@Override
	public int updateimg(VodTvSpecial vodSpecial) {
		return vodSpecialMapper.updateimg(vodSpecial);
	}

	@Override
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return vodSpecialMapper.findalbumsequence(map);
	}

	@Override
	public Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map) {
		return vodSpecialMapper.findalbummaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams) {
		return vodSpecialMapper.findalbumsequencesum(queryParams);
	}

	@Override
	public Map<String, Object> findmaxVSminsequence(Map<String, Object> map) {
		return vodSpecialMapper.findmaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams) {
		return vodSpecialMapper.findsequencesum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findspecialsequence(Map<String, Object> map) {
		return vodSpecialMapper.findspecialsequence(map);
	}

}
