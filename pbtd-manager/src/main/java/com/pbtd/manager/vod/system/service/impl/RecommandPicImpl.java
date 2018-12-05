package com.pbtd.manager.vod.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
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
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		recommandPic.setUpdate_user(current.getUsername());
			return recommandPicMapper.add(recommandPic);
	}

	@Override
	public int modify(RecommandPic recommandPic) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		recommandPic.setUpdate_user(current.getUsername());
		return recommandPicMapper.modify(recommandPic);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
			return recommandPicMapper.deletes(ids);
	}

	@Override
	public List<Map<String,Object>> page(Map<String, Object> queryParams) {
			return recommandPicMapper.showAll(queryParams);
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
