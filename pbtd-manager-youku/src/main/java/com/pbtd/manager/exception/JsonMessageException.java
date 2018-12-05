package com.pbtd.manager.exception;

/**
 * 后台操作返回异常类
 * @author JOJO
 *
 */
public class JsonMessageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonMessageException() {
	}

	public JsonMessageException(String message) {
		super(message);
	}

}
