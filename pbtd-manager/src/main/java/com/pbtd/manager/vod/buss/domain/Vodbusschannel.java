package com.pbtd.manager.vod.buss.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Vodbusschannel  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int bussId	;
	private int groupId	;
	private int  channelCode	;
	private String  channelname	;
}
