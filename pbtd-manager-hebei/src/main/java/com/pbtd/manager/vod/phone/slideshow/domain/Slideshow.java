package com.pbtd.manager.vod.phone.slideshow.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 手机推荐轮播图实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class Slideshow implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer type;// 点播还是直播
	private String name;// 图片名字
	private String imageUrl;// 图片
	private String contentPoint;//内容看点
	private String targetCode;//专辑id,直播或点播地址的唯一标识
	private String playurl;// 视频播放地址
	private String weburl;// 跳转地址
	private Integer sequence;// 顺序
	private Integer status;// 上下线状态
	private String updateUser;// 编辑账号名称
	private Date updateTime;// 修改时间
}
