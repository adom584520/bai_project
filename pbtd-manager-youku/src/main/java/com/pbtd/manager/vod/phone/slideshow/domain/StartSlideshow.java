package com.pbtd.manager.vod.phone.slideshow.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 手机开机启动轮播图
 * 
 * @author JOJO
 *
 */
@Getter
@Setter
public class StartSlideshow {
	private Integer id;
	private Integer type;// 点播还是直播
	private Integer showType;//视频或者图片，0是图片，1是视频
	private String name;// 图片名字
	private String imageUrl;// 图片
	private String targetCode;// 专辑id,直播或点播地址的唯一标识
	private String playurl;// 视频播放地址
	private String weburl;// 跳转地址
	private Integer skipTime;//跳过的时间
	private Integer status;// 上下线状态
	private String updateUser;// 编辑账号名称
	private Date updateTime;// 修改时间
}
