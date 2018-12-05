package com.pbtd.playuser.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回消息实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class JsonMessage {
	
	public int code = 1;// 响应码
	public String message = "响应成功";// 响应信息
	public Object data = "{}";// 数据集合

	public JsonMessage() {
	}

	public JsonMessage(Object data) {
		this.data = data;
	}
	
	public JsonMessage(int code,String message) {
		this.code = code;
		this.message = message;
	}
	public JsonMessage(int code,String message,Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
/*//	public int code;//返回状态  
	public String message;//返回的具体信息
	
	
	
	public JsonMessage() {
	}
	public JsonMessage(int code,String message) {
		this.code = code;
		this.message = message;
	}*/
}
