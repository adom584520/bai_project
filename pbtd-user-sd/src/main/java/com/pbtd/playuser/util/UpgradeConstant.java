package com.pbtd.playuser.util;

public class UpgradeConstant {
	private UpgradeConstant() {
	};

	/**
	 * upgrade新版本
	 */
	public static final Integer UPGRADE_STATUS_NEW = 1;

	/**
	 * upgrade旧版本
	 */
	public static final Integer UPGRADE_STATUS_FORMER = 2;

	/**
	 * upgrade版本类型-Android
	 */
	public static final Integer UPGRADE_TYPE_ANDROID = 1;
	/**
	 * upgrade版本类型-iOS
	 */
	public static final Integer UPGRADE_TYPE_IOS = 2;
	/**
	 * upgrade版本类型-TV直播
	 */
	public static final Integer UPGRADE_TYPE_TV_LIVE = 3;
	/**
	 * upgrade版本类型-TV-Launcher
	 */
	public static final Integer UPGRADE_TYPE_TV_LAUNCHER = 4;
	/**
	 * upgrade版本类型-TV-点播
	 */
	public static final Integer UPGRADE_TYPE_TV_VOD = 5;

	/**
	 * upgrade版本类型-党建
	 */
	public static final Integer UPGRADE_TYPE_PARTY_BUILDING = 6;
	
	/**
	 * upgrade版本地区分组-河北
	 */
	public static final String UPGRADE_GROUP_ID_HEBEI = "河北";

	/**
	 * upgrade版本地区分组-山东
	 */
	public static final String UPGRADE_GROUP_ID_SHANDONG = "山东";
}
