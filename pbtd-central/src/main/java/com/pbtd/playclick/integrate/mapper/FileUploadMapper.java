package com.pbtd.playclick.integrate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface FileUploadMapper {
	List<Map<String,Object>> album(Map<String,Object> params);
	List<Map<String,Object>> albumvideo(Map<String,Object> params);
	int insertvodcpimg(Map<String,Object> params);
	int  deletevodcpimg(Map<String,Object> params);
	 
	
	//更改入库后的图片
	int updatealbum(Map<String,Object> params);
	int updatealbumvideo(Map<String,Object> params);//更改剧集图片
	
	//国广银河 合一 自动入库 专辑和剧集图片爬取
	List<Map<String,Object>> album_strategy(Map<String,Object> params);
	List<Map<String,Object>> albumvideo_strategy(Map<String,Object> params);
	int updatealbum_strategy(Map<String,Object> params);
	int updatealbumvideo_strategy(Map<String,Object> params);
 
	

	//youku 自动入库 专辑和剧集图片爬取
	List<Map<String,Object>> youku_album_strategy(Map<String,Object> params);
	List<Map<String,Object>> youku_albumvideo_strategy(Map<String,Object> params);
	int updateyouku_album_strategy(Map<String,Object> params);
	int updateyouku_albumvideo_strategy(Map<String,Object> params);
}
