package com.yh.search.tv.service;

import java.util.List;

import com.yh.search.tv.bean.TvSubSearchItem;

public interface TvUrlJsonService {
	List<TvSubSearchItem> getSubSearch(String seriesId) throws Exception;
}
