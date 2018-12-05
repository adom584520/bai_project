package com.yh.search.tv.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yh.search.tv.bean.TvSearchLive;
import com.yh.search.tv.service.TvLiveIndexIds;
import com.yh.search.utils.PropertyUtil;
import com.yh.search.utils.pingYinUtil;

@Service
public class TvLiveIndexIdsImpl implements TvLiveIndexIds {
	
	public static Logger log = Logger.getLogger(TvLiveIndexIdsImpl.class);

	@Override
	public void liveImport(String ids) {
		// 创建一个SolrServer对象。
		PropertyUtil pro;
		HttpSolrServer solrServer;
		try {
			pro = new PropertyUtil("app-config/solr.properties");
			solrServer = new HttpSolrServer(pro.getProperty("solrTvURL"));
			// 为每个专辑创建一个SolrInputDocument对象。
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			List<TvSearchLive> testList = JSON.parseArray(ids, TvSearchLive.class);
			for (TvSearchLive tvSearchLive : testList) {
				SolrInputDocument document = new SolrInputDocument();
				tvSearchLive.setChannelName("直播回看");
				tvSearchLive.setStatus((long) 2);
				// 清除掉所有特殊字符
				String name = tvSearchLive.getPackagename();
				String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{}《》【】‘；：”“’。，、？·]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name);
				name = m.replaceAll("").trim();
				String headChar = pingYinUtil.getPinYinHeadChar(name).toLowerCase();
				tvSearchLive.setPinyinsuoxie(headChar);
				// 为文档添加域
				document.addField("id", tvSearchLive.getPackageid());
				document.addField("play_name", tvSearchLive.getPackagename());
				document.addField("play_supid", tvSearchLive.getChncode());
				document.addField("play_categoryName",tvSearchLive.getChannelName());
				document.addField("play_pinName", tvSearchLive.getPinyinsuoxie());
				document.addField("play_image_v", tvSearchLive.getPackageposter());
				document.addField("play_image_h", tvSearchLive.getPictureurl2());
				document.addField("play_zt", tvSearchLive.getStatus());
				// 向索引库中添加文档。
				docs.add(document);
			}
			solrServer.add(docs);
			// 提交修改
			solrServer.commit();
		} catch (Exception e) {
			log.error("直播索引批量添加失败:" + e);
		}
	}
}
