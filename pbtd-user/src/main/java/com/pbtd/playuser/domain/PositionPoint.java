package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 运营位打点实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class PositionPoint {
	private Integer id;
	private Integer pos;// 位置
	private String code;// 专辑或直播ID
	private Integer type;// 类型
	private String channel;// 频道
	private String userId;// 用户ID
	private Date createTime;// 创建时间
}
