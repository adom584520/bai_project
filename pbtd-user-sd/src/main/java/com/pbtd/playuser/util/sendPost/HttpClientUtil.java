package com.pbtd.playuser.util.sendPost;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String doPost(String url, Map<String, String> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			logger.info("开始调用外部接口！开始时间：" + System.currentTimeMillis());
			logger.info("参数列表：" + map.toString());
			HttpResponse response = httpClient.execute(httpPost);
			logger.info("调用外部接口成功！结束时间：" + System.currentTimeMillis());
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			logger.error("调用外部接口失败！结束时间：" + System.currentTimeMillis(), ex);
		}
		return result;
	}

	public static String doPostjson(String url, String map, String charset) {
		String result = null;
		try {
			final HttpClient httpClient = new SSLClient();
			final HttpPost httpPost = new HttpPost(url);
			HttpResponse response = null;
			StringEntity se = new StringEntity(map, "UTF-8");
			se.setContentType("application/json");
			httpPost.addHeader("Content-type", "application/json; charset=utf-8");
			httpPost.setHeader("Accept", "application/json");
			httpPost.setEntity(new StringEntity(map, Charset.forName("UTF-8")));
			httpPost.setEntity(se);
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						HttpResponse response = httpClient.execute(httpPost);
						if (response != null) {
							HttpEntity resEntity = response.getEntity();
							if (resEntity != null) {
								String result = EntityUtils.toString(resEntity, "UTF-8");
								System.out.println(result);
							}
						}
					} catch (IllegalArgumentException ec) {
						interrupted();
					} catch (ClientProtocolException e) {
						interrupted();
					} catch (IOException e) {
						interrupted();
					}
				}
			};
			hth.start();
			// Thread.sleep(2000);
			if (response == null) {
				hth.interrupt();
				return "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}