package com.yh.search.phone.service;

import java.util.List;

import com.yh.search.phone.bean.PhoneStatSearchItem;



public interface PhoneStatSearchService {
	List<PhoneStatSearchItem> GroupFieldQuery(String queryString) throws Exception;
}
