package com.pbtd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class VideoVersion {

	private Integer code;
	private String codeName;
	private String remark;
	private String bits; 

}
