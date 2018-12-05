package com.yh.search.phone.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.phone.bean.PhoneSearchResult;
import com.yh.search.phone.dao.MobileItemSearchDao;
import com.yh.search.phone.service.MobileSearchService;
import com.yh.search.utils.pingYinUtil;

/**
 * 手机端搜索分页Service 查询所有
 * 
 * @author 程先生
 *
 */
@Service
public class MobileSearchServiceImpl implements MobileSearchService {

	@Autowired
	private MobileItemSearchDao mobileItemSearchDao;

	@Override // 查询所有
	public PhoneSearchResult search(String queryString, int page, int rows) throws Exception {
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
		// query.addFilterQuery("play_zt" + ":" + 1);
		boolean chinese = pingYinUtil.checkStringContainChinese(queryString);
		if (chinese) {
			// 需要指定默认搜索域。
			query.addFilterQuery("{!frange l=0.6}query($q)");
			query.set("df", "play_keywords");
			// 搜索域权重
			query.set("defType", "dismax");
			query.set("qf", "play_name^12 play_actor^12 play_names^5");
		} else {
			// 需要指定默认搜索域。
			query.addFilterQuery("{!frange l=0.7}query($q)");
			query.set("df", "play_pinName");
		}
		// 执行查询
		PhoneSearchResult result = mobileItemSearchDao.search(query);
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

	@Override // 带过滤条件搜索
	public PhoneSearchResult supSearch(String queryString, int page, int rows, String categoryName) throws Exception {
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
		// query.addFilterQuery("play_zt" + ":" + 1);
		boolean chinese = pingYinUtil.checkStringContainChinese(queryString);
		if (chinese) {
			query.addFilterQuery("play_categoryName" + ":" + categoryName);
			query.addFilterQuery("{!frange l=0.7}query($q)");
			// 需要指定默认搜索域。
			query.set("df", "play_keywords");
			// 搜索域权重
			query.set("defType", "dismax");
			query.set("qf", "play_name^12 play_actor^12 play_names^5");
		} else {
			query.addFilterQuery("play_categoryName" + ":" + categoryName);
			// 需要指定默认搜索域。
			query.addFilterQuery("{!frange l=0.7}query($q)");
			query.set("df", "play_pinName");
		}
		// 设置高亮
		query.setHighlight(true);
		query.addHighlightField("play_name");
		query.setHighlightSimplePre("<span style=\"color:blue\">");
		query.setHighlightSimplePost("</span>");
		// 执行查询，调用SearchDao。得到SearchResult
		PhoneSearchResult result = mobileItemSearchDao.search(query);
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
