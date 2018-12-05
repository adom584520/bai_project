package com.pbtd.manager.query;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CpChannelQueryObject extends QueryObject {
	private String cpCode;//己方频道code
	private String chnCode;//己方频道名称
	private String cpChnCode;//cp频道code
	private String cpChnName;//cp频道名称
	private Integer status;// 上下线状态
	private Integer joinStatus;// 绑定状态
}
