package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.Drama;

public interface DramaMapper {
	List<Drama> queryBySeriesCode(Long seriesCode);

	Drama queryById(Long id);

	Drama queryBySeriesCodeAndDrama(@Param("seriesCode")Long seriesCode,@Param("drama")Integer drama);

	void insert(Drama drama);

	void update(Drama drama);

	Drama queryByDramaName(@Param("drama")Integer drama,@Param("dramaname")String dramaname,@Param("duration")String duration);
}
