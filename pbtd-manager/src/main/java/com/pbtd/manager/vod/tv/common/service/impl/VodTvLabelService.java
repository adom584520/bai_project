package com.pbtd.manager.vod.tv.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.tv.common.domain.VodTvlabel;
import com.pbtd.manager.vod.tv.common.mapper.VodTvLabelMapper;
import com.pbtd.manager.vod.tv.common.service.face.IVodTvLabelService;
@Service
public class VodTvLabelService implements IVodTvLabelService {

	@Autowired
	private VodTvLabelMapper  vodTvLabelMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodTvLabelMapper.count(queryParams);
	}

	@Override
	public List<VodTvlabel> page(Map<String, Object> queryParams) {
		
		return vodTvLabelMapper.page(queryParams);
	}

	@Override
	public List<VodTvlabel> find(Map<String, Object> queryParams) {
		
		return vodTvLabelMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodTvLabelMapper.generatePosition(queryParams);
	}

	@Override
	public VodTvlabel load(int id) {
		
		return vodTvLabelMapper.load(id);
	}

	@Override
	public int insert(VodTvlabel vodTvlabel) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("channelCode", vodTvlabel.getChannelCode());
		queryParams.put("level", vodTvlabel.getLevel());
		//获取排序序号
		int newsequence=vodTvlabel.getSequence();
			queryParams.put("maxnum",newsequence);
			vodTvLabelMapper.addsequce( queryParams);
		return vodTvLabelMapper.insert(vodTvlabel);
	}

	@Override
	public int update(VodTvlabel vodTvlabel) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("channelCode", vodTvlabel.getChannelCode());
		queryParams.put("id", vodTvlabel.getId());
		queryParams.put("level", vodTvlabel.getLevel());
		//获取原始排序序号
		VodTvlabel map1=vodTvLabelMapper.load(vodTvlabel.getId());
		int currsequence=map1.getSequence();
		int newsequence=vodTvlabel.getSequence();
		//比对序号是变大还是变小
		if(currsequence>newsequence){
			queryParams.put("plus", "1");
			queryParams.put("minnum", newsequence);
			queryParams.put("maxnum",currsequence);
		}else{
			queryParams.put("minnum", currsequence);
			queryParams.put("maxnum",newsequence);
		}
		vodTvLabelMapper.updatesequce( queryParams);
		return	 vodTvLabelMapper.update(vodTvlabel);
	}

	@Override
	public int deletes(Map<String, Object> map,List<Integer> ids) {
		//获取原始排序序号
		Map<String, Object> map2=new HashMap<>();
		for (Integer i : ids) {
			map.put("id", i);
			VodTvlabel map1=vodTvLabelMapper.load(i);
			vodTvLabelMapper.deletes(map);
			int currsequence=map1.getSequence();
			map2.put("maxnum",currsequence);
			map2.put("channelCode", map1.getChannelCode());
			vodTvLabelMapper.deletesequce(map2);
		}
		
		return 1;
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return vodTvLabelMapper.updateStatus(map);
	}
 

}
