package com.pbtd.manager.service;

import java.util.HashMap;
import java.util.List;

import com.pbtd.manager.domain.Albuminfo;
import com.pbtd.manager.query.AlbuminfoQueryObject;
import com.pbtd.manager.util.PageResult;

public interface AlbuminfoService {

	PageResult queryList(AlbuminfoQueryObject qo);

	void insertList(List<HashMap> list,Integer type);

	Albuminfo queryBySeriesCode(Long seriesCode);

	List<Albuminfo> queryBySeriesName(String seriesName);

}
