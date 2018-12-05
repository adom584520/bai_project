package com.pbtd.playuser.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpgradeDTO {
	/**
	 * 有活动
	 */
	public static Integer ACTIVITY_EXIST = 1;
	/**
	 * 无活动
	 */
	public static Integer ACTIVITY_NOT_EXIST = 0;
	/**
	 * 无记录
	 */
	public static Integer NOT_ACTIVITYRECORD = 0;
	/**
	 * 有记录
	 */
	public static Integer ONE_ACTIVITYRECORD = 1;
	private String gradeName;// 版本的名称
	private String download;// 下载链接
	private Integer type;// 版本数据对应的APK类型，如ios或TV
	private String versionInfo;// 版本的信息描述
	private String version;// 版本号
	private Integer activity;// 1表示有活动，-1表示没有活动
	private Integer activityRecord;// 1表示参加过，-1表示没有参加
}
