package com.yh.search.phone.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.search.phone.bean.PingYinSearch;
import com.yh.search.phone.mapper.PingYinMapper;
import com.yh.search.phone.service.PingYinSearchService;
import com.yh.search.utils.PropertyUtil;

/**
 * 添加专辑拼音索引
 * @author 程先生
 *
 */
@Service
public class PingYinSearchServiceImpl implements PingYinSearchService {
	
	@Autowired
	private PingYinMapper pingYinMapper;

	@Override
	public void pinyinAddIndex() throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/solr.properties");
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer(pro.getProperty("solrURL"));
		// 查询所有专辑数据。
		List<PingYinSearch> itemList = pingYinMapper.getItemList();
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
			document.addField("id", name);
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

	}
}
