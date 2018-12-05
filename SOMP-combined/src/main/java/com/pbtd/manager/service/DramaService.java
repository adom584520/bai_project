package com.pbtd.manager.service;

import java.util.HashMap;
import java.util.List;

import com.pbtd.manager.domain.Drama;

public interface DramaService {
	List<Drama> queryBySeriesCode(Long seriesCode);

	void insertList(List<HashMap> list);
}
