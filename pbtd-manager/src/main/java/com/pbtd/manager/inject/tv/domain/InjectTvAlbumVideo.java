package com.pbtd.manager.inject.tv.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class InjectTvAlbumVideo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id; 
	private Integer seriesCode;
	private Integer drama;
	//private String channel;
	private Integer zxInjectState;
	private Integer zxPriority;
	private Integer hwInjectState;
	private Integer hwPriority;
	
}
