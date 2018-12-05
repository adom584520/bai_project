package com.pbtd.manager.vod.tv.common.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class VodTvlabel {
	private int id	;
	private String name;
	private int channelCode;
	private int  level	;
	private int sequence	;
	private int status	;
	private String bz;
	private Date create_time	;
	private String create_user;
	private Date update_time	;
	private String update_user;
    private String channelName;
}
