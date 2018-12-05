package com.yh.search.phone.service;

import com.yh.search.phone.bean.PhoneSearchResult;


public interface MobileSearchService {
	PhoneSearchResult search(String queryString, int page, int rows) throws Exception;
	PhoneSearchResult supSearch(String queryString, int page, int rows,String categoryName) throws Exception;
}
