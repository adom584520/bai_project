package com.pbtd.launcher.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class AdvertisementDTO {
	private Long id;// 主键ID
	private String advName;// 广告名称
	private Long groupId;// 分组ID
	private Integer type;// 广告种类，开机广告或其他
	private Integer showType;// 视频还是图片
	private String video;// 视频url
	private Integer advTime;// 播放时间
	private Integer status; // 是否上下线，0下线，1上线
	private List<String> imageList = new ArrayList<>();// 图片url
	private Long version;//版本
}
