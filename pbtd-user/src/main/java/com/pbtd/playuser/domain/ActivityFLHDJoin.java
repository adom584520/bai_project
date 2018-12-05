package com.pbtd.playuser.domain;

import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityFLHDJoin {
	private String commendmobile;	//推荐过的手机号
	private String createtime; //参与活动时间

	public ActivityFLHDJoin(UserActivitiesInfo active) {
		this.commendmobile = active.getCommendmobile();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");  
		this.createtime = sdf.format(active.getCreatetime());
	}
}
