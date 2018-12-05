package com.pbtd.manager.vodOnlinelibrary.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;

@Mapper
public interface TvOnlineMapper {
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
	int  insertspecial(Map<String,Object> map);//添加专题
	int  deletesspecial(Map<String,Object> map);//删除专题
	int  insertspecialalbum(Map<String,Object> map);//添加专题关联数据
	int  deletesspecialalbum(Map<String,Object> map);//删除专题关联数据
	int updatealbumrecommend(Map<String,Object>map);// 更新关联推荐中的专辑信息
	int updatechannelalbuminfo(Map<String,Object>map);//更新二级栏目下绑定的专辑信息
	int updatespecialalbuminfo(Map<String,Object>map);//更新专题下绑定的专辑信息
	
	int  deletechannelmodule(Map<String,Object> map);//删除模版关联数据
	int  insertchannelmodule(Map<String,Object> map);//添加模版关联数据
	
	int  deletechannelmodulealbum(Map<String,Object> map);//删除模版绑定专辑数据
	int  insertchannelmodulealbum(Map<String,Object> map);//添加模版关联数据
	
	int  deleteSingleModule(Map<String,Object> map);//删除单个模版绑定专辑数据
	int  deleteSingleModuleAlbum(Map<String,Object> map);//添加单个模版关联数据
	int  insertSingleModule(Map<String,Object> map);//添加单个模版绑定专辑数据
	int  insertSingleModuleAlbum(Map<String,Object> map);//添加单个模版关联数据
	
	int  updateChannelSeq(VodTvchannel vodTvchannel);//更新单个频道排序
	int  updateModuleSeq(Map<String,Object> map);//更新单个模块排序
}


