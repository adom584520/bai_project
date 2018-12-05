package com.pbtd.launcher.util;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
/**
 * 后台接口统一返回类型
 * @author JOJO
 *
 * @param <T>
 */
@Setter@Getter
public class ResultBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean success = true;
	private String message;
	private T data;
}
