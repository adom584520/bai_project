package com.yh.search.tv.bean;

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
public class TvSearchResult implements Serializable {

	private List itemList;
	private List channelList;
	private String queryString;
	private long recordCount;
	private long pageCount;
	private long page;
	
	
}
