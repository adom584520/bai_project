package com.pbtd.manager.vod.common.corner.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class VodCollectfeesbag {
	private int id	;
	private int code	;
	private String name	;
	private String starttime	;
	private String endtime	;
	private int  type	;
	private float discountrate	;
	private float price	;
	private int  status	;

}
