package com.pbtd.manager.vod.tv.search.domain;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 搜索全部专辑分页列表
 * @author 程先生
 *
 */
@SuppressWarnings("all")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class SearchResult implements Serializable {
			private static final long serialVersionUID = 1L;
	private List itemList;
	private String queryString;
	private long recordCount;
	private long pageCount;
	private long page;
	
	
}
