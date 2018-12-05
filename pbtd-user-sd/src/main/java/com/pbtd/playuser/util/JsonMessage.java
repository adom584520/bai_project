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
	public int code;//返回状态  
	public String message;//返回的具体信息
	public JsonMessage() {
	}
	public JsonMessage(int code,String message) {
		this.code = code;
		this.message = message;
	}
}
