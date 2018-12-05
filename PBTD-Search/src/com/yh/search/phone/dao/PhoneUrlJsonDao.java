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

import com.yh.search.phone.bean.PhoneSearchResult;
import com.yh.search.phone.bean.PhoneSubSearchItem;
import com.yh.search.utils.PropertyUtil;

//手机端查询索引库
@Repository
public class PhoneUrlJsonDao {
	

	public List<PhoneSubSearchItem> getSubSearch(SolrQuery query) throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/solr.properties");
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer(pro.getProperty("solrURL"));
		// 根据query对象查询索引库
		QueryResponse response = solrServer.query(query);
		// 取专辑列表
		SolrDocumentList solrDocumentList = response.getResults();
		PhoneSearchResult result = new PhoneSearchResult();
		// 总记录数
		result.setRecordCount(solrDocumentList.getNumFound());
		// 专辑列表
		List<PhoneSubSearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			PhoneSubSearchItem play = new PhoneSubSearchItem();
			play.setSeriesCode((String)solrDocument.get("id").toString());
			play.setEpis(Long.parseLong((solrDocument.get("play_epis") == null ? "0":solrDocument.get("play_epis").toString())));
			play.setSeriesName((String)solrDocument.get("play_name"));
			play.setActorName((String)solrDocument.get("play_actor"));
			play.setWriterName((String)solrDocument.get("play_writer"));
			play.setChannel((String)solrDocument.get("play_supid"));
			play.setChannelName((String)solrDocument.get("play_categoryName"));
			play.setPictureurl1((String)solrDocument.get("play_image_v"));
			play.setPictureurl2((String)solrDocument.get("play_image_hs"));
			play.setCurrentnum(Long.parseLong((solrDocument.get("play_updatano") == null ? "0":solrDocument.get("play_updatano").toString())));
			play.setOriginalCountry((String)solrDocument.get("play_country"));
			play.setDescription((String)solrDocument.get("play_descs"));
			play.setCornerimg((String)solrDocument.get("play_imgurl"));
			play.setOrgairDate((String)solrDocument.get("play_year"));
			play.setStatus((long)solrDocument.get("play_zt"));
			play.setIsvip((String)solrDocument.get("play_vip"));
			play.setScore((String)solrDocument.get("play_score"));
			// 添加到专辑列表
			itemList.add(play);
		}

		return itemList;
	}
}
