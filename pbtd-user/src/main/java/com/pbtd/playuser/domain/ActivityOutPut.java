package com.pbtd.playuser.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivityOutPut {
	private Integer activitycode = 0;//获奖值：0，未获奖，1-6为奖品，详见数据库
	private Integer residcode;//剩余次数

	
	public ActivityOutPut(int code) {
	this.activitycode = 0;
	this.residcode = code;
	}
	public ActivityOutPut(int activitycode,int code ) {
		this.activitycode = activitycode;
		this.residcode = code;
	}
}