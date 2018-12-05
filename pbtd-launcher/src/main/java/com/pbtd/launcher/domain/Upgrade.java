package com.pbtd.launcher.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Upgrade {
	private Long id;
	private String gradeName;// 版本的名称
	private String groupId;// 分组ID，
	private String download;// 下载链接
	private Integer type;// 版本数据对应的APK类型，如ios或TV
	private Integer status;//状态为1表示新版本，状态为2表示旧版本
	private String versionInfo;// 版本的信息描述
	private String version;// 版本号
	private String logininfoName;// 修改账号
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
}
