package com.pbtd.vodinterface.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AlbumMapper {
     List<Map<String,Object>> tgetchannel2Album(Map<String,Object> queryParams);
     List<Map<String,Object>> tgetchannelAlbum(Map<String,Object> queryParams);
     Map<String,Object> tgetAlbuminfo(Map<String,Object> queryParams); 
     List<Map<String,Object>> tgetAlbuminfovideo(Map<String,Object> queryParams); 
     List<Map<String,Object>> tgetAlbuminforecommend(Map<String,Object> queryParams);
     Map<String,Object> findtalbumforuser(Map<String,Object>map);//用于与用户收藏预约对接接口
     List<Map<String,Object>>  tgetAlbuminfobyactor(Map<String,Object> queryParams); //通过演员id查询所有相关专辑
     int findchannel2Albumcount(Map<String,Object> queryParams);//用于查询二级栏目换一批专辑总条数
     Map<String,Object> album(String seriesCode);//查询某个专辑
     List<Map<String,Object>> findalbumOne(Map<String,Object> map);//相关推荐演员
 	 List<Map<String,Object>> findalbumTwo(Map<String,Object> map);//相关推荐导演
 	 List<Map<String,Object>> findalbumThree(@Param("ChannelName")String ChannelName);//相关推荐频道
 	 int findchannelAlbumcount(Map<String,Object>map);//查询相关频道下的专辑总条数
 	List<Map<String,Object>> findmovieurl(Map<String,Object> map);	//查询返回播放字段	
}
