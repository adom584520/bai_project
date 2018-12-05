package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityPrizesInfo {
	private Integer id;

	private Integer prizecode;// 奖品标识

	private Integer prizenum;// 奖品数量

	private Integer prizeresnum;// 库存

	private String language;// 中奖信息

	private Date createtime;// 创建时间

	private Date updatetime;// 修改时间

	private String activename;// 活动标识

	private Date starttime;// 活动开始时间

	private Date endtime;// 活动结束时间

	private String prizename;// 奖品名称

	private Integer probability;// 中奖概率
	
	private Integer version;// 版本
	
//	public ActivityPrizesInfo(int code,String message) {
//		this.prizecode = code;
//		this.language = message;
//	}
}