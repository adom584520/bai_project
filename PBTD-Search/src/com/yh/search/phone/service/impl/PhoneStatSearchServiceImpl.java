package com.yh.search.phone.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.GroupParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.phone.bean.PhoneStatSearchItem;
import com.yh.search.phone.dao.PhoneStatSearchDao;
import com.yh.search.phone.service.PhoneStatSearchService;
import com.yh.search.utils.pingYinUtil;

/**
 * 搜索结果中频道分类
 * @author Administrator
 *
 */

@Service // 搜索结果中有哪些频道
public class PhoneStatSearchServiceImpl implements PhoneStatSearchService {

	@Autowired
	private PhoneStatSearchDao statSearchDao;

	@Override
	public List<PhoneStatSearchItem> GroupFieldQuery(String queryString) throws Exception {
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 过滤条件
		// query.addFilterQuery("play_zt" + ":" + 1);
		boolean chinese = pingYinUtil.checkStringContainChinese(queryString);
		if (chinese) {
			// 设置默认搜索域
			query.addFilterQuery("{!frange l=0.6}query($q)");
			query.set("df", "play_keywords");
		} else {
			// 需要指定默认搜索域。
			query.addFilterQuery("{!frange l=0.7}query($q)");
			query.set("df", "play_pinName");
		}
		// 设置通过facet查询为true，表示查询时使用facet机制
		query.setParam(GroupParams.GROUP, true);
		query.setParam(GroupParams.GROUP_FIELD, "play_categoryName");// 分组
		// 设置每个quality对应的
		// query.setParam(GroupParams.GROUP_SORT, "play_categoryName asc");
		// 设置返回doc文档数据，因只需要数量，故设置为0
		query.setParam(GroupParams.GROUP_LIMIT, "0");
		// 显示多少行
		query.setRows(50);
		// 取查询结果。
		List<PhoneStatSearchItem> list = statSearchDao.GroupFieldQuery(query);

		// 返回SearchResult
		return list;
	}

}
