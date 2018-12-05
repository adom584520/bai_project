package com.yh.search.tv.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.tv.bean.SearchItem;
import com.yh.search.tv.mapper.TvItemMapper;
import com.yh.search.tv.service.TvSearchItemService;
import com.yh.search.utils.PropertyUtil;
import com.yh.search.utils.SolrServerUtils;

/**
 * 从数据库查出所有专辑导入到索引库
 * 
 * @author 程先生
 *
 */
@Service
public class TvSearchItemServiceImpl implements TvSearchItemService {

	public static Logger log = Logger.getLogger(TvSearchItemServiceImpl.class);

	@Autowired
	private TvItemMapper itemMapper;

	@Override
	public void importAllIndex() {
		// 查询所有专辑数据。
		List<SearchItem> itemList = itemMapper.getItemList();
		// 创建一个SolrServer对象。
		PropertyUtil pro;
		HttpSolrServer solrServer;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			solrServer = new HttpSolrServer(pro.getProperty("solrTvURL"));
			// 为每个专辑创建一个SolrInputDocument对象。
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			for (SearchItem searchItem : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				// 为文档添加域
				document.addField("id", searchItem.getSeriesCode());
				document.addField("play_epis", searchItem.getEpis());
				document.addField("play_name", searchItem.getSeriesName());
				document.addField("play_names", searchItem.getSeriesName());
				document.addField("play_pinName", searchItem.getPinyinsuoxie());
				document.addField("play_actor", searchItem.getActorName());
				document.addField("play_writer", searchItem.getWriterName());
				document.addField("play_year", searchItem.getOrgairDate());
				document.addField("play_supid", searchItem.getChannel());
				document.addField("play_categoryName", searchItem.getChannelName());
				document.addField("play_image_v", searchItem.getPictureurl1());
				document.addField("play_image_h", searchItem.getPictureurl2());
				document.addField("play_image_vs", searchItem.getPictureurl3());
				document.addField("play_image_hs", searchItem.getPictureurl4());
				document.addField("play_updatano", searchItem.getCurrentnum());
				document.addField("play_country", searchItem.getOriginalCountry());
				document.addField("play_descs", searchItem.getDescription());
				document.addField("play_imgurl", searchItem.getCornerimg());
				document.addField("play_zt", searchItem.getStatus());
				document.addField("play_vip", searchItem.getIsvip());
				document.addField("play_score", searchItem.getScore());
				// 向索引库中添加文档。
				docs.add(document);
			}
			solrServer.add(docs);
			// 提交修改
			solrServer.commit();
		} catch (Exception e) {
			log.error("索引批量添加失败:" + e);
		}
	}

	@Override
	public void deleteIndex() {
		// 创建一个SolrServer对象。
		PropertyUtil pro;
		HttpSolrServer solrServer;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			solrServer = new HttpSolrServer(pro.getProperty("solrTvURL"));
			// 根据查询条件删除文档
			solrServer.deleteByQuery("*:*");
			// 提交修改
			solrServer.commit();
		} catch (Exception e) {
			log.error("索引删除失败:" + e);
		}
	}

	@Override
	public void optimizeIndex() {
		// 创建一个SolrServer对象。
		PropertyUtil pro;
		HttpSolrServer solrServer;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			solrServer = new HttpSolrServer(pro.getProperty("solrTvURL"));
			// 优化索引，不建议每次创建好之后都优化，定期优化即可
			solrServer.optimize();
		} catch (Exception e) {
			log.error("索引优化失败:" + e);
		}
	}

	@Override
	public void importAllTitleIndex() {
		/*List<PingYinSearch> itemList = pingYinMapper.getItemList();
		// 创建一个SolrServer对象。
		PropertyUtil pro;
		HttpSolrServer solrServer;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			solrServer = SolrServerUtils.getHttpSolrServer(pro.getProperty("solrTitleURL"));
			// 查询所有专辑数据。
			// 为每个专辑创建一个SolrInputDocument对象。
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			for (PingYinSearch searchItem : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				// 清除掉所有特殊字符
				String name = searchItem.getSeriesName();
				String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{}《》【】‘；：”“’。，、？·]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name);
				name = m.replaceAll("").trim();
				// 为文档添加域
				document.addField("id", searchItem.getSeriesCode());
				document.addField("play_name", name);
				document.addField("play_pingyin", searchItem.getPinyin());
				document.addField("play_pingyinsx", searchItem.getPinyinsuoxie());
				document.addField("play_zt", searchItem.getStatus());
				// 向索引库中添加文档。
				docs.add(document);
			}
			solrServer.add(docs);
			// 提交修改
			solrServer.commit();
		} catch (Exception e) {
			log.error("标题索引添加失败:" + e);
		}*/
	}

	@Override
	public void deleteTitleIndex() {
		// 创建一个SolrServer对象。
		PropertyUtil pro;
		HttpSolrServer solrServer;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			solrServer = SolrServerUtils.getHttpSolrServer(pro.getProperty("solrTitleURL"));
			// 根据查询条件删除文档
			solrServer.deleteByQuery("*:*");
			// 提交修改
			solrServer.commit();
		} catch (Exception e) {
			log.error("标题索引删除失败:" + e);
		}
	}

	@Override
	public void optimizeTitleIndex() {
		// 创建一个SolrServer对象。
		PropertyUtil pro;
		HttpSolrServer solrServer;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			solrServer = SolrServerUtils.getHttpSolrServer(pro.getProperty("solrTitleURL"));
			// 优化索引，不建议每次创建好之后都优化，定期优化即可
			solrServer.optimize();
		} catch (Exception e) {
			log.error("标题索引优化失败:" + e);
		}
	}

}
