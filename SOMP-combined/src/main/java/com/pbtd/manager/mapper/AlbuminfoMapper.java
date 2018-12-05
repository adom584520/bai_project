package com.pbtd.manager.mapper;

import java.util.HashMap;
import java.util.List;

import com.pbtd.manager.domain.Albuminfo;
import com.pbtd.manager.query.AlbuminfoQueryObject;

public interface AlbuminfoMapper {
	/**
	 * 查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(AlbuminfoQueryObject qo);

	/**
	 * 查询总记录
	 * @param qo
	 * @return
	 */
	List<Albuminfo> queryList(AlbuminfoQueryObject qo);

	Albuminfo queryBySeriesCode(Long seriesCode);

	void update(Albuminfo albuminfo);

	void insert(Albuminfo albuminfo);
	
	List<Albuminfo> queryBySeriesName(String seriesName);
}
