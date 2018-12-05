package com.yh.search.phone.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;
import com.yh.search.utils.PropertyUtil;

/**
 * 搜索标题
 * @author Administrator
 *
 */
@Repository
public class PhoneIntelligentDao {

	public List<String> search(SolrQuery query) throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/solr.properties");
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer(pro.getProperty("solrTitleURL"));
		// 执行查询。得到一个Response对象。
		QueryResponse response = solrServer.query(query);
		// 取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		List<String> list = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			// 添加到专辑列表
			list.add(solrDocument.get("play_name").toString());
		}

		return list;

	}

}
