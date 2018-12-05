package com.pbtd.playuser.page;

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
public class LiveCollectResult<T> extends JsonMessage{
	private T data;// 数据集合
	
	public LiveCollectResult(T data) {
		this.code = 1;
		this.message = "获取成功";
		this.data = data;
	}
}
