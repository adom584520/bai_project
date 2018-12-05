package com.pbtd.manager.util;

public class UserVodConstant {
	private UserVodConstant() {
	}

	/**
	 * 缓存连接异常
	 */
	public static Integer REDIS_CONNECT = -110;
	/**
	 * 用户未登录状态码
	 */
	public static Integer USER_NOT_LOG_IN = 110;
	/**
	 * 用户点播使用播放的设备类型-手机
	 */
	public static String USER_VOD_PLAY_TYPE_PHONE = "1";
	/**
	 * 用户点播使用播放的设备类型-TV
	 */
	public static String USER_VOD_PLAY_TYPE_TV = "2";

	/**
	 * 用户播放记录-用户未删除
	 */
	public static Integer USER_VOD_PLAY_HISTORY_STATUS_STANDARD = 1;

	/**
	 * 用户播放记录-用户已删除
	 */
	public static Integer USER_VOD_PLAY_HISTORY_STATUS_DELETE = -1;
}
