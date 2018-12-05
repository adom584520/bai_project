package com.pbtd.playuser.page;

import com.pbtd.playuser.domain.ActivityFLHDJoin;
import com.pbtd.playuser.domain.UserActivitiesInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 首次登陆活动
 * 
 * @author JOJO
 * @param <T>
 * @param <T>
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class ActivityFLHDOutPut<T>{
	public int code;//是否可以参与活动  
	public String message;//是否可以参与活动  
	public T  data;//
	
	
	public ActivityFLHDOutPut(int a,String b) {
		this.code = 1;
		this.message = b;
		this.data = null;
	}
	public ActivityFLHDOutPut(UserActivitiesInfo active) {
		this.code = 2;
		this.message = "已参与过该活动";
		this.data = (T) new ActivityFLHDJoin(active);
	}
}
