package com.pbtd.launcher.dto;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class LauncherUIDTO {
	private Long id; // 分组详细信息ID
	private Long groupId; // launcherGroup的id
	private String logo; // logo图片url地址
	private String posterBackground; // 背景海报url地址
	private String pickerView;// 选中框url地址
	private String watchBackground;// 看点背景url地址
}
