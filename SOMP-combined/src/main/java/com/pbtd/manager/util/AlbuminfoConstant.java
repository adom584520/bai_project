package com.pbtd.manager.util;

public class AlbuminfoConstant {
	private AlbuminfoConstant() {
	}

	/**
	 * cp专辑数据上线状态—已上线
	 */
	public static final Integer CP_ALBUMINFO_STATUS_UPLINE = 1;
	/**
	 * cp专辑数据上线状态—已下线
	 */
	public static final Integer CP_ALBUMINFO_STATUS_DOWNLINE = 0;

	
	
	
	
	/**
	 * cp专辑绑定状态—未确认
	 */
	public static final Integer CP_ALBUMINFO_JOIN_STATUS_UNCONFIRMED = 2;
	/**
	 * cp专辑绑定状态—确认
	 */
	public static final Integer CP_ALBUMINFO_JOIN_STATUS_CONFIRMED = 1;
	/**
	 * cp专辑绑定状态—未关联
	 */
	public static final Integer CP_ALBUMINFO_JOIN_STATUS_NOT = 0;
	
	
	
	
	/**
	 * cp剧集绑定状态—未确认
	 */
	public static final Integer CP_DRAMA_JOIN_STATUS_UNCONFIRMED = 2;
	/**
	 * cp剧集绑定状态—确认
	 */
	public static final Integer CP_DRAMA_JOIN_STATUS_CONFIRMED = 1;
	/**
	 * cp剧集绑定状态—未关联
	 */
	public static final Integer CP_DRAMA_JOIN_STATUS_NOT = 0;
	
	
	
	
	/**
	 * cp频道绑定状态—未绑定
	 */
	public static final Integer CP_CHANNEL_JOIN_STATUS_NOT = 0;
	/**
	 * cp频道绑定状态—已绑定
	 */
	public static final Integer CP_CHANNEL_JOIN_STATUS_CONFIRMED = 1;

	
	
	
	/**
	 * cp的版权-全部版权
	 */
	public static final Integer CP_INFO_STATUS_ALL = 1;

	/**
	 * cp的版权-点播版权
	 */
	public static final Integer CP_INFO_STATUS_VOD = 2;
	/**
	 * cp的版权-直播版权
	 */
	public static final Integer CP_INFO_STATUS_LIVE = 3;
	/**
	 * cp的版权-无版权
	 */
	public static final Integer CP_INFO_STATUS_NOT = 4;
	
	
	
	
	
	/**
	 * 设备类型-Android
	 */
	public static final Integer DEVICE_TYPE_ANDROID = 1;
	/**
	 * 设备类型-iOS
	 */
	public static final Integer DEVICE_TYPE_IOS = 2;
	/**
	 * 设备类型-TV
	 */
	public static final Integer DEVICE_TYPE_TV = 3;
	
	
	
	
	
	/**
	 * 其他CP专辑上下线状态-已上线
	 */
	public static final Integer ALBUMINFO_STATUS_UPLINE = 1;
	/**
	 * 其他CP专辑上下线状态-已下线
	 */
	public static final Integer ALBUMINFO_STATUS_DOWNLINE = 0;

	
	
	
	/**
	 * 专辑上线类型-手机
	 */
	public static final Integer ALBUMINFO_DEVICE_TYPE_PHONE = 1;

	/**
	 * 专辑上线类型-TV
	 */
	public static final Integer ALBUMINFO_DEVICE_TYPE_TV = 2;

	
	/**
	 * 专辑上线类型-全部上线
	 */
	public static final Integer ALBUMINFO_DEVICE_TYPE_ALL = 3;
	/**
	 * 专辑上线类型-全部下线
	 */
	public static final Integer ALBUMINFO_DEVICE_TYPE_NOT = 0;

}
