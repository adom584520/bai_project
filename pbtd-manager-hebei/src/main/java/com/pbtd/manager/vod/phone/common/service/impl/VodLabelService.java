package com.pbtd.manager.vod.phone.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.common.domain.Vodlabel;
import com.pbtd.manager.vod.phone.common.mapper.VodLabelMapper;
import com.pbtd.manager.vod.phone.common.service.face.IVodLabelService;
import com.pbtd.manager.vod.tv.common.domain.VodTvlabel;

@Service
public class VodLabelService implements IVodLabelService {

	@Autowired
	private VodLabelMapper  vodLabelMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodLabelMapper.count(queryParams);
	}

	@Override
	public List<Vodlabel> page(int start, int limit, Map<String, Object> queryParams) {
		
		return vodLabelMapper.page(start, limit, queryParams);
	}

	@Override
	public List<Vodlabel> find(Map<String, Object> queryParams) {
		
		return vodLabelMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodLabelMapper.generatePosition(queryParams);
	}

	@Override
	public Vodlabel load(int id) {
		
		return vodLabelMapper.load(id);
	}

	@Override
	public int insert(Vodlabel vodlabel) {
		return vodLabelMapper.insert(vodlabel);
	}

	@Override
	public int update(Vodlabel vodlabel) {
      return vodLabelMapper.update(vodlabel);
	}

	@Override
	public int deletes(Map<String, Object> map,List<Integer> ids) {
					return vodLabelMapper.deletes(map);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {		
		return vodLabelMapper.updateStatus(map) ;
	}

	@Override
	public int insertjson(Vodlabel vodlabel) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("type", vodlabel.getType());
		//获取排序序号
		Map<String, Object> map=vodLabelMapper.findsequence(queryParams);
		String max="0";
		if(map!=null){
			max=map.get("max").toString();
		}
		vodlabel.setSequence(Integer.parseInt(max)+1);
		return vodLabelMapper.insertjson(vodlabel);
	}

	@Override
	public int addlabelchannel(Map<String, Object> map) {
		
		return vodLabelMapper.addlabelchannel(map);
	}

	@Override
	public int deletelabelchannel(Map<String, Object> map) {
		
		return vodLabelMapper.deletelabelchannel(map);
	}

	@Override
	public int countlabelchannel(Map<String, Object> queryParams) {
		
		return vodLabelMapper.countlabelchannel(queryParams);
	}

	@Override
	public List<Map<String, Object>> pagelabelchannel(Map<String, Object> queryParams) {
		
		return vodLabelMapper.pagelabelchannel(queryParams);
	}

	@Override
	public int countchannel(Map<String, Object> map) {
		return vodLabelMapper.addlabelchannel(map);
	}

	@Override
	public Map<String, Object> findmaxVSminsequence(Map<String, Object> map) {
		return vodLabelMapper.findmaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams) {
		return vodLabelMapper.findsequencesum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findlabelsequence(Map<String, Object> map) {
		return vodLabelMapper.findlabelsequence(map);
	}


	@Override
	public String getType(Map<String, Object> map) {
		return vodLabelMapper.getType(map);
	}
	
	@Override
	public int updateimg(Map<String, Object> Map) {
		return vodLabelMapper.updateimg(Map);
	}
 

}
