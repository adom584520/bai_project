package com.pbtd.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.CpLiveFavorite;
import com.pbtd.manager.mapper.CpLiveFavoriteMapper;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;
import com.pbtd.manager.service.CpLiveFavoriteService;

@Service
public class CpLiveFavoriteServiceImpl implements CpLiveFavoriteService {
	@Autowired
	private CpLiveFavoriteMapper clfMapper;

	@Override
	public void deleteByDeviceId(String deviceId) {
		clfMapper.deleteByDeviceId(deviceId);
	}

	@Override
	public void insertBatch(List<CpLiveFavorite> data, Integer deviceType, String chnCode, String cpCode) {
		clfMapper.insertBatch(data, deviceType, chnCode, cpCode);
	}

	@Override
	public CpLiveFavorite queryByChnCode(String deviceId, String chnCode) {
		return clfMapper.queryByChnCode(deviceId, chnCode);
	}

	@Override
	public void insert(CpLiveFavorite clf) {
		clf.setCreateTime(new Date());
		clfMapper.insert(clf);

	}

	@Override
	public void update(CpLiveFavorite clf) {
		clfMapper.update(clf);
	}

	@Override
	public long queryContByDeviceId(String deviceId) {
		return clfMapper.queryContByDeviceId(deviceId);
	}

	@Override
	public List<CpLiveFavorite> queryExcessList(CpPlayHistoryQueryObject qo) {
		return clfMapper.queryExcessList(qo);
	}

	@Override
	public void deleteBatch(List<Long> ids) {
		clfMapper.deleteBatch(ids);
	}

	@Override
	public void deleteByChnCode(String deviceId, String chnCode) {
		clfMapper.deleteByChnCode(deviceId, chnCode);
	}

	@Override
	public void deleteBatchByChnCode(String deviceId, List<String> chnCodeList) {
		clfMapper.deleteBatchByChnCode(deviceId, chnCodeList);
	}
}
