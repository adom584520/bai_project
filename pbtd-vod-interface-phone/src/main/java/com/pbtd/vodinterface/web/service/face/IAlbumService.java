package com.pbtd.vodinterface.web.service.face;

import java.util.List;
import java.util.Map;

public interface IAlbumService {
	List<Map<String,Object>> pgetAlbum(Map<String,Object> map);
	List<Map<String,Object>> pgetchannel2Album(Map<String,Object> map);//用于二级栏目查询专辑列表
	List<Map<String,Object>> pgetchannelAlbum(Map<String,Object> map);//用于频道查询专辑列表
	Map<String,Object> pgetAlbuminfo(Map<String,Object> map);//专辑详情
	List<Map<String,Object>> pgetAlbuminfovideo(Map<String,Object> map);//用于专辑剧集列表
	List<Map<String,Object>> pgetAlbuminforecommend(Map<String,Object> map);//用于专辑关联推荐
	List<Map<String,Object>> pgetAlbumhotsearch(Map<String,Object> map);//用于热搜
	List<Map<String,Object>> findchannelhot(Map<String,Object> map);//用于频道查询热播
	List<Map<String,Object>> findhotseriescode(Map<String,Object> map);//热播详情
	Map<String,Object> findpalbumforuser(Map<String,Object>map);//用于与用户收藏预约对接接口
	List<Map<String,Object>> findcount(Map<String,Object>map);//用于查询热播推荐返回条数
	int findAlbuminfovideocount(Map<String,Object>map);//用于获取专辑总条数
	List<Map<String,Object>> findalbum(String seriesCode ,List<Map<String, Object>> list);//第一种查数据库(自动专辑关联推荐)
	List<Map<String,Object>> findalbums(String seriesCode ,List<Map<String, Object>> list);//第二种通过HTTP请求solr客户端(自动专辑关联推荐)
	List<Map<String,Object>> findalbumSolr(String solrURL ,String seriesCode ,List<Map<String, Object>> list);//第三种集成solrClient端(自动专辑关联推荐)
	int findchannel2Albumcount(Map<String,Object> map);//查询二级栏目下的专辑总条数
	int findhotseriescount(Map<String,Object> map);//查询热播推荐总条数
	int findchannelAlbumcount(Map<String,Object> map);//查询推荐页根据频道查询专辑总条数
	List<Map<String,Object>> pfindmovieurl(Map<String,Object> map);	//查询返回播放字段
	
	List<Map<String,Object>> findsearchalbum(Map<String,Object> map);	//搜索专辑
	int findsearchalbumcount(Map<String,Object> map);//搜索专辑总数
	
	
	Map<String,Object> pgetAlbuminfo_videoid(Map<String,Object> map);//根据videoid查询详情
	List<Map<String,Object>> loadalbumpaid(Map<String,Object> map);//查询该专辑时候收费
	
	
}
