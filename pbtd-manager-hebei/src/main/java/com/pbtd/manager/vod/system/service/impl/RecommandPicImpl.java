package com.pbtd.manager.vod.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pbtd.manager.vod.system.domain.RecommandPic;
import com.pbtd.manager.vod.system.mapper.RecommandPicMapper;
import com.pbtd.manager.vod.system.service.face.RecommandPicService;

@Service
public class RecommandPicImpl implements RecommandPicService {

	@Autowired
	private RecommandPicMapper recommandPicMapper;


	@Override
	public void updateImg(RecommandPic recomandPic) {
		recommandPicMapper.updateImg(recomandPic);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		return recommandPicMapper.generatePosition(queryParams);
	}

	@Override
	public RecommandPic load(int id) {
		return recommandPicMapper.load(id);
	}

	@Override
	public int add(RecommandPic recommandPic) {
				return recommandPicMapper.add(recommandPic);
	}

	@Override
	public int modify(RecommandPic recommandPic) {
		return recommandPicMapper.modify(recommandPic);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
			return recommandPicMapper.deletes(ids);
	}

	@Override
	public List<RecommandPic> page(int start, int limit, Map<String, Object> queryParams) {
			return recommandPicMapper.showAll(start,limit,queryParams);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
			return recommandPicMapper.count(queryParams);
	}

	@Override
	public List<RecommandPic> find(Map<String, Object> queryParams) {
		return recommandPicMapper.find(queryParams);
	}

	@Override
	public List<Map<String, Object>> queryChannelInfo() {
		List<Map<String,Object>> list=recommandPicMapper.queryChannelInfo();
		return list ;
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return recommandPicMapper.updateStatus(map);
	}

	@Override
	public int insertjson(RecommandPic recommandPic) {
		return recommandPicMapper.insertjson(recommandPic);
	}

	@Override
	public Map<String, Object> findmaxVSminsequence(Map<String, Object> map) {
		return recommandPicMapper.findmaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams) {
		return recommandPicMapper.findsequencesum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findsequence(Map<String, Object> map) {
		return recommandPicMapper.findsequence(map);
	}

}
