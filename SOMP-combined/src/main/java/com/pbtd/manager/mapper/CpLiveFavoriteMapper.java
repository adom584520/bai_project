package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.CpLiveFavorite;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;

public interface CpLiveFavoriteMapper {
	void deleteByDeviceId(String deviceId);

	void insertBatch(@Param("data")List<CpLiveFavorite> data, @Param("deviceType")Integer deviceType, @Param("deviceId")String deviceId, @Param("cpCode")String cpCode);

	CpLiveFavorite queryByChnCode(@Param("deviceId")String deviceId, @Param("chnCode")String chnCode);

	void insert(CpLiveFavorite clf);

	void update(CpLiveFavorite clf);

	long queryContByDeviceId(String deviceId);

	List<CpLiveFavorite> queryExcessList(CpPlayHistoryQueryObject qo);

	void deleteBatch(List<Long> ids);

	void deleteByChnCode(@Param("deviceId")String deviceId,@Param("chnCode")String chnCode);

	void deleteBatchByChnCode(@Param("deviceId")String deviceId,@Param("list")List<String> chnCodeList);
}
