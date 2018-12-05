package com.yh.search.tv.service.impl;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.stereotype.Service;

import com.yh.search.tv.service.TvLiveDelIds;
import com.yh.search.utils.PropertyUtil;
import com.yh.search.utils.SolrServerUtils;

@Service
public class TvLiveDelIdsImpl implements TvLiveDelIds{
	
	public static Logger log = Logger.getLogger(TvLiveDelIdsImpl.class);

	@Override
	public void delIndexIds(String id_jmmd) {
		PropertyUtil pro;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			HttpSolrServer solrServer = SolrServerUtils.getHttpSolrServer(pro.getProperty("solrTvURL"));
		// 根据查询条件删除文档
			String[] ids = id_jmmd.split(",");
			for (String id : ids) {
				solrServer.deleteByQuery("id"+":"+id);
				// 提交修改
				solrServer.commit();
			}
		} catch (Exception e) {
			log.error("直播根据ID删除文档失败: " + id_jmmd);
		}
		
	}

}
