package com.yh.search.tv.service.impl;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.tv.bean.SearchItem;
import com.yh.search.tv.mapper.TvItemMapper;
import com.yh.search.tv.service.TvIndexDelIds;
import com.yh.search.utils.PropertyUtil;
import com.yh.search.utils.SolrServerUtils;

/**
 *添加单个专辑到索引库 
 * @author Administrator
 *
 */
@Service
public class TvIndexDelIdsImpl implements TvIndexDelIds {
	
	public static Logger log = Logger.getLogger(TvIndexDelIdsImpl.class);
	
	@Autowired
	private TvItemMapper tvItemMapper;
	
	@Override
	public void ImportDel(String id_jmmd) {
		 Delete(id_jmmd);
		 Import(id_jmmd);
	}

	public void Delete(String id_jmmd) {
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
			log.error("根据ID删除文档失败: " + id_jmmd);
		}
	}

	public void Import(String id_jmmd) {
		PropertyUtil pro;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			HttpSolrServer solrServer = SolrServerUtils.getHttpSolrServer(pro.getProperty("solrTvURL"));
			String[] ids = id_jmmd.split(",");
			for (String id : ids) {
			// 根据专辑Id查询
			SearchItem searchItem = tvItemMapper.getItemById(id);
			// 把商品消息添加到索引库
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
			solrServer.add(document);
			// 提交
			solrServer.commit();
			}
		} catch (Exception e) {
			log.error("id_jmmd添加到搜索失败: " + id_jmmd);
		}

	}
	
}
