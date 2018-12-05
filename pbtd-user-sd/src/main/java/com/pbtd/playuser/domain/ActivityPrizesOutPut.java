package com.pbtd.playuser.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivityPrizesOutPut {
	private Integer prizecode;// 奖品标识
	private String prizename;// 奖品名称
	
	public ActivityPrizesOutPut(int prizecode,String prizename ) {
		this.prizecode = prizecode;
		this.prizename = prizename;
	}
}