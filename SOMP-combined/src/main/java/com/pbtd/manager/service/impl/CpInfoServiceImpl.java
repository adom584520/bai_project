package com.pbtd.manager.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.domain.CpInfo;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.CpInfoMapper;
import com.pbtd.manager.query.CpInfoQueryObject;
import com.pbtd.manager.service.CpInfoService;
import com.pbtd.manager.util.PageResult;

@Service
public class CpInfoServiceImpl implements CpInfoService {
	@Autowired
	private CpInfoMapper cpInfoMapper;

	@Override
	public PageResult queryList(CpInfoQueryObject qo) {
		Long count = cpInfoMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<CpInfo> data = cpInfoMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(CpInfo cpInfo) {
		CpInfo ci = cpInfoMapper.queryByCode(cpInfo.getCode());
		if (ci != null) {
			throw new JsonMessageException("该cpCode已存在，不能重复！");
		}
		cpInfo.setCreateTime(new Date());
		cpInfo.setUpdateTime(new Date());
		cpInfoMapper.insert(cpInfo);
	}

	@Override
	@Transactional
	public void update(CpInfo cpInfo) {
		CpInfo ci = cpInfoMapper.queryByCode(cpInfo.getCode());
		if (ci != null) {
			if (!ci.getId().equals(cpInfo.getId())) {
				throw new JsonMessageException("该cpCode已存在，不能重复！");
			}
		}
		cpInfo.setUpdateTime(new Date());
		cpInfoMapper.update(cpInfo);
	}

	@Override
	public void delete(Integer id) {
		cpInfoMapper.delete(id);
	}

	@Override
	public List<CpInfo> queryAll() {
		return cpInfoMapper.queryAll();
	}

	@Override
	public CpInfo queryByCpCode(String cpCode) {
		return cpInfoMapper.queryByCode(cpCode);
	}
}
