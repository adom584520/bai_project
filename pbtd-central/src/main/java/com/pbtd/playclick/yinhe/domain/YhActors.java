package com.pbtd.playclick.yinhe.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class YhActors implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String name;
	
	private String bz;
	
	private String status;
	
	private String actor;
	
	private String director;
}
