package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.CpVodFavorite;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;

public interface CpVodFavoriteMapper {
	void deleteByDeviceId(String deviceId);

	void insertBatch(@Param("data")List<CpVodFavorite> data, @Param("deviceType")Integer deviceType, @Param("deviceId")String deviceId, @Param("cpCode")String cpCode);

	CpVodFavorite queryBySeriesCode(@Param("deviceId")String deviceId, @Param("seriesCode")String seriesCode);

	void insert(CpVodFavorite cvf);

	void update(CpVodFavorite cvf);

	long queryContByDeviceId(String deviceId);

	List<CpVodFavorite> queryExcessList(CpPlayHistoryQueryObject qo);

	void deleteBatch(List<Long> ids);
	
	void deleteBySeriesCode(@Param("deviceId")String deviceId,@Param("seriesCode")String seriesCode);
	
	void deleteBatchBySeriesCode(@Param("deviceId")String deviceId,@Param("list")List<String> seriesCodeList);
	
	long queryCountByDeviceIdSeriesCode(@Param("deviceId")String deviceId,@Param("seriesCode")String seriesCode);
}
