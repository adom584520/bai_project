package com.pbtd.vodinterface.web.service.face;

import java.util.List;
import java.util.Map;

public interface ICommonService {
		//频道
		List<Map<String,Object>> getChannel(Map<String,Object> map);
	    //标签 频道id(必填)
		List<Map<String,Object>> getLabel(Map<String,Object> map);
		//专题
		List<Map<String,Object>> getpgetspecial(Map<String,Object> map);
		//专题详情
		List<Map<String,Object>> getpgetspecialvideo(Map<String,Object> map);
		//频道
		List<Map<String,Object>> findChannel(Map<String,Object> map);
		//专区推荐页图片
		List<Map<String,Object>> recommandpic(Map<String,Object> map);
		List<Map<String, Object>>  recommandpiclabel(Map<String,Object> map);
		//底部导航栏
		List<Map<String, Object>>  bottomnavigation(Map<String,Object> map);
		//LOGO
		Map<String, Object>  getlogo(Map<String,Object> map);
		Map<String, Object>  getonechannel(Map<String,Object> map);
		//文字推荐
		List<Map<String, Object>>  textrecommendation(Map<String,Object> map);
		//专区推荐轮播图
		List<Map<String,Object>> slideshow(Map<String,Object> map);
		//开机启动图
		List<Map<String,Object>> startSlideshow(Map<String,Object> map);
		//获取用户自制频道相关接口
		List<Map<String, Object>>  pgetchannelforuser(Map<String,Object> map);
		//获取用户自制删除频道相关接口
		List<Map<String, Object>>  pgetchannelnotforuser(Map<String,Object> map);
		//查询用户信息
		Map<String, Object>  findUser(Map<String,Object> map);
		//自制频道添加
		int  addchannelforuser(Map<String,Object> map);
		//自制频道删除
		int  deletechannelforuser(Map<String,Object> map);
		//查询频道是否有效
		List<Map<String,Object>> findbychannel(Map<String,Object> map);
		//获取标签分类
		List<Map<String,Object>> getlabeltype(Map<String,Object> map);
}
