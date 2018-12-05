package com.pbtd.playclick.util;

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
	public boolean success = true;// 返回的状态
	public String message;// 返回的具体信息
}
