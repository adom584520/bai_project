package com.pbtd.manager.vodOnlinelibrary.service.face;

import java.util.Map;

import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;


public interface IPhoneOnlineService {
	int  resetalbum(Map<String,Object> map);//重置专辑
	int  resetchannel(Map<String,Object> map);//重置频道
	int  resetlabel(Map<String,Object> map);//重置标签
	int  resetspecial(Map<String,Object> map);//重置专题
	int  resethotsearch(Map<String,Object> map);//重置热播
	int  resetrecommandpic(Map<String,Object> map);//重置专区推荐图片
	int  resetbottomnavigation(Map<String,Object> map);//重置底部导航
	int  resettextrecommendation(Map<String,Object> map);//重置文字描述
	int  resetlogo(Map<String,Object> map);//重置logo
	int  resethotseries(Map<String,Object> map);//重置热播推荐
	int  resetstartslideshow(Map<String,Object> map);//重置开机轮播图
	int  resetslideshow(Map<String,Object> map);//重置开机轮播图
	int  resetmovieurl(Map<String,Object> map);//重置播放地址
	int resetlabeltype(Map<String,Object> map);//重置标签分类
	int resetModuleSeq(Map<String,Object> map) throws Exception;//更新单个模块排序
	int  resetSingleMoudle(Map<String,Object> map) throws Exception; //重置phone单个模块
	int  resetChannelSeq(VodTvchannel vodTvchannel) throws Exception;//更新单个频道排序
	int  resetmodule(Map<String,Object> map);//重置phone模块
	int resetAllmodule(Map<String, Object> map); //重置全部phone频道下模块
	
	int  resetphonepaidalbum(Map<String,Object> map);//重置phone 收费专辑
	
	int  resetphonealbumvideo_interface(Map<String,Object> map);//自动同步剧集
	int  resetphonealbum_interface(Map<String,Object> map);//自动同步增量专辑
}
