package com.pbtd.launcher.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * launhcer分组开机信息
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class LauncherUI {
	private Long id; // 分组详细信息ID
	private Long groupId; // launcherGroup的id
	private String groupName; // 分组的名称，对象冗余属性
	private String logo; // logo图片url地址
	private String posterBackground; // 背景海报url地址
	private String pickerView;// 选中框url地址
	private String watchBackground;// 看点背景url地址
	private Integer status; // 是否上下线，0下线，1上线
	private String loginInfoName;// 创建账号名称
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
}
