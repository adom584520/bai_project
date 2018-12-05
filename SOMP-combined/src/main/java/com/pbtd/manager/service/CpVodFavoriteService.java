package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.CpVodFavorite;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;

public interface CpVodFavoriteService {
	/**
	 * @param deviceId 设备唯一标识
	 */
	void deleteByDeviceId(String deviceId);

	void insertBatch(List<CpVodFavorite> data,Integer deviceType,String deviceId,String cpCode);

	CpVodFavorite queryBySeriesCode(String deviceId,String seriesCode);

	void insert(CpVodFavorite cvf);

	void update(CpVodFavorite cvf);

	long queryContByDeviceId(String deviceId);

	List<CpVodFavorite> queryExcessList(CpPlayHistoryQueryObject qo);

	void deleteBatch(List<Long> ids);

	void deleteBySeriesCode(String deviceId,String seriesCode);

	void deleteBatchBySeriesCode(String deviceId,List<String> seriesCodeList);
	
	long queryCountByDeviceIdSeriesCode(String deviceId,String seriesCode);
}
