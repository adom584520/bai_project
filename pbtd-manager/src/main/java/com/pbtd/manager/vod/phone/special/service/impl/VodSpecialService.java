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
	public List<VodSpecial> page(Map<String, Object> queryParams) {
		
		return vodSpecialMapper.page(queryParams);
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
		Map<String, Object> queryParams =new HashMap<>();
		//获取排序序号
		int newsequence=vodSpecial.getSequence();
			queryParams.put("maxnum",newsequence);
			vodSpecialMapper.addsequce( queryParams);
		return vodSpecialMapper.insert(vodSpecial);
	}

	@Override
	public int update(VodSpecial vodSpecial) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("id", vodSpecial.getId());
		//获取原始排序序号
		VodSpecial map1=vodSpecialMapper.load(vodSpecial.getId());
		int currsequence=map1.getSequence();
		int newsequence=vodSpecial.getSequence();
		//比对序号是变大还是变小
		if(currsequence>newsequence){
			queryParams.put("plus", "1");
			queryParams.put("minnum", newsequence);
			queryParams.put("maxnum",currsequence);
		}else{
			queryParams.put("minnum", currsequence);
			queryParams.put("maxnum",newsequence);
		}
		vodSpecialMapper.updatesequce( queryParams);
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
	public List<Map<String, Object>> findpagealbum(Map<String, Object> queryParams) {
		return vodSpecialMapper.findpagealbum(queryParams);
	}

	@Override
	public int updateimg(VodSpecial vodSpecial) {
		return vodSpecialMapper.updateimg(vodSpecial);
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
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return vodSpecialMapper.findalbumsequence(map);
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
