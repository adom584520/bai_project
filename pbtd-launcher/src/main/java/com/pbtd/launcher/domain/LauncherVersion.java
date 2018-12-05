package com.pbtd.launcher.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LauncherVersion {
	private Long id;
	private Long groupId;// 分组ID
	private Long version;// 版本号
	private Integer type;// 版本类型
}
