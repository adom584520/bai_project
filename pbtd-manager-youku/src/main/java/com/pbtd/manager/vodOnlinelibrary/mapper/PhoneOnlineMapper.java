package com.pbtd.manager.vodOnlinelibrary.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhoneOnlineMapper {
	int  insertalbum(Map<String,Object> map);//添加专辑
	int  deletealbum(Map<String,Object> map);//删除专辑
	int  insertvideo(Map<String,Object> map);//添加剧集
	int  deletesvideo(Map<String,Object> map);//删除剧集
	int  addalbumrecommend(Map<String,Object> map);//添加专辑关联推荐
	int  deletealbumrecommend(Map<String,Object> map);//删除专辑关联推荐
	int  insertchannel(Map<String,Object> map);//添加频道
	int  deleteschannel(Map<String,Object> map);//删除频道
	int  insertchannelalbum(Map<String,Object> map);//添加频道关联数据
	int  deleteschannelalbum(Map<String,Object> map);//删除频道关联数据
	int  insertlabel(Map<String,Object> map);//添加标签
	int  deleteslabel(Map<String,Object> map);//删除标签
	int  insertlabelchannel(Map<String,Object> map);//添加标签
	int  deleteslabelchannel(Map<String,Object> map);//删除标签
	int  insertspecial(Map<String,Object> map);//添加专题
	int  deletesspecial(Map<String,Object> map);//删除专题
	int  insertspecialalbum(Map<String,Object> map);//添加专题关联数据
	int  deletesspecialalbum(Map<String,Object> map);//删除专题关联数据
	int  inserthotsearch(Map<String,Object> map);//添加热播
	int  deleteshotsearch(Map<String,Object> map);//删除热播
	
	int  insertrecommandpic(Map<String,Object> map);//添加专区推荐图片
	int  deleterecommandpic(Map<String,Object> map);//删除专区推荐图片
	
	int  insertbottomnavigation(Map<String,Object> map);//添加底部导航
	int  deletebottomnavigation(Map<String,Object> map);//删除底部导航
	
	int  inserttextrecommendation(Map<String,Object> map);//添加文字描述
	int  deletetextrecommendation(Map<String,Object> map);//删除文字描述
	
	int  insertlogo(Map<String,Object> map);//添加logo
	int  deletelogo(Map<String,Object> map);//删除logo
	
	int  insertmovieurl(Map<String,Object> map);//添加播放地址
	int  deletemovieurl(Map<String,Object> map);//删除播放地址
	
	int  inserthotseries(Map<String,Object> map);//添加热播推荐
	int inserthotseriesalbum(Map<String,Object> map);//添加热播推荐专辑
	int  deletehotseries(Map<String,Object> map);//删除热播推荐
	int  deletehotseriesalbum(Map<String,Object> map);//删除热播推荐专辑
	
	int  insertslideshow(Map<String,Object> map);//添加开机轮播图
	int  deleteslideshow(Map<String,Object> map);//删除开机轮播图
	
	int  insertstartslideshow(Map<String,Object> map);//添加开机轮播图
	int  deletestartslideshow(Map<String,Object> map);//删除开机轮播图
	int updatealbumrecommend(Map<String,Object>map);// 更新关联推荐中的专辑信息
	int updatechannelalbuminfo(Map<String,Object>map);//更新二级栏目下绑定的专辑信息
	int updatespecialalbuminfo(Map<String,Object>map);//更新专题下绑定的专辑信息
	int updatehotsearchalbuminfo(Map<String,Object>map);//更新热搜下绑定的专辑信息
	int updatehotseriesalbuminfo(Map<String,Object>map);//更新热播推荐下绑定的专辑信息
	
	
	int  insertlabeltype(Map<String,Object> map);//添加标签分类
	int  deletelabeltype(Map<String,Object> map);//删除标签分类
	
	
	
	
	
	
}
