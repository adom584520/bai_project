package com.pbtd.playlive.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResult<T> {
	private Integer code = 1;// 响应码
	private String message = "响应成功";// 响应信息
	private Object data = "{}";// 数据集合

	public PageResult() {
	}

	public PageResult(Object data) {
		this.data = data;
	}
	
	public PageResult(int code,String message) {
		this.code = code;
		this.message = message;
	}
}
