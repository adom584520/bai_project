package com.pbtd.manager.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cp方的点播收藏记录信息实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
public class CpVodFavorite {
	private Long id;
	private String deviceId;// 设备唯一标识符，手机为userId，tv为mac
	private Integer deviceType;// 设备类型，1.手机2.tv
	private String seriesCode;// 专辑ID
	private String seriesName;// 专辑名称
	private Integer drama;// 剧集号
	private String dramaname;// 剧集名称
	private String duration;// 播放总时长
	private Integer playpos;// 剧集播放时长
	private String viewPoint;// 内容看点
	private String pictureurl1;// 图片1
	private String pictureurl2;// 图片2
	private String cpCode;// cp方的唯一标识
	private Date createTime;// 创建时间

}
