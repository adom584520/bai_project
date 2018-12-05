package com.pbtd.manager.vod.phone.special.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.special.domain.VodSpecial;
import com.pbtd.manager.vod.phone.special.mapper.VodSpecialMapper;
import com.pbtd.manager.vod.phone.special.service.face.IVodSpecialService;
@Service
public class VodSpecialService implements IVodSpecialService {

	@Autowired
	private VodSpecialMapper vodSpecialMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.count(queryParams);
	}

	@Override
	public List<VodSpecial> page(int start, int limit, Map<String, Object> queryParams) {
		
		return vodSpecialMapper.page(start, limit, queryParams);
	}

	@Override
	public List<VodSpecial> find(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.generatePosition(queryParams);
	}

	@Override
	public VodSpecial load(int id) {
		
		return vodSpecialMapper.load(id);
	}

	@Override
	public int insert(VodSpecial vodSpecial) {
	
		return vodSpecialMapper.insert(vodSpecial);
	}

	@Override
	public int update(VodSpecial vodSpecial) {
		return vodSpecialMapper.update(vodSpecial);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return vodSpecialMapper.deletes(ids);
	}

	@Override
	public int updatezt(int id,int status) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("status", status);
		return vodSpecialMapper.updatezt(map);
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
	public int countalbum(Map<String, Object> queryParams) {
		return vodSpecialMapper.countalbum(queryParams);
	}

	@Override
	public List<Map<String, Object>> pagealbum(Map<String, Object> queryParams) {
		return vodSpecialMapper.pagealbum(queryParams);
	}

	@Override
	public int deletesalbum(Map<String, Object> map) {
		return vodSpecialMapper.deletesalbum(map);
	}

	@Override
	public int insertjson(VodSpecial vodSpecial) {
		// TODO Auto-generated method stub
		return vodSpecialMapper.insertjson(vodSpecial);
	}

	@Override
	public int updateimg(VodSpecial vodSpecial) {
		return vodSpecialMapper.updateimg(vodSpecial);
	}

	@Override
	public List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams) {
		return vodSpecialMapper.findalbumsequencesum(queryParams);
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
	public Map<String, Object> findmaxVSminsequence(Map<String, Object> map) {
		return vodSpecialMapper.findmaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams) {
		return vodSpecialMapper.findsequencesum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findsequence(Map<String, Object> map) {
		return vodSpecialMapper.findsequence(map);
	}
}
