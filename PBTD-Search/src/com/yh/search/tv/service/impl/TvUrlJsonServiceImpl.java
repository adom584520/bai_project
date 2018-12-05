package com.yh.search.tv.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.tv.bean.TvSubSearchItem;
import com.yh.search.tv.dao.TvUrlJsonDao;
import com.yh.search.tv.service.TvUrlJsonService;
import com.yh.search.utils.PropertyUtil;

/**
 * TV端搜索相关推荐Service
 * 
 * @author 程先生
 *
 */
@Service // 查询所有
public class TvUrlJsonServiceImpl implements TvUrlJsonService {

	@Autowired
	private TvUrlJsonDao urlJsonDao;

	@Override
	public List<TvSubSearchItem> getSubSearch(String seriesId) throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/solr.properties");
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer(pro.getProperty("solrURL"));
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(seriesId);
		// 需要指定默认搜索域。
		query.set("df", "id");
		// 根据query对象查询索引库
		QueryResponse response = solrServer.query(query);
		// 取专辑
		SolrDocumentList solrDocumentList = response.getResults();
		if (solrDocumentList.size() > 0) {
			SolrDocument solrDocument = solrDocumentList.get(0);
			String actor = null;
			if (solrDocument.get("play_actor") != null) {
				actor = solrDocument.get("play_actor").toString();
			}
			String categoryName = null;
			if (solrDocument.get("play_categoryName") != null) {
				categoryName = solrDocument.get("play_categoryName").toString();
			}

			List<TvSubSearchItem> itemList = new ArrayList<>();
			// 有演员
			if (actor != null && !"".equals(actor)) {
				// 有演员,有频道
				if (categoryName != null && !"".equals(categoryName)) {
					itemList = SubSearch1(actor, categoryName, seriesId);
				} else {
					// 有演员,无频道
					itemList = SubSearch2(actor, seriesId);
				}
				// 无演员
			} else {
				// 无演员,有频道
				if (categoryName != null && !"".equals(categoryName)) {
					itemList = SubSearch3(categoryName, seriesId);
				} else {
					// 无演员,无频道
					itemList = SubSearch4(seriesId);
				}
			}
			return itemList;
		}
		return null;
	}

	// 无演员,无频道
	private List<TvSubSearchItem> SubSearch4(String seriesId) throws Exception {
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("*:*");
		// 设置分页条件
		query.setStart(0);
		query.setRows(10);
		// 过滤条件
		query.addFilterQuery("play_zt" + ":" + 1);
		List<TvSubSearchItem> itemList = urlJsonDao.getSubSearch(query);
		Iterator<TvSubSearchItem> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getSeriesCode().equals(seriesId)) {
				iterator.remove();
			}
		}
		if (itemList.size() == 10) {
			itemList.remove(9);
		}
		return itemList;
	}

	// 无演员,有频道
	private List<TvSubSearchItem> SubSearch3(String categoryName, String seriesId) throws Exception {
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("*:*");
		// 设置分页条件
		query.setStart(0);
		query.setRows(10);

		// 过滤条件
		query.addFilterQuery("play_zt" + ":" + 1);
		query.addFilterQuery("play_categoryName" + ":" + categoryName);
		List<TvSubSearchItem> itemList = urlJsonDao.getSubSearch(query);
		Iterator<TvSubSearchItem> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getSeriesCode().equals(seriesId)) {
				iterator.remove();
			}
		}
		if (itemList.size() == 10) {
			itemList.remove(9);
		}
		return itemList;
	}

	// 有演员,无频道
	private List<TvSubSearchItem> SubSearch2(String actor, String seriesId) throws Exception {
		String[] actors = actor.split("\\|");
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(actors[0]);
		// 设置分页条件
		query.setStart(0);
		query.setRows(10);
		// 过滤条件
		query.addFilterQuery("play_zt" + ":" + 1);
		query.addFilterQuery("{!frange l=1}query($q)");
		// 需要指定默认搜索域。
		query.set("df", "play_actor");
		List<TvSubSearchItem> itemList = urlJsonDao.getSubSearch(query);
		// 相关推荐大于10
		if (itemList.size() >= 10) {
			Iterator<TvSubSearchItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getSeriesCode().equals(seriesId)) {
					iterator.remove();
				}
			}
			if (itemList.size() == 10) {
				itemList.remove(9);
			}
		} else {
			// 相关推荐小于10
			// 创建一个SolrQuery对象。
			SolrQuery querys = new SolrQuery();
			// 设置查询条件
			querys.setQuery("*:*");
			// 设置分页条件
			querys.setStart(0);
			querys.setRows(19);
			// 过滤条件
			querys.addFilterQuery("play_zt" + ":" + 1);
			// 需要指定默认搜索域。
			query.set("df", "play_actor");
			List<TvSubSearchItem> list = urlJsonDao.getSubSearch(querys);
			Iterator<TvSubSearchItem> iterator = list.iterator();
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
			Iterator<TvSubSearchItem> iterators = itemList.iterator();
			while (iterators.hasNext()) {
				if (iterators.next().getSeriesCode().equals(seriesId)) {
					iterators.remove();
				}
			}
			if (itemList.size() == 10) {
				itemList.remove(9);
			}
		}

		return itemList;
	}

	// 有演员,有频道
	private List<TvSubSearchItem> SubSearch1(String actor, String categoryName, String seriesId) throws Exception {
		String[] actors = actor.split("\\|");
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(actors[0]);
		// 设置分页条件
		query.setStart(0);
		query.setRows(10);
		// 过滤条件
		query.addFilterQuery("play_zt" + ":" + 1);
		query.addFilterQuery("{!frange l=1}query($q)");
		query.addFilterQuery("play_categoryName" + ":" + categoryName);
		// 需要指定默认搜索域。
		query.set("df", "play_actor");
		// solr查询
		List<TvSubSearchItem> itemList = urlJsonDao.getSubSearch(query);
		// 相关推荐大于10
		if (itemList.size() >= 10) {
			Iterator<TvSubSearchItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getSeriesCode().equals(seriesId)) {
					iterator.remove();
				}
			}
			if (itemList.size() == 10) {
				itemList.remove(9);
			}
		} else {
			// 相关推荐小于10
			// 创建一个SolrQuery对象。
			SolrQuery querys = new SolrQuery();
			// 设置查询条件
			querys.setQuery(actors[0]);
			// 设置分页条件
			querys.setStart(0);
			querys.setRows(10);
			// 过滤条件
			querys.addFilterQuery("play_zt" + ":" + 1);
			querys.addFilterQuery("{!frange l=1}query($q)");
			// 需要指定默认搜索域。
			querys.set("df", "play_actor");
			List<TvSubSearchItem> list = urlJsonDao.getSubSearch(querys);
			if (list.size() == 10) {
				Iterator<TvSubSearchItem> iterators = list.iterator();
				while (iterators.hasNext()) {
					if (iterators.next().getSeriesCode().equals(seriesId)) {
						iterators.remove();
					}
				}
				itemList = list;
				if (itemList.size() == 10) {
					itemList.remove(9);
				}

			} else {
				// 相关推荐小于10
				// 创建一个SolrQuery对象。
				SolrQuery queryz = new SolrQuery();
				// 设置查询条件
				queryz.setQuery("*:*");
				// 设置分页条件
				queryz.setStart(0);
				queryz.setRows(19);
				// 过滤条件
				queryz.addFilterQuery("play_zt" + ":" + 1);
				queryz.addFilterQuery("play_categoryName" + ":" + categoryName);
				List<TvSubSearchItem> lists = urlJsonDao.getSubSearch(queryz);
				Iterator<TvSubSearchItem> iterator = lists.iterator();
				for (int i = 0; i < list.size(); i++) {
					while (iterator.hasNext()) {
						if (list.get(i).getSeriesCode().equals(iterator.next().getSeriesCode())) {
							iterator.remove();
						}
					}
				}
				int i = 0;
				while (list.size() <= 10) {
					i++;
					list.add(lists.get(i));
				}
				Iterator<TvSubSearchItem> iterators = list.iterator();
				while (iterators.hasNext()) {
					if (iterators.next().getSeriesCode().equals(seriesId)) {
						iterators.remove();
					}
				}
				itemList = list;
				if (itemList.size() == 10) {
					itemList.remove(9);
				}
			}
		}

		return itemList;
	}

}
