package com.pbtd.manager.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 直播频道实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Channel {
	private Integer id;
	private String oldChnCode;// 旧的频道ID
	private String chnCode;// 频道唯一标识
	private String chnName;// 频道名称
	private String packageCover;// 频道图片
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
}
