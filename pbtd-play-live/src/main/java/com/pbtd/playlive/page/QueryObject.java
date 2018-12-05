package com.pbtd.playlive.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryObject {
	private Integer page = 1;// 初始索引
	private Integer rows = 10;// 当页显示的记录数

	public Integer getStart() {
		return (page-1) * rows;
	}
	public Integer getPageSize() {
		return rows;
	}
}
