package com.pbtd.launcher.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NavigationDTO {
	private Long id;// 导航id
	private Integer showType;// 导航显示的类型
	private String text;// 导航文字
	private String normalIcon;// 默认图标
	private String pressIcon;// 按下图标
	private String selectedIcon;// 选中图标
	private Integer sortpos;// 导航的位置，排序序号
	private String packageName;// 跳转包名
	private String className;// 跳转类名
	private String paramKey;// 跳转参数key值
	private List<String> paramKeys = new ArrayList<String>(20);// 图片的集合，图片属性的冗余字段
	private String paramValue;// 跳转参数value值
	private List<String> paramValues = new ArrayList<String>(20);// 图片的集合，图片属性的冗余字段

}
