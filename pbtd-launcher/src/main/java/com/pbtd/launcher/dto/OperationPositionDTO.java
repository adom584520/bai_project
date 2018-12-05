package com.pbtd.launcher.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OperationPositionDTO {
	private Long id;
	private String titleName;// 图片上文字标题
	private String titleDetail;// 内容看点
	private Integer showTitle;// 0显示，1不显示
	private Integer playTime;// 播放时间
	private Integer sortpos;// 运营位位置
	private Integer showType;// 0图片，1视频，2特殊焦点，3倒影，4多张图片
	private Integer focus;// 0获取焦点，1不获取焦点，默认0
	private Integer topMargin;// 控件上边距
	private Integer leftMargin;// 空间左边距
	private Integer width;// 控件宽度
	private Integer height;// 控件高度
	private String video;// 视频播放URL
	private String image;// 单张图片
	private String packageName;// 跳转包名
	private String className;// 跳转类名
	private String images;// 多张图片
	private Integer optionFocus;//是否是默认焦点，是为1不是为0
	private List<String> imageList = new ArrayList<String>(20);// 图片的集合，图片属性的冗余字段
	private String paramKey;// 跳转参数key值
	private List<String> paramKeys = new ArrayList<String>(20);// 图片的集合，图片属性的冗余字段
	private String paramValue;// 跳转参数value值
	private List<String> paramValues = new ArrayList<String>(20);// 图片的集合，图片属性的冗余字段
}
