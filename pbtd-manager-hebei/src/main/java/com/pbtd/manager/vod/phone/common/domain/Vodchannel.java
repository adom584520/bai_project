package com.pbtd.manager.vod.phone.common.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vodchannel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int channelCode;
	private String channelName;
	private int levels;
	private String name;
	private int sequence;
	private int parentCode;
	private int status;
	private int navigationtype;
	private int count;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private int type;
	private String img	;
	private int isshow_img	;
	private int isshow_right	;
	private int isshow_left	;
	private String labels;
	private String anotherName;
	private int labelType;

}
