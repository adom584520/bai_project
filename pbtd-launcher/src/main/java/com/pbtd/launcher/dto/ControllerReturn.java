package com.pbtd.launcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 前端接口统一返回类型
 * 
 * @author JOJO
 *
 * @param <T>
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ControllerReturn<T> {
	private Integer code = 0;
	private String message;
	private T data;
}
