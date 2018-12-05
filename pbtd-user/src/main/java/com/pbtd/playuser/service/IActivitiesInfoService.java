package com.pbtd.playuser.service;

import java.util.HashMap;

import com.pbtd.playuser.util.JsonMessage;

public interface IActivitiesInfoService {
	/**
	 *  获取所以有活动项目
	 */
	public JsonMessage queryActivity(HashMap<String, Object> params);

	/**
	 *  获取 转盘活动详细描述
	 */
	public JsonMessage queryActivityForZP(HashMap<String, Object> params);
	/**
	 * 获取 签到活动页面信息
	 */
	public JsonMessage signInActivities(HashMap<String,Object> params);

	/**
	 *  获取首次注册活动详细描述
	 */
	public JsonMessage queryActivityForFL(HashMap<String, Object> params);

	/**
	 *  获取其他活动详细描述
	 */
	public JsonMessage queryActivityForOther(HashMap<String, Object> params);

	/**
	 * 参加转盘活动
	 */
	public JsonMessage isnotJoinActivities(HashMap<String,Object> params);
	
	/**
	 * 参加签到活动
	 */
	public JsonMessage signInActivitiesJoin(HashMap<String,Object> params);

	/**
	 * 参加升级活动
	 */
	public JsonMessage upgradeActivities(HashMap<String,Object> params);
	/**
	 * 参加拉新活动
	 */
	public JsonMessage ActivityForFLJoin(HashMap<String,Object> params);
	
	/**
	 * 用户输入收货地址
	 */
	public JsonMessage saveAddress(HashMap<String,Object> params);


}
