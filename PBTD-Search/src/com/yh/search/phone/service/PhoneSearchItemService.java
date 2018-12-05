package com.yh.search.phone.service;

public interface PhoneSearchItemService {
	public void importAllIndex();
	public void deleteIndex();
	public void optimizeIndex();
	public void importAllTitleIndex();
	public void deleteTitleIndex();
	public void optimizeTitleIndex();
}
