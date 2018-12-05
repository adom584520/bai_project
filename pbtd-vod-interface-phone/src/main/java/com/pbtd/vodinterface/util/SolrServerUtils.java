package com.pbtd.vodinterface.util;

import org.apache.solr.client.solrj.impl.HttpSolrServer;

@SuppressWarnings("all")
public class SolrServerUtils {

	private static HttpSolrServer httpSolrServer;

	/** httpServer 是用来连接solr服务器，这里采用单例模式设计 */

	public static HttpSolrServer getHttpSolrServer(String SolrURL) {
		if (httpSolrServer == null) {
			// 用户（User）数据solr服务地址
			httpSolrServer = new HttpSolrServer(SolrURL);
			// 设置solr查询超时时间
			httpSolrServer.setSoTimeout(60000);
			// 设置solr连接超时时间
			httpSolrServer.setConnectionTimeout(3000);
			// solr最大连接数
			httpSolrServer.setDefaultMaxConnectionsPerHost(3000);
			// solr最大重试次数
			httpSolrServer.setMaxRetries(1);
			// solr所有最大连接数
			httpSolrServer.setMaxTotalConnections(3000);
			// solr是否允许压缩
			httpSolrServer.setAllowCompression(false);
			// solr是否followRedirects
			httpSolrServer.setFollowRedirects(true);
		}
		return httpSolrServer;
	}

}
