package com.pbtd.launcher.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpgradeDTO {
	private String gradeName;// 版本的名称
	private String download;// 下载链接
	private Integer type;// 版本数据对应的APK类型，如ios或TV
	private String versionInfo;// 版本的信息描述
	private String version;// 版本号
}
