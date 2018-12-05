package com.pbtd.playuser.page;

import com.pbtd.playuser.domain.ActivityOutPut;
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
public class ActivityResult<T> extends JsonMessage{
	private T data;// 数据集合
	
	public ActivityResult(T data) {
		this.code = 1;
		this.message = "获取成功";
		this.data =  data;
	}
	public ActivityResult(int code,String message,int recode) {
		this.code = code;
		this.message = message;
		this.data = (T) new ActivityOutPut(recode);
	}
	public ActivityResult(int code,String message,int recode,int activitycode) {
		this.code = code;
		this.message = message;
		this.data = (T) new ActivityOutPut(activitycode,recode);
	}
	public ActivityResult() {
	}
}
