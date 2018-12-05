package com.yh.search.phone.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.phone.dao.PhoneIntelligentDao;
import com.yh.search.phone.service.PhoneIntelligentService;
/**
 * 手机端提示
 * @author Administrator
 *
 */
@Service
public class PhoneIntelligentServiceImpl implements PhoneIntelligentService {

	@Autowired
	private PhoneIntelligentDao phoneIntelligentDao;
	
	@Override
	public List<String> search(String queryString) throws Exception {
		// 创建一个query对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 结果中域的列表
		query.setFields("play_name");
		// 设置默认搜索域
		query.set("df", "suggest");
		// 执行查询
		List<String> result = phoneIntelligentDao.search(query);

		return result;
	}

}
