package com.yh.search.tv.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Repository;

import com.yh.search.tv.bean.TvStatSearchItem;
import com.yh.search.utils.PropertyUtil;

//搜索内容中频道分类
@Repository
public class TvStatSearchDao {


	public List<TvStatSearchItem> GroupFieldQuery(SolrQuery query) throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/solr.properties");
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer(pro.getProperty("solrTvURL"));
		// 根据query对象查询索引库
		QueryResponse response = solrServer.query(query);

		GroupResponse groupResponse = response.getGroupResponse();

		List<GroupCommand> groupList = groupResponse.getValues();

		List<TvStatSearchItem> statList = new ArrayList<>();
		// 记录数
		for (GroupCommand groupCommand : groupList) {
			List<Group> groups = groupCommand.getValues();
			for (Group group : groups) {
				TvStatSearchItem stat = new TvStatSearchItem();
				if (group.getGroupValue() != null) {
					stat.setChannelName(group.getGroupValue());
					stat.setTotal(group.getResult().getNumFound());
					statList.add(stat);
				}
			}
		}
		return statList;
	}
}
