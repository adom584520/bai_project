package com.yh.search.phone.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.phone.bean.PhoneSearchItem;
import com.yh.search.phone.bean.PingYinSearch;
import com.yh.search.phone.mapper.PhoneItemMapper;
import com.yh.search.phone.mapper.PingYinMapper;
import com.yh.search.phone.service.PhoneIndexDelIds;
import com.yh.search.utils.PropertyUtil;
import com.yh.search.utils.SolrServerUtils;

/**
 * Phone添加增量专辑到索引库
 * 
 * @author Administrator
 *
 */
@Service
public class PhoneIndexDelIdsImpl implements PhoneIndexDelIds {

	public static Logger log = Logger.getLogger(PhoneIndexDelIdsImpl.class);

	@Autowired
	private PhoneItemMapper phoneItemMapper;

	@Autowired
	private PingYinMapper pingYinMapper;

	@Override
	public void ImportDel(String id_jmmd) {
		Delete(id_jmmd);
		Import(id_jmmd);
		importTitleIndex(id_jmmd);

	}

	public void Delete(String id_jmmd) {
		PropertyUtil pro;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			HttpSolrServer solrServer = SolrServerUtils.getHttpSolrServer(pro.getProperty("solrURL"));
			// 根据查询条件删除文档
			String[] ids = id_jmmd.split(",");
			for (String id : ids) {
				solrServer.deleteByQuery("id" + ":" + id);
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
			HttpSolrServer solrServer = SolrServerUtils.getHttpSolrServer(pro.getProperty("solrURL"));
			String[] ids = id_jmmd.split(",");
			for (String id : ids) {
				// 根据专辑Id查询
				PhoneSearchItem searchItem = phoneItemMapper.getItemById(id);
				// 把商品消息添加到索引库
				SolrInputDocument document = new SolrInputDocument();
				// 为文档添加域
				document.addField("id", searchItem.getSeriesCode());
				document.addField("play_epis", searchItem.getEpis());
				document.addField("play_name", searchItem.getSeriesName());
				document.addField("play_names", searchItem.getSeriesName());
				document.addField("play_pinName", searchItem.getPinyin());
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

	public void importTitleIndex(String id_jmmd) {
		// 创建一个SolrServer对象。
		PropertyUtil pro;
		HttpSolrServer solrServer;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			solrServer = SolrServerUtils.getHttpSolrServer(pro.getProperty("solrTitleURL"));
			// 查询所有专辑数据。
			String[] ids = id_jmmd.split(",");
			for (String id : ids) {
				// 根据专辑Id查询
				PingYinSearch pingYinSearch = pingYinMapper.getItemById(id);
				// 清除掉所有特殊字符
				String name = pingYinSearch.getSeriesName();
				String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{}《》【】‘；：”“’。，、？·]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name);
				name = m.replaceAll("").trim();
				// 把专辑添加到索引库
				SolrInputDocument document = new SolrInputDocument();
				// 为文档添加域
				document.addField("id", pingYinSearch.getSeriesCode());
				document.addField("play_name", name);
				document.addField("play_pingyin", pingYinSearch.getPinyin());
				document.addField("play_pingyinsx", pingYinSearch.getPinyinsuoxie());
				document.addField("play_zt", pingYinSearch.getStatus());
				// 向索引库中添加文档。
				solrServer.add(document);
				// 提交修改
				solrServer.commit();
			}

		} catch (Exception e) {
			log.error("标题ids索引添加失败:" + e);
		}
	}

}
