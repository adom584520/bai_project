package com.yh.search.phone.service;

import java.util.List;

import com.yh.search.phone.bean.PhoneSubSearchItem;

public interface PhoneUrlJsonService {
	List<PhoneSubSearchItem> getSubSearch(String seriesId) throws Exception;
}
