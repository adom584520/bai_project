package com.yh.search.tv.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.tv.bean.TvSearchResult;
import com.yh.search.tv.dao.TvItemSearchDao;
import com.yh.search.tv.service.TvSearchService;

/**
 * TV端搜索分页Service
 * 
 * @author 程先生
 *
 */
@Service
public class TvSearchServiceImpl implements TvSearchService {

	@Autowired
	private TvItemSearchDao itemSearchDao;

	// 查询所有
	@Override
	public TvSearchResult search(String queryString, int page, int rows) throws Exception {
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 设置分页条件
		if (page < 1) {
			page = 1;
		}
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 过滤条件
		//query.addFilterQuery("play_zt" + ":" + 1);
		query.addFilterQuery("{!frange l=0.7}query($q)");
		// 需要指定默认搜索域。
		query.set("df", "play_pinName");
		// 执行查询
		TvSearchResult result = itemSearchDao.search(query);
		// 需要计算总页数。
		long recordCount = result.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		result.setQueryString(queryString);
		result.setPage(page);
		// 返回SearchResult
		return result;
	}

	 // 带过滤条件搜索
	@Override
	public TvSearchResult supSearch(String queryString, int page, int rows, String categoryName) throws Exception {
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 设置分页条件
		if (page < 1) {
			page = 1;
		}
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 过滤条件
		//query.addFilterQuery("play_zt" + ":" + 1);
		query.addFilterQuery("{!frange l=0.7}query($q)");
		query.addFilterQuery("play_categoryName" + ":" + categoryName);
		// 需要指定默认搜索域。
		query.set("df", "play_pinName");
		// 设置高亮
		/*query.setHighlight(true);
		query.addHighlightField("play_name");
		query.setHighlightSimplePre("<span style=\"color:blue\">");
		query.setHighlightSimplePost("</span>");*/
		// 执行查询，调用SearchDao。得到SearchResult
		TvSearchResult result = itemSearchDao.search(query);
		// 需要计算总页数。
		long recordCount = result.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		result.setQueryString(queryString);
		result.setPage(page);
		// 返回SearchResult
		return result;

	}

}
