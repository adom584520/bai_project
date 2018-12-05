package com.pbtd.vodinterface.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.pbtd.vodinterface.web.domain.SearchItem;

public class SolrClient {

	// 无演员,无频道
	public static List<SearchItem> SubSearch4(String seriesCode, String solrURL) throws Exception {
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("*:*");
		// 设置分页条件
		Integer page = 0;
		Integer rows = 10;
		query.setStart(page);
		query.setRows(rows);
		// 过滤条件
		query.addFilterQuery("play_zt" + ":" + 1);
		long solrCount = solrCount(query, solrURL);
		if (solrCount >rows) {
			int num=(int)(Math.random()*(solrCount-rows));
			System.out.println("随机数:"+num);
			query.setStart(num);
			query.setRows(rows);
		}
		List<SearchItem> itemList = getSubSearch(query, solrURL);
		Iterator<SearchItem> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getSeriesCode().equals(seriesCode)) {
				iterator.remove();
			}
		}
		if (itemList.size() == 10) {
			itemList.remove(9);
		}
		return itemList;
	}

	// 无演员,有频道
	public static List<SearchItem> SubSearch3(String categoryName, String seriesCode, String solrURL) throws Exception {
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("*:*");
		// 设置分页条件
		Integer page = 0;
		Integer rows = 10;
		query.setStart(page);
		query.setRows(rows);
		// 过滤条件
		query.addFilterQuery("play_zt" + ":" + 1);
		query.addFilterQuery("play_categoryName" + ":" + categoryName);
		long solrCount = solrCount(query, solrURL);
		if (solrCount >rows) {
			int num=(int)(Math.random()*(solrCount-rows));
			System.out.println("随机数:"+num);
			query.setStart(num);
			query.setRows(rows);
		}
		List<SearchItem> itemList = getSubSearch(query, solrURL);
		Iterator<SearchItem> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getSeriesCode().equals(seriesCode)) {
				iterator.remove();
			}
		}
		if (itemList.size() == 10) {
			itemList.remove(9);
		}
		return itemList;
	}

	// 有演员,无频道
	public static List<SearchItem> SubSearch2(String actor, String seriesCode, String solrURL) throws Exception {
		String actors = actor.replaceAll("\\||,", " ");
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(actors);
		// 设置分页条件
		Integer page = 0;
		Integer rows = 10;
		query.setStart(page);
		query.setRows(rows);
		// 过滤条件
		query.addFilterQuery("play_zt" + ":" + 1);
		// 需要指定默认搜索域。
		query.set("df", "play_actors");
		List<SearchItem> itemList = getSubSearch(query, solrURL);
		// 相关推荐大于10
		if (itemList.size() >= 10) {
			Iterator<SearchItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getSeriesCode().equals(seriesCode)) {
					iterator.remove();
				}
			}
			if (itemList.size() == 10) {
				itemList.remove(9);
				return itemList;
			}
		}
		// 相关推荐小于10
		// 创建一个SolrQuery对象。
		SolrQuery querys = new SolrQuery();
		// 设置查询条件
		querys.setQuery("*:*");
		// 设置分页条件
		page = 0;
		rows = 19;
		querys.setStart(page);
		querys.setRows(rows);
		// 过滤条件
		querys.addFilterQuery("play_zt" + ":" + 1);
		// 需要指定默认搜索域。
		//query.set("df", "play_actors");
		long solrCount = solrCount(querys, solrURL);
		if (solrCount >rows) {
			int num=(int)(Math.random()*(solrCount-rows));
			System.out.println("随机数:"+num);
			querys.setStart(num);
			querys.setRows(rows);
		}
		List<SearchItem> list = getSubSearch(querys, solrURL);
		Iterator<SearchItem> iterator = list.iterator();
		for (int i = 0; i < itemList.size(); i++) {
			while (iterator.hasNext()) {
				if (itemList.get(i).getSeriesCode().equals(iterator.next().getSeriesCode())) {
					iterator.remove();
				}
			}
		}
		int i = 0;
		while (itemList.size() <= 10) {
			i++;
			itemList.add(list.get(i));
		}
		Iterator<SearchItem> iterators = itemList.iterator();
		while (iterators.hasNext()) {
			if (iterators.next().getSeriesCode().equals(seriesCode)) {
				iterators.remove();
			}
		}
		if (itemList.size() == 10) {
			itemList.remove(9);
		}

		return itemList;
	}

	// 有演员,有频道
	public static List<SearchItem> SubSearch1(String actor, String categoryName, String seriesCode, String solrURL)
			throws Exception {
		String actors = actor.replaceAll("\\||,", " ");
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(actors);
		// 设置分页条件
		Integer page = 0;
		Integer rows = 10;
		query.setStart(page);
		query.setRows(rows);
		// 过滤条件
		query.addFilterQuery("play_zt" + ":" + 1);
		query.addFilterQuery("play_categoryName" + ":" + categoryName);
		// 需要指定默认搜索域。
		query.set("df", "play_actors");
		// solr查询
		List<SearchItem> itemList = getSubSearch(query, solrURL);
		// 相关推荐大于10
		if (itemList.size() >= 10) {
			Iterator<SearchItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getSeriesCode().equals(seriesCode)) {
					iterator.remove();
				}
			}
			if (itemList.size() == 10) {
				itemList.remove(9);
				return itemList;
			}
		}
		// 相关推荐小于10
		// 创建一个SolrQuery对象。
		SolrQuery queryz = new SolrQuery();
		// 设置查询条件
		queryz.setQuery("*:*");
		// 设置分页条件
		page = 0;
		rows = 19;
		queryz.setStart(page);
		queryz.setRows(rows);
		// 过滤条件
		queryz.addFilterQuery("play_zt" + ":" + 1);
		queryz.addFilterQuery("play_categoryName" + ":" + categoryName);
		// 需要指定默认搜索域。
		//query.set("df", "play_actors");
		long solrCount = solrCount(queryz, solrURL);
		if (solrCount >rows) {
			int num=(int)(Math.random()*(solrCount-rows));
			System.out.println("随机数:"+num);
			queryz.setStart(num);
			queryz.setRows(rows);
		}
		
		List<SearchItem> lists = getSubSearch(queryz, solrURL);
		Iterator<SearchItem> iterator = lists.iterator();
		for (int i = 0; i < itemList.size(); i++) {
			while (iterator.hasNext()) {
				if (itemList.get(i).getSeriesCode().equals(iterator.next().getSeriesCode())) {
					iterator.remove();
				}
			}
		}
		int i = 0;
		while (itemList.size() <= 10) {
			i++;
			itemList.add(lists.get(i));
		}
		Iterator<SearchItem> iterators = itemList.iterator();
		while (iterators.hasNext()) {
			if (iterators.next().getSeriesCode().equals(seriesCode)) {
				iterators.remove();
			}
		}
		if (itemList.size() == 10) {
			itemList.remove(9);
		}

		return itemList;
	}

	// SolrClient
	public static List<SearchItem> getSubSearch(SolrQuery query, String solrURL) throws Exception {
		// 创建一个SolrServer对象
		SolrServer solrServer = SolrServerUtils.getHttpSolrServer(solrURL);
		// 根据query对象查询索引库
		QueryResponse response = solrServer.query(query,METHOD.POST);
		// 取专辑列表
		SolrDocumentList solrDocumentList = response.getResults();
		// 专辑列表
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem play = new SearchItem();
			play.setSeriesCode((String) solrDocument.get("id").toString());
			play.setEpis(Long.parseLong(
					(solrDocument.get("play_epis") == null ? "0" : solrDocument.get("play_epis").toString())));
			play.setSeriesName((String) solrDocument.get("play_name") == null ? "" :solrDocument.get("play_name").toString());
			play.setActorName((String) solrDocument.get("play_actor") == null ? "" :solrDocument.get("play_actor").toString());
			play.setWriterName((String) solrDocument.get("play_writer") == null ? "" :solrDocument.get("play_writer").toString());
			play.setChannel((String) solrDocument.get("play_supid") == null ? "" :solrDocument.get("play_supid").toString());
			play.setShow_id((String) solrDocument.get("play_show_id") == null ? "" :solrDocument.get("play_show_id").toString());
			play.setChannelName((String) solrDocument.get("play_categoryName") == null ? "" :solrDocument.get("play_categoryName").toString());
			play.setPictureurl1((String) solrDocument.get("play_image_v") == null ? "" :solrDocument.get("play_image_v").toString());
			play.setPictureurl2((String) solrDocument.get("play_image_hs") == null ? "" :solrDocument.get("play_image_hs").toString());
			play.setVolumncount(Long.parseLong((solrDocument.get("play_volumncount") == null ? "0":solrDocument.get("play_volumncount").toString())));
			play.setCurrentnum(Long.parseLong((solrDocument.get("play_updatano") == null ? "0" : solrDocument.get("play_updatano").toString())));
			play.setOriginalCountry((String) solrDocument.get("play_country") == null ? "" :solrDocument.get("play_country").toString());
			play.setDescription((String) solrDocument.get("play_descs") == null ? "" :solrDocument.get("play_descs").toString());
			play.setCornerimg((String) solrDocument.get("play_imgurl") == null ? "" :solrDocument.get("play_imgurl").toString());
			play.setOrgairDate((String) solrDocument.get("play_year") == null ? "" :solrDocument.get("play_year").toString());
			// play.setStatus((long)solrDocument.get("play_zt"));
			play.setIsvip((String) solrDocument.get("play_vip") == null ? "" :solrDocument.get("play_vip").toString());
			play.setScore((String) solrDocument.get("play_score") == null ? "" :solrDocument.get("play_score").toString());
			play.setViewPoint((String)solrDocument.get("play_viewPoint")== null ? "":solrDocument.get("play_viewPoint").toString());
			play.setCorner((String)solrDocument.get("play_corner")== null ? "":solrDocument.get("play_corner").toString());
			// 添加到专辑列表
			itemList.add(play);
		}

		return itemList;
	}

	// SolrClient
	/***
	 * 改好了
	 * 计算搜索结果数
	 * @param query
	 * @param solrURL
	 * @return
	 * @throws Exception
	 */
		public static long solrCount(SolrQuery query, String solrURL) throws Exception {
			// 创建一个SolrServer对象
			SolrServer solrServer = SolrServerUtils.getHttpSolrServer(solrURL);
			// 根据query对象查询索引库
			QueryResponse response = solrServer.query(query,METHOD.POST);
			// 取专辑列表
			SolrDocumentList solrDocumentList = response.getResults();
			// 总记录数
			long solrCount = solrDocumentList.getNumFound();
			

			return solrCount;
		}
}
