package com.pbtd.manager.launcher.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LauncherVersion implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long groupId;// 分组ID
	private Long version;// 版本号
	private Integer type;// 版本类型
}
