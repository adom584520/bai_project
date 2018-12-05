package com.yh.push.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("all")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class PushUser implements Serializable{
	private String src_userID;

	private String des_userID;

	private String src_type;

	private String des_type;
	
	private String src_token;

	private String des_token;
	
	private String src_projID;

	private String des_projID;

	private Date create_time;

	private Date update_time;

	private String phoneName;
	
	private String tv_name;

	private String status;

	private String bz;

}