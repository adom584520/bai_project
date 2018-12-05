package com.pbtd.vodinterface.web.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.vodinterface.util.SolrClient;
import com.pbtd.vodinterface.util.SolrServerUtils;
import com.pbtd.vodinterface.web.domain.SearchItem;
import com.pbtd.vodinterface.web.mapper.AlbumMapper;
import com.pbtd.vodinterface.web.service.face.IAlbumService;

@Service
public class AlbumService implements IAlbumService {
	@Autowired
	private AlbumMapper albummapper;

	@Override
	public List<Map<String, Object>> pgetAlbum(Map<String, Object> map) {
		return albummapper.pgetAlbum(map);
	}

	@Override
	public List<Map<String, Object>> pgetchannel2Album(Map<String, Object> map) {
		return albummapper.pgetchannel2Album(map);
	}

	@Override
	public List<Map<String, Object>> pgetchannelAlbum(Map<String, Object> map) {
		return albummapper.pgetchannelAlbum(map);
	}

	@Override
	public Map<String, Object> pgetAlbuminfo(Map<String, Object> map) {
		return albummapper.pgetAlbuminfo(map);
	}

	@Override
	public List<Map<String, Object>> pgetAlbuminfovideo(Map<String, Object> map) {
		return albummapper.pgetAlbuminfovideo(map);
	}

	@Override
	public List<Map<String, Object>> pgetAlbuminforecommend(Map<String, Object> map) {
		return albummapper.pgetAlbuminforecommend(map);
	}

	@Override
	public List<Map<String, Object>> pgetAlbumhotsearch(Map<String, Object> map) {
		return albummapper.pgetAlbumhotsearch(map);
	}

	@Override
	public List<Map<String, Object>> findchannelhot(Map<String, Object> map) {
		return albummapper.findchannelhot(map);
	}

	@Override
	public List<Map<String, Object>> findhotseriescode(Map<String, Object> map) {
		return albummapper.findhotseriescode(map);
	}

	@Override
	public Map<String, Object> findpalbumforuser(Map<String, Object> map) {
		 Map<String, Object>  parmemap  = new HashMap<>();
		 parmemap =  albummapper.findpalbumforuser(map);
		 if(parmemap == null){
			 parmemap = map;
		 }
		 String  parmemap2 =  albummapper.findchannelpalbumforuser(map);
		 parmemap.put("channellist", parmemap.get("Channel"));
		return parmemap;
	}

	// 第一种查数据库(自动专辑关联推荐)
	@Override
	public List<Map<String, Object>> findalbum(String seriesCode, List<Map<String, Object>> list) {

		return list;
	}

	// 第二种通过HTTP请求solr客户端(自动专辑关联推荐)
	@Override
	public List<Map<String, Object>> findalbums(String solr_client, List<Map<String, Object>> list) {

		return list;
	}

	// 第三种集成solrClient端(自动专辑关联推荐)
	@Override
	public List<Map<String, Object>> findalbumSolr(String solrURL, String seriesCode, List<Map<String, Object>> list) {
		// 创建一个SolrServer对象
		SolrServer solrServer = SolrServerUtils.getHttpSolrServer(solrURL);
		// 创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(seriesCode);
		// 需要指定默认搜索域。
		query.set("df", "id");
		// 根据query对象查询索引库
		QueryResponse response;
		List<SearchItem> itemList = new ArrayList<>();
		try {
			response = solrServer.query(query,METHOD.POST);
			// 取专辑
			SolrDocumentList solrDocumentList = response.getResults();
			if (solrDocumentList.size() > 0) {
				SolrDocument solrDocument = solrDocumentList.get(0);
				//演员
				String actor = solrDocument.get("play_actor").toString();
				//频道
				String categoryName = solrDocument.get("play_categoryName").toString();
				// 有演员
				if (actor != null && !"".equals(actor)) {
					// 有演员,有频道
					if (categoryName != null && !"".equals(categoryName)) {
						itemList = SolrClient.SubSearch1(actor, categoryName, seriesCode, solrURL);
					} else {
						// 有演员,无频道
						itemList = SolrClient.SubSearch2(actor, seriesCode, solrURL);
					}
					// 无演员
				} else {
					// 无演员,有频道
					if (categoryName != null && !"".equals(categoryName)) {
						itemList = SolrClient.SubSearch3(categoryName, seriesCode, solrURL);
					} else {
						// 无演员,无频道
						itemList = SolrClient.SubSearch4(seriesCode, solrURL);
					}
				}
			} else {// 无数据
				Map<String, Object> map = albummapper.album(seriesCode);
				if (map.size() > 0) {
					itemList = SolrClient.SubSearch4(seriesCode, solrURL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = list.size();// 取10条
		// 添加专辑
		for (int i = 0; i < itemList.size() && count <= 7; i++) {
			int listNum = 0;
			for (int j = 0; j < list.size(); j++) {
				if (itemList.get(i).getSeriesCode().equals(list.get(j).get("seriesCode").toString())) {
					listNum++;
				}
			}
			if (listNum == 0) {
				// 将javaBean 转换为map
				Map<String, Object> map = transBean2Map(itemList.get(i));
				list.add(map);
				count++;
			}
		}
		if (count >= 7) {
			Iterator<Map<String, Object>> iteratorOne = list.iterator();
			while (iteratorOne.hasNext()) {
				if (seriesCode.equals(iteratorOne.next().get("seriesCode").toString())) {
					iteratorOne.remove();
				}
			}
			return list.subList(0, 6);
		}
		return list;
	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	@Override
	public int findAlbuminfovideocount(Map<String, Object> map) {
		return albummapper.findAlbuminfovideocount(map);
	}

	@Override
	public List<Map<String, Object>> findcount(Map<String, Object> map) {
		return albummapper.findcount(map);
	}

	@Override
	public int findchannel2Albumcount(Map<String, Object> map) {
		return albummapper.findchannel2Albumcount(map);
	}

	@Override
	public int findhotseriescount(Map<String, Object> map) {
		return albummapper.findhotseriescount(map);
	}

	@Override
	public int findchannelAlbumcount(Map<String, Object> map) {
		return albummapper.findchannelAlbumcount(map);
	}

	@Override
	public List<Map<String, Object>> pfindmovieurl(Map<String, Object> map) {
		return albummapper.pfindmovieurl(map);
	}

	@Override
	public List<Map<String, Object>> findsearchalbum(Map<String, Object> map) {
		return albummapper.findsearchalbum(map);
	}

	@Override
	public int findsearchalbumcount(Map<String, Object> map) {
		return albummapper.findsearchalbumcount(map);
	}

	@Override
	public Map<String, Object> pgetAlbuminfo_videoid(Map<String, Object> map) {
		return albummapper.pgetAlbuminfo_videoid(map);
	}

	@Override
	public List<Map<String, Object>> loadalbumpaid(Map<String, Object> map) {
		return albummapper.loadalbumpaid(map);
	}

}
