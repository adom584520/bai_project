package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.CpDrama;

public interface CpDramaMapper {
	List<CpDrama> queryByAlbumId(@Param("cpSeriesCode")Long cpSeriesCode,@Param("cpCode")String cpCode);

	CpDrama queryById(Long id);

	List<Long> queryIdsByAlbumId(List<Long> albumIds);

	void updateJoinStatus(CpDrama cpDrama);

	void unpinlessBatch(@Param("ids")List<Long> ids,@Param("cd")CpDrama cd);

	CpDrama queryByCpDrama(CpDrama cd);

	void insert(CpDrama cd);

	void update(CpDrama cd);

	void confirm(@Param("list")List<Long> list,@Param("joinStatus")Integer joinStatus);

	List<CpDrama> queryBatchById(@Param("list")List<Long> list);
}
