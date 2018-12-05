package com.yh.search.tv.service;

import java.util.List;

import com.yh.search.tv.bean.TvStatSearchItem;


public interface TvStatSearchService {
	List<TvStatSearchItem> GroupFieldQuery(String queryString) throws Exception;
}
