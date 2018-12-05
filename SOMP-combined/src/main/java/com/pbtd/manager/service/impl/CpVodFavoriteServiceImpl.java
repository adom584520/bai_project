package com.pbtd.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.CpVodFavorite;
import com.pbtd.manager.mapper.CpVodFavoriteMapper;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;
import com.pbtd.manager.service.CpVodFavoriteService;

@Service
public class CpVodFavoriteServiceImpl implements CpVodFavoriteService {
	@Autowired
	private CpVodFavoriteMapper cvfMapper;
	

	@Override
	public void deleteByDeviceId(String deviceId) {
		cvfMapper.deleteByDeviceId(deviceId);
	}

	@Override
	public void insertBatch(List<CpVodFavorite> data, Integer deviceType, String deviceId, String cpCode) {
		cvfMapper.insertBatch(data, deviceType, deviceId, cpCode);
	}

	@Override
	public CpVodFavorite queryBySeriesCode(String deviceId, String seriesCode) {
		return cvfMapper.queryBySeriesCode(deviceId, seriesCode);
	}

	@Override
	public void insert(CpVodFavorite cvf) {
		cvf.setCreateTime(new Date());
		cvfMapper.insert(cvf);

	}

	@Override
	public void update(CpVodFavorite cvf) {
		cvfMapper.update(cvf);

	}

	@Override
	public long queryContByDeviceId(String deviceId) {
		return cvfMapper.queryContByDeviceId(deviceId);
	}

	@Override
	public List<CpVodFavorite> queryExcessList(CpPlayHistoryQueryObject qo) {
		return cvfMapper.queryExcessList(qo);
	}

	@Override
	public void deleteBatch(List<Long> ids) {
		cvfMapper.deleteBatch(ids);

	}

	@Override
	public void deleteBySeriesCode(String deviceId, String seriesCode) {
		cvfMapper.deleteBySeriesCode(deviceId,seriesCode);
		
	}

	@Override
	public void deleteBatchBySeriesCode(String deviceId, List<String> seriesCodeList) {
		cvfMapper.deleteBatchBySeriesCode(deviceId,seriesCodeList);
	}

	@Override
	public long queryCountByDeviceIdSeriesCode(String deviceId, String seriesCode) {
		return cvfMapper.queryCountByDeviceIdSeriesCode(deviceId,seriesCode);
	}
}
