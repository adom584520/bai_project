package com.pbtd.manager.launcher.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 导航实体
 * 
 * @author JOJO
 *
 */
@Getter
@Setter
public class Navigation {
	private Long id;// 导航id
	private String navName;// 导航名称
	private Long groupId;// 分组的id
	private Integer showType;//导航显示的类型
	private String text;//导航文字
	private String normalIcon;//默认图标
	private String pressIcon;//按下图标
	private String selectedIcon;//选中图标
	private Integer sortpos;// 导航的位置，排序序号
	private String packageName;// 跳转包名
	private String className;// 跳转类名
	private String paramKey;// 跳转参数key值
	private String paramValue;// 跳转参数value值
	private Integer status;// 上下线状态
	private String loginInfoName;// 创建账号名称
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
}
