package com.pbtd.playuser.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 返回消息实体
 * 
 * @author JOJO
 * @param <T>
 * @param <T>
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityZPHDOutPut<T>{
	public int code;//是否可以参与活动  
	public String message;//是否可以参与活动  
	public Integer  residcode;//当日活动剩余次数
	public String  playneedtime;//活动需要播放时长
//	public T  data;//
	
	public ActivityZPHDOutPut(int code,String message) {
		this.code = code;
		this.message = message;
	//	this.data =  data;
	}
	
	
	
}
