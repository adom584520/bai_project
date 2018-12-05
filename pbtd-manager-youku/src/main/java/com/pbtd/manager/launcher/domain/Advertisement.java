package com.pbtd.manager.launcher.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 广告实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class Advertisement {
	private Long id;// 主键ID
	private String advName;// 广告名称
	private Long groupId;// 分组ID
	private Integer type;// 广告种类，开机广告或其他
	private Integer showType;// 视频还是图片
	private List<String> imageList = new ArrayList<>();// 图片url
	private String images;// 数据库图片url存放形式
	private String video;// 视频url
	private Integer advTime;// 播放时间
	private Integer status; // 是否上下线，0下线，1上线
	private String loginInfoName;// 创建账号名称
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间

	public List<String> getImageList() {
		return this.images != null && this.images.length() > 0 ? JSON.parseArray(this.images, String.class)
				: this.imageList;
	}
}
