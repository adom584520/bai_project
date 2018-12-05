package com.pbtd.manager.vod.system.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecommandPic {
	private Integer id;
	private Integer type; 
	private String channel_id;
	private String name;  
	private String target_code; 
	private String imageUrl;
	private Boolean status;
	private Integer sequence;
	private String weburl;
	private String playurl;
	private String viewPoint;
	private String update_user;
	private Date update_time;
	public RecommandPic(int id){
		this.id=id;		
	}
}
