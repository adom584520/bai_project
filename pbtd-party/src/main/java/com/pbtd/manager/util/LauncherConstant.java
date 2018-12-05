package com.pbtd.manager.util;

public abstract class LauncherConstant {
	/**
	 * 下线状态码
	 */
	public static final Integer DOWNLINE_STATUS = 0;
	/**
	 * 上线状态码
	 */
	public static final Integer UPLINE_STATUS = 1;
	/**
	 * 开机广告位
	 */
	public static final Integer ADVERTISEMENT_TYPE_STARTING_UP = 1;
	/**
	 * launcher的启动广告位
	 */
	public static final Integer ADVERTISEMENT_TYPE_LAUNCHER_START = 2;

	/**
	 * 广告显示类型-图片
	 */
	public static final Integer ADVERTISEMENT_SHOW_TYPE_IMAGE = 1;

	/**
	 * 广告显示类型-视频
	 */
	public static final Integer ADVERTISEMENT_SHOW_TYPE_VIDEO = 2;

	/**
	 * 导航显示类型-图标
	 */
	public static final Integer NAVIGATION_SHOW_TYPE_ICON = 1;
	/**
	 * 导航显示类型-文字
	 */
	public static final Integer NAVIGATION_SHOW_TYPE_TEXT = 2;

	/**
	 * 运营位显示类型-图片
	 */
	public static final Integer OPERATION_POSITION_SHOW_TYPE_IMAGE = 0;
	/**
	 * 运营位显示类型-视频
	 */
	public static final Integer OPERATION_POSITION_SHOW_TYPE_VIDEO = 1;
	/**
	 * 运营位显示类型-特殊焦点
	 */
	public static final Integer OPERATION_POSITION_SHOW_TYPE_FOCUS = 2;
	/**
	 * 运营位显示类型-倒影
	 */
	public static final Integer OPERATION_POSITION_SHOW_TYPE_INVERTED_IMAGE = 3;
	/**
	 * 运营位显示类型-多张图片
	 */
	public static final Integer OPERATION_POSITION_SHOW_TYPE_MULTIPLE_IMAGE = 4;
	/**
	 * 运营位是否获取焦点-获取
	 */
	public static final Integer OPERATION_POSITION_OPERATION_FOCUS_YES = 0;
	/**
	 * 运营位是否获取焦点-不获取
	 */
	public static final Integer OPERATION_POSITION_OPERATION_FOCUS_NO = 1;
	/**
	 * launcher版本类型-开机广告版本
	 */
	public static final Integer LAUNCHER_VERSION_TYPE_START_ADV= 1;
	/**
	 * launcher版本类型-launcher启动广告版本
	 */
	public static final Integer LAUNCHER_VERSION_TYPE_START_LAUNCHER = 2;
	
	/**
	 * launcher数据类型是否有效-有效
	 */
	public static final Integer LAUNCHER_DATA_STATUS_VALID = 1;
	/**
	 * launcher数据类型是否有效-无效
	 */
	public static final Integer LAUNCHER_DATA_STATUS_INVALID = -1;
	
}
