package com.pbtd.manager.vodOnlinelibrary.service.face;

import java.util.Map;


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
	
}
