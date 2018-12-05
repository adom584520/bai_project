package com.pbtd.playuser.page;

import com.pbtd.playuser.domain.ActivitiesBaseInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回消息实体
 * 
 * @author JOJO
 * @param <T>
 *
 */
@Setter
@Getter
public class ActivityObjectOutPut{
	public String  activityCode;//活动code
	public String  activityName;//活动code
	public String  activityDescribe;//活动code
	
	
	
	public ActivityObjectOutPut(ActivitiesBaseInfo active) {
		this.activityCode = active.getActivecode();
		this.activityName = active.getActivename();
		this.activityDescribe = active.getActivedescribe();
	}
}
