package com.yh.search.tv.service;

import com.yh.search.tv.bean.TvSearchResult;


public interface TvSearchService {
	TvSearchResult search(String queryString, int page, int rows) throws Exception;
	TvSearchResult supSearch(String queryString, int page, int rows,String categoryName) throws Exception;
}
