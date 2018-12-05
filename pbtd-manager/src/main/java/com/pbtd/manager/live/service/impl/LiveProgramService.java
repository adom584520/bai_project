package com.pbtd.manager.live.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveProgram;
import com.pbtd.manager.live.mapper.LiveBussInfoMapper;
import com.pbtd.manager.live.mapper.LiveProgramMapper;
import com.pbtd.manager.live.mapper.LiveSysParamMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveProgramService;

@Service
public class LiveProgramService implements ILiveProgramService {
	@Autowired
	private LiveProgramMapper liveProgramMapper;
	@Autowired
	private LiveBussInfoMapper liveBussInfoMapper;



	/**
	 * 
	 */
	@Override
	public PageResult querylistallLiveProgram() {
		List<LiveProgram> list = liveProgramMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveProgramMapper.count(queryParams);
	}

	@Override
	public List<LiveProgram> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveProgramMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveProgram> find(Map<String, Object> queryParams) {
		return this.liveProgramMapper.find(queryParams);
	}


	@Override
	public LiveProgram load(int id) {
		return this.liveProgramMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveProgramMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LiveProgram LiveProgram) {
		this.liveProgramMapper.insert(LiveProgram);
		return LiveProgram.getProgramid();
	}
	@Override
	@Transactional
	public int update(LiveProgram LiveProgram) {
		return this.liveProgramMapper.update(LiveProgram);
	}


	@Override
	public List<LiveProgram> getprogram(String id) {
		int bussid = liveBussInfoMapper.selectByValue(id).getBussid();
		return this.liveProgramMapper.getprogram(bussid);
	}

}
