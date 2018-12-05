package com.pbtd.manager.vod.phone.hotsearch.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class VodHotSearchInfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String seriesCode;
	private Integer status;
	private Integer sequence;
	
}
