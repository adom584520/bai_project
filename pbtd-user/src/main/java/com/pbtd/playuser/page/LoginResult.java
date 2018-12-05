package com.pbtd.playuser.page;

import com.pbtd.playuser.util.JsonMessage;

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
public class LoginResult extends JsonMessage{
	
	public String userId;//用户Id
	public String token;//用户token
	public LoginResult(int code,String message,String userId,String token) {
		this.code = code;
		this.message = message;
		this.userId = userId;
		this.token = token;
	}
}
