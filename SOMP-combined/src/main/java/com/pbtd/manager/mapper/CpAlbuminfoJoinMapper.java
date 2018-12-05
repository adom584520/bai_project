package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.CpAlbuminfoJoin;

public interface CpAlbuminfoJoinMapper {
	CpAlbuminfoJoin validataDramaNoRepeat(@Param("dramaId")Long dramaId,@Param("cpCode")String cpCode);

	CpAlbuminfoJoin queryByCpDrama(@Param("cpDramaId")Long cpDramaId);

	void update(CpAlbuminfoJoin caj);

	void insert(CpAlbuminfoJoin caj);

	void deleteByDramaIdBatch(List<Long> ids);

	CpAlbuminfoJoin queryBySeriesCodeDrama(@Param("seriesCode")Long seriesCode,@Param("drama")Integer drama,@Param("cpCode")String cpCode);

	CpAlbuminfoJoin queryByCpSeriesCodeOne(@Param("cpSeriesCode")Long cpSeriesCode,@Param("cpCode")String cpCode);
}
