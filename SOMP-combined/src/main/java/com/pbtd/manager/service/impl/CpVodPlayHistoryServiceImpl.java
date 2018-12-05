package com.pbtd.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.CpVodPlayHistory;
import com.pbtd.manager.mapper.CpVodPlayHistoryMapper;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;
import com.pbtd.manager.service.CpVodPlayHistoryService;

@Service
public class CpVodPlayHistoryServiceImpl implements CpVodPlayHistoryService {
	@Autowired
	private CpVodPlayHistoryMapper cvphMapper;

	@Override
	public void deleteByDeviceId(String deviceId) {
		cvphMapper.deleteByDeviceId(deviceId);
	}

	@Override
	public void insertBatch(List<CpVodPlayHistory> data, Integer deviceType, String deviceId, String cpCode) {
		cvphMapper.insertBatch(data, deviceType, deviceId, cpCode);
	}

	@Override
	public CpVodPlayHistory queryBySeriesCode(String deviceId, String seriesCode) {
		return cvphMapper.queryBySeriesCode(deviceId, seriesCode);
	}

	@Override
	public void insert(CpVodPlayHistory cvph) {
		cvph.setCreateTime(new Date());
		cvph.setUpdateTime(new Date());
		cvphMapper.insert(cvph);

	}

	@Override
	public void update(CpVodPlayHistory cvph) {
		cvph.setUpdateTime(new Date());
		cvphMapper.update(cvph);

	}

	@Override
	public long queryContByDeviceId(String deviceId) {
		return cvphMapper.queryContByDeviceId(deviceId);
	}

	@Override
	public List<CpVodPlayHistory> queryExcessList(CpPlayHistoryQueryObject qo) {
		return cvphMapper.queryExcessList(qo);
	}

	@Override
	public void deleteBatch(List<Long> ids) {
		cvphMapper.deleteBatch(ids);

	}

	@Override
	public void deleteBySeriesCode(String deviceId, String seriesCode) {
		cvphMapper.deleteBySeriesCode(deviceId, seriesCode);

	}

	@Override
	public void deleteBatchBySeriesCode(String deviceId, List<String> seriesCodeList) {
		cvphMapper.deleteBatchBySeriesCode(deviceId, seriesCodeList);
	}

	@Override
	public long queryCountByDeviceIdSeriesCode(String deviceId, String seriesCode) {
		return cvphMapper.queryCountByDeviceIdSeriesCode(deviceId,seriesCode);
	}
}
