package com.pbtd.manager.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cp方的频道收藏记录信息实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
public class CpLiveFavorite {
	private Long id;
	private String deviceId;// 设备唯一标识符，手机为userId，tv为mac
	private Integer deviceType;// 设备类型，1.手机2.tv
	private String chnCode;// 频道ID
	private String chnName;// 频道名称
	private String packageCover;// 频道图片url
	private String cpCode;// cp方的唯一标识
	private Date createTime;// 创建时间
}
