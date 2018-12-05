package com.pbtd.manager.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cp媒资的剧集实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString

public class CpDrama {
	private Long id;
	private Long cpSeriesCode;// cp方的专辑ID
	private Integer cpDrama; // cp方的剧集号
	private String cpDramaname;// cp方的剧集名称
	private Integer drama; // 己方的剧集号
	private String dramaname;// 己方的剧集名称
	private String type;// 介质类型
	private String duration;// 播放时长
	private String description;// 描述信息
	private String cpCode;// cp方的唯一标识
	private Integer joinStatus;// 关联关系状态：0.未关联1.确认2.未确认
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
}
