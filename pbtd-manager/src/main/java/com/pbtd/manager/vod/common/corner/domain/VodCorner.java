package com.pbtd.manager.vod.common.corner.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class VodCorner  implements Serializable{
	private static final long serialVersionUID = 1L;
private int 	id	;
private String 	name	;
private String 	imgurl	;
private Date 	create_time	;
private String 	create_user	;
private Date 	update_time	;
private String 	update_user	;
private int 	status	;
private String 	bz	;
private int 	pos	;
}
