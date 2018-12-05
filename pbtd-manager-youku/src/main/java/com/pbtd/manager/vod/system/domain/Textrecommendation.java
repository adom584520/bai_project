package com.pbtd.manager.vod.system.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Textrecommendation {
	private int id;
	private int channelCode;
	private int seriesCode;
	private String seriesName;
	private int status;
	private String text;
	private String playurl;
	private String weburl;
	private int type;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	public Textrecommendation(int id) {
		super();
		this.id = id;
	}
	
	

}
