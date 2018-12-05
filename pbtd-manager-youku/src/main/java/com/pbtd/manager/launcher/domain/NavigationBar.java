package com.pbtd.manager.launcher.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 导航栏实体
 * @author JOJO
 *
 */
@Setter
@Getter
public class NavigationBar {
	private Long id;
	private String text;//导航名称或者导航栏简述
	private Long groupId;// 分组ID
	private String groupName;// 分组名称
	private Integer status;// 状态0下线或1上线
	private String loginInfoName;// 创建人
	private Date createDate;// 创建时间
	private Date modifyDate;// 修改时间
}
