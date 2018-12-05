package com.pbtd.vodinterface.web.service.face;

import java.util.List;
import java.util.Map;

public interface IAlbumService {
	 List<Map<String,Object>> tgetchannel2Album(Map<String,Object> queryParams);
     List<Map<String,Object>> tgetchannelAlbum(Map<String,Object> queryParams);
     Map<String,Object> tgetAlbuminfo(Map<String,Object> queryParams); 
     List<Map<String,Object>> tgetAlbuminfovideo(Map<String,Object> queryParams); 
     List<Map<String,Object>> tgetAlbuminforecommend(Map<String,Object> queryParams);
     Map<String,Object> findtalbumforuser(Map<String,Object>map);//用于与用户收藏预约对接接口
     List<Map<String,Object>>  tgetAlbuminfobyactor(Map<String,Object> queryParams); //通过演员id查询所有相关专辑
     int findchannel2Albumcount(Map<String,Object> queryParams);//用于查询二级栏目换一批专辑总条数
     List<Map<String,Object>> findalbum(String seriesCode ,List<Map<String, Object>> list);//自动专辑关联推荐
     int findchannelAlbumcount(Map<String,Object>map);//查询相关频道下的专辑总条数
     List<Map<String,Object>> findmovieurl(Map<String,Object> map);	//查询返回播放字段	
}
