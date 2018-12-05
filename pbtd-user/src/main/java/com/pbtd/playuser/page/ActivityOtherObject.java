package com.pbtd.playuser.page;

import java.text.SimpleDateFormat;

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
public class ActivityOtherObject<T>{
	public String  activityCode;//活动code
	public String  activityName;//活动code
	public String  activityDescribe;//活动code
	public String  activityPicture;//活动海报
	public String  activityStart;//活动开始
	public String  activityEnd;//活动结束
	public ActivityOtherObject(ActivitiesBaseInfo active) {
		this.activityCode = active.getActivecode();
		this.activityName = active.getActivename();
		this.activityDescribe = active.getActivedescribe();
		this.activityPicture = active.getActivepicture();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");  
		this.activityStart = sdf.format(active.getStarttime());
		this.activityEnd = sdf.format(active.getEndtime());
	}
}
