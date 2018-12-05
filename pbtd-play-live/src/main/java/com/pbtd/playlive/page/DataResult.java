package com.pbtd.playlive.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResult<T> {
	private int total;// 响应码
	private T rows;// 数据集合

	public DataResult() {
	}

	public DataResult(int total,T rows) {
		this.total = total;
		this.rows = rows;
	}
}
