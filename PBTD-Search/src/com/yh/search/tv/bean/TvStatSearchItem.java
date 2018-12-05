package com.yh.search.tv.bean;

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
public class TvStatSearchItem implements Serializable{

	private String Channel;    //频道Id
	private String ChannelName;    //频道名称
	private Long total;    //记录数
	
}
