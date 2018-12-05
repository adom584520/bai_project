package com.pbtd.manager.vod.phone.slideshow.util;

/**
 * 手机轮播图常量工具类
 * 
 * @author JOJO
 *
 */
public class SlideshowConstant {
	private SlideshowConstant() {
	}

	/**
	 * 手机轮播图跳转类型-无跳转
	 */
	public static final Integer SLIDESHOW_TYPE_NOT = -1;
	/**
	 * 手机轮播图跳转类型-点播
	 */
	public static final Integer SLIDESHOW_TYPE_VOD = 1;
	/**
	 * 手机轮播图跳转类型-直播
	 */
	public static final Integer SLIDESHOW_TYPE_LIVE = 2;
	/**
	 * 手机轮播图跳转类型-web页面
	 */
	public static final Integer SLIDESHOW_TYPE_WEB_URL = 3;
	/**
	 * 手机轮播图跳转类型-专题
	 */
	public static final Integer SLIDESHOW_TYPE_SPECIAL = 4;
	/**
	 * 手机轮播图跳转类型-图片
	 */
	public static final Integer SLIDESHOW_TYPE_IMAGE = 5;
	/**
	 * 手机轮播图跳转类型-视频
	 */
	public static final Integer SLIDESHOW_TYPE_VIDEO = 6;
	/**
	 * 手机开机启动显示类型-图片
	 */
	public static final Integer START_SLIDESHOW_SHOW_TYPE_IMAGE = 0;
	/**
	 * 手机开机启动显示类型-视频
	 */
	public static final Integer START_SLIDESHOW_SHOW_TYPE_VIDEO = 1;
	/**
	 * 上线状态码
	 */
	public static final Integer UPLINE_STATUS = 1;
	/**
	 * 下线状态码
	 */
	public static final Integer DOWNLINE_STATUS = 0;
}
