package com.pbtd.manager.util;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 后台接口统一返回类型
 * @author JOJO
 *
 * @param <T>
 */
@Setter@Getter
@ToString
public class ResultBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean success = true;
	private String message;
	private T data;
}
