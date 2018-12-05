package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.CpLiveFavorite;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;

public interface CpLiveFavoriteService {
	/**
	 * @param deviceId 设备唯一标识
	 */
	void deleteByDeviceId(String deviceId);

	void insertBatch(List<CpLiveFavorite> data,Integer deviceType,String deviceId,String cpCode);

	CpLiveFavorite queryByChnCode(String deviceId,String chnCode);

	void insert(CpLiveFavorite clf);

	void update(CpLiveFavorite clf);

	long queryContByDeviceId(String deviceId);

	List<CpLiveFavorite> queryExcessList(CpPlayHistoryQueryObject qo);

	void deleteBatch(List<Long> ids);

	void deleteByChnCode(String deviceId,String chnCode);

	void deleteBatchByChnCode(String deviceId,List<String> chnCodeList);
}
