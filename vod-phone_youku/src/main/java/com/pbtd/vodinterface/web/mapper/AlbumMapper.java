package com.pbtd.vodinterface.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AlbumMapper {
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
	Map<String,Object> album(String seriesCode);//查询某个专辑
	List<Map<String,Object>> findalbumOne(Map<String,Object> map);//相关推荐演员
	List<Map<String,Object>> findalbumTwo(Map<String,Object> map);//相关推荐导演
	List<Map<String,Object>> findalbumThree(@Param("ChannelName")String ChannelName);//相关推荐频道
	int findchannel2Albumcount(Map<String,Object> map);//查询二级栏目下的专辑总条数
	int findhotseriescount(Map<String,Object> map);//查询热播推荐总条数
	int findchannelAlbumcount(Map<String,Object> map);//查询推荐页根据频道查询专辑总条数
	
	List<Map<String,Object>> pfindmovieurl(Map<String,Object> map);	//查询返回播放字段	
	
	List<Map<String,Object>> findsearchalbum(Map<String,Object> map);	//搜索专辑
	int findsearchalbumcount(Map<String,Object> map);//搜索专辑总数
	
}
