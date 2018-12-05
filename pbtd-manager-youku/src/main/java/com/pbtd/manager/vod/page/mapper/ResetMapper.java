package com.pbtd.manager.vod.page.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用于同步线上库 接口
 * @author zr
 */
@Mapper
public interface ResetMapper {
	
	//手机接口
	List<Map<String,Object>>  pfindalbum(Map<String,Object> map);//查询专辑
	List<Map<String,Object>>  pfindvideo(Map<String,Object> map);//查询剧集
	List<Map<String,Object>>  pfindalbumrecommend(Map<String,Object> map);//查询专辑关联推荐
	List<Map<String,Object>>  pfindchannel(Map<String,Object> map);//查询频道
	List<Map<String,Object>>  pfindchannelalbum(Map<String,Object> map);//查询频道关联数据
	List<Map<String,Object>>  pfindlabel(Map<String,Object> map);//查询标签
	List<Map<String,Object>>  pfindlabelchannel(Map<String,Object> map);//重置标签频道
	List<Map<String,Object>>  pfindspecial(Map<String,Object> map);//查询专题
	List<Map<String,Object>>  pfindspecialalbum(Map<String,Object> map);//查询专题关联数据
	List<Map<String,Object>>  pfindhotsearch(Map<String,Object> map);//查询热播
	List<Map<String,Object>>  pfindrecommandpic(Map<String,Object> map);//查询专区推荐图片
	List<Map<String,Object>> pfindbottomnavigation(Map<String,Object> map);//查询底部导航
	List<Map<String,Object>> pfindtextrecommendation(Map<String,Object> map);//查询文字描述
	List<Map<String,Object>> pfindlogo(Map<String,Object> map);//查询logo
	List<Map<String,Object>> pfindhotseries(Map<String,Object> map);//查询热播
	List<Map<String,Object>> pfindhotseriesalbum(Map<String,Object> map);//查询热播专辑
	List<Map<String,Object>> pfindslideshow(Map<String,Object> map);//查询专区轮播图
	List<Map<String,Object>> pfindstartslideshow(Map<String,Object> map);//查询开机轮播图
	
	
	//------------------------------------------------------------------------------------------------------------
	//公共接口
	List<Map<String,Object>>   findactors(Map<String,Object> map);//重置演员
	List<Map<String,Object>>   findcorner(Map<String,Object> map);//重置角标
	List<Map<String,Object>>   findcollectfeesbag(Map<String,Object> map);//重置付费包
	List<Map<String,Object>>   findmovieurl(Map<String,Object> map);//重置播放地址
	List<Map<String,Object>>  findlabeltype(Map<String,Object> map);//重置标签分类

	
	
	//------------------------------------------------------------------------------------------------------------
	 //tv接口
	List<Map<String,Object>>  tfindalbum(Map<String,Object> map);//查询专辑
	List<Map<String,Object>>  tfindvideo(Map<String,Object> map);//查询剧集
	List<Map<String,Object>>  tfindalbumrecommend(Map<String,Object> map);//查询专辑关联推荐
	List<Map<String,Object>>  tfindchannel(Map<String,Object> map);//查询频道
	List<Map<String,Object>>  tfindchannelalbum(Map<String,Object> map);//查询频道关联数据
	List<Map<String,Object>>  tfindlabel(Map<String,Object> map);//查询标签
	List<Map<String,Object>>  tfindspecial(Map<String,Object> map);//查询专题
	List<Map<String,Object>>  tfindspecialalbum(Map<String,Object> map);//查询专题关联数据
	List<Map<String,Object>>  tfindhotsearch(Map<String,Object> map);//查询热播
	List<Map<String,Object>>  tfindrecommandpic(Map<String,Object> map);//查询专区推荐图片
 
}
