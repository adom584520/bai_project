package com.pbtd.manager.vod.tv.common.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter@Setter 
public class VodTvchannel  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id	;
	private int channelCode;
	private String channelName;
	private int levels	;
	private String name;
	private int sequence	;
	private int parentCode;
	private int status;
	private int navigationtype	;
	private int count;
	private Date create_time	;
	private String  create_user	;
	private Date update_time;
	private String update_user;
	private int type	;
	private int isshow_img	;
	private int isshow_right	;
	private int isshow_left	;
	public VodTvchannel(int id, int channelCode, String channelName, int levels, int sequence, int parentCode,
			int status, int navigationtype, int count, Date create_time, String create_user, Date update_time,
			String update_user,int type) {
		super();
		this.id = id;
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.levels = levels;
		this.sequence = sequence;
		this.parentCode = parentCode;
		this.status = status;
		this.navigationtype = navigationtype;
		this.count = count;
		this.create_time = create_time;
		this.create_user = create_user;
		this.update_time = update_time;
		this.update_user = update_user;
		this.type=type;
	}

	public VodTvchannel() {
		super();
	}

	
}
