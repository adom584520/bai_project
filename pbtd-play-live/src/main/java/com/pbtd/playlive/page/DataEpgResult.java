package com.pbtd.playlive.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataEpgResult<T> {
	private String Datetime;// 日期
	private int count;// 条数
	private T list;// 数据集合

	public DataEpgResult() {
	}

	public DataEpgResult(String time,int total,T rows) {
		this.Datetime = time;
		this.count = total;
		this.list = rows;
	}
}
