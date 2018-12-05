package com.pbtd.manager.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BindingInfoQueryObject {
	private String cpCode;// cp方的唯一标识
	private Integer status;// 绑定关系状态
}
