package com.pbtd.manager.vod.phone.search.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 

/**
 * 频道和频道id实体
 * @author 程先生
 *
 */
@SuppressWarnings("all")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class StatSearchItem implements Serializable{
	private static final long serialVersionUID = 1L;
	private String sup_id;    //频道Id
	private String categoryName;    //频道名称
	private Long total;    //记录数
	
}
