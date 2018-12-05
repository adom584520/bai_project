package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.CpVodPlayHistory;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;

public interface CpVodPlayHistoryService {
	/**
	 * @param deviceId 设备唯一标识
	 */
	void deleteByDeviceId(String deviceId);

	void insertBatch(List<CpVodPlayHistory> data,Integer deviceType,String deviceId,String cpCode);

	CpVodPlayHistory queryBySeriesCode(String deviceId,String seriesCode);

	void insert(CpVodPlayHistory cvph);

	void update(CpVodPlayHistory cvph);

	long queryContByDeviceId(String deviceId);

	List<CpVodPlayHistory> queryExcessList(CpPlayHistoryQueryObject qo);

	void deleteBatch(List<Long> ids);

	void deleteBySeriesCode(String deviceId,String seriesCode);

	void deleteBatchBySeriesCode(String deviceId,List<String> seriesCodeList);
	
	long queryCountByDeviceIdSeriesCode(String deviceId,String seriesCode);
}
