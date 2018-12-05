package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 手机和tv收藏信息实体
 * @author JOJO
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VodCollectInfo {
	private Integer id;
	private String userId;// 用户ID
	private String mac;// MAC
	private Integer palypos;// 用户播放剧集时长(错误名称)
	private Integer playpos;// 用户播放剧集时长
	private Integer drama;// 播放剧集
	private Integer dram;// 播放剧集(错误名称)
	private String dramaname;// 播放剧集名称
	private Integer seriesCode;// 专辑ID
	private String pictureurl1;// 图片1
	private String pictureurl2;// 图片2
	private Integer duration;// 剧集总时长
	private String viewPoint;// 专辑看点
	private String seriesName;// 专辑名称
	private Date createTime;// 创建时间
	
	public void setDram(Integer dram) {
		this.drama = dram;
		this.dram = dram;
	}
	public void setDrama(Integer drama) {
		this.drama = drama;
		this.dram = drama;
	}
	public void setPlaypos(Integer playpos) {
		this.palypos = playpos;
		this.playpos = playpos;
	}

	public void setPalypos(Integer playpos) {
		this.palypos = playpos;
		this.playpos = playpos;
	}
}
