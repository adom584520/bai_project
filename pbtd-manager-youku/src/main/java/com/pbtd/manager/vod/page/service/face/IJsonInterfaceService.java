package com.pbtd.manager.vod.page.service.face;

import java.util.Map;

public interface IJsonInterfaceService {
	//获取手机数据
	int phonealbuminfo(Map<String,Object> map);
	int phonealbuminfovideo(Map<String,Object> map);
	int phonealbuminforecommend(Map<String,Object> map);
	int phonechannel(Map<String,Object> map);
	int phonelabel(Map<String,Object> map);
	int phonehotsearch(Map<String,Object> map);
	int phonespecial(Map<String,Object> map);
	int phonerecommandpic(Map<String,Object> map);
	int phonetextrecommendation(Map<String,Object> map);
	int phoneslideshow(Map<String,Object> map);
	int phonestartslideshow(Map<String,Object> map);
	int phonehotseries(Map<String,Object> map);
	int labeltype(Map<String,Object> map);
	int cpsourcetype(Map<String,Object> map);
	
	//获取tv数据
	int tvalbuminfo(Map<String,Object> map);
	int tvalbuminfovideo(Map<String,Object> map);
	int tvalbuminforecommend(Map<String,Object> map);
	int tvchannel(Map<String,Object> map);
	int tvlabel(Map<String,Object> map);
	int tvhotsearch(Map<String,Object> map);
	int tvspecial(Map<String,Object> map);
	
	//获取中心数据
	int actors(Map<String,Object> map);
	int corner(Map<String,Object> map);
	int collectfeesbag(Map<String,Object> map);

}
