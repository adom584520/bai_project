package com.pbtd.playuser.service;

import java.util.HashMap;

import com.pbtd.playuser.util.JsonMessage;

public interface IActivitiesInfoService {
	//获取活动项目
	public JsonMessage queryActivity(HashMap<String, Object> params);
	//用户是否参加过活动是否中奖，是否参加超过三次
	public JsonMessage isnotJoinActivities(HashMap<String,Object> params);
	//用户输入收货地址
	public JsonMessage saveAddress(HashMap<String,Object> params);
	
}
