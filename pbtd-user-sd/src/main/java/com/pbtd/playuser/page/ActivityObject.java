package com.pbtd.playuser.page;

import com.pbtd.playuser.domain.ActivitiesBaseInfo;
import com.pbtd.playuser.domain.ActivityPrizesOutPut;
import com.pbtd.playuser.util.JsonMessage;

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
public class ActivityObject<T> extends JsonMessage{
	public String  activityName;//活动code
	public String  activityDescribe;//活动code
	public String  activityPicture;//活动图片
	public int  residcode;//用户剩余次数
	public T data ;//返回奖品列表 
	public ActivityObject(int code,String message) {
		this.code = code;
		this.message = message;
	}
	public ActivityObject(int code, ActivitiesBaseInfo active,java.util.List<ActivityPrizesOutPut> prizelist, int i) {
		this.code = code;
		this.message = "获取成功";
		this.activityName = active.getActivename();
		this.activityDescribe = active.getActivedescribe();
		this.activityPicture = active.getActivepicture();
		this.residcode = i;
		this.data = (T) prizelist;
	}
}
