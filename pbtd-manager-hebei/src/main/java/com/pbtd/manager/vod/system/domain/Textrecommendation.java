package com.pbtd.manager.vod.system.domain;

import java.io.Serializable;
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
public class Textrecommendation  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private long channelCode;
	private int seriesCode;
	private String seriesName;
	private int status;
	private String textPic;
	private String text;
	private String playurl;
	private String weburl;
	private int type;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private String picStatus;
	public Textrecommendation(int id) {
		super();
		this.id = id;
	}
	
	

}
