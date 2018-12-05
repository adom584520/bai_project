package com.pbtd.manager.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cp频道的实体
 * 
 * @author sum
 *
 */
@Setter
@Getter
@ToString
public class CpChannel {
	private Integer id;
	private String chnCode;// 对应的己方唯一标识
	private String chnName;// 对应的己方唯一标识
	private String cpChnCode;// cp源的频道唯一标识
	private String cpChnName;// 频道的名称
	private String cpCode;// cp源标识，冗余字段
	private Integer status;// 上下线状态：1.上线，0.下线
	private Integer joinStatus;// 关联状态：0.未关联1.已确认2.未确认
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
}
