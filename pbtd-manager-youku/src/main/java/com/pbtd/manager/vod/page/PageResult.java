package com.pbtd.manager.vod.page;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResult {
	private Long total;// 总条数
	private List<?> rows;// 数据集合

	public PageResult() {
		
	}

	public PageResult(Long total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public PageResult(int total, List<?> rows) {
		this.total = Long.parseLong(total+"");
		this.rows = rows;
	}
}
