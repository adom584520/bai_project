package com.pbtd.manager.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 己方媒资剧集信息实体
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
public class Drama {
	private Long id;
	private Long seriesCode;//专辑Code
	private Integer drama;//剧集号
	private String dramaname;//剧集名称
	private String type;//介质类型
	private String duration;//播放时长
	private String description;//描述信息
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
}
