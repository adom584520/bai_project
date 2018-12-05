package com.pbtd.vodinterface.page;

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
}
