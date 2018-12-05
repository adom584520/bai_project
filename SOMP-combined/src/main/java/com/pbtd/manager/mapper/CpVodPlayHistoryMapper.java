package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.CpVodPlayHistory;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;

public interface CpVodPlayHistoryMapper {
	void deleteByDeviceId(String deviceId);

	void insertBatch(@Param("data")List<CpVodPlayHistory> data, @Param("deviceType")Integer deviceType, @Param("deviceId")String deviceId, @Param("cpCode")String cpCode);

	CpVodPlayHistory queryBySeriesCode(@Param("deviceId")String deviceId, @Param("seriesCode")String seriesCode);

	void insert(CpVodPlayHistory cvph);

	void update(CpVodPlayHistory cvph);

	long queryContByDeviceId(String deviceId);

	List<CpVodPlayHistory> queryExcessList(CpPlayHistoryQueryObject qo);

	void deleteBatch(List<Long> ids);

	void deleteBySeriesCode(@Param("deviceId")String deviceId,@Param("seriesCode")String seriesCode);

	void deleteBatchBySeriesCode(@Param("deviceId")String deviceId,@Param("list")List<String> seriesCodeList);
	
	long queryCountByDeviceIdSeriesCode(@Param("deviceId")String deviceId,@Param("seriesCode")String seriesCode);
}
