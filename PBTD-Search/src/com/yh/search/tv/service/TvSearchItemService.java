package com.yh.search.tv.service;

public interface TvSearchItemService {
	public void importAllIndex();
	public void deleteIndex();
	public void optimizeIndex();
	public void importAllTitleIndex();
	public void deleteTitleIndex();
	public void optimizeTitleIndex();
}
