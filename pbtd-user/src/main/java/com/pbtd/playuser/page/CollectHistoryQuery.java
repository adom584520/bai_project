package com.pbtd.playuser.page;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class CollectHistoryQuery {
	private Integer status;
	private String userId; 
	private String mac; 
	private Integer row = 30;
	private Integer pagination = 0;
}
