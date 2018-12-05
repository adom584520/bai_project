package com.pbtd.manager.vod.system.domain;



import java.io.Serializable;
import java.sql.Date;

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
public class RecommandPic  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer type; 
	private String channel_id;
	private String name;  
	private String target_code; 
	private String imageUrl;
	private int status;
	private Integer sequence;
	private String playurl;
	private String weburl;
	private String update_user;
	private Date update_time;
	private String viewPoint;
	public RecommandPic(int id){
		this.id=id;		
	}
}
