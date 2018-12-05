package com.pbtd.manager.vod.buss.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Vodbussinfo {
	private int bussId	;
	private int groupId	;
	private  String  name;
	private  String address;
	private  String bussUser;
	private  String bussPhone;
	private  Date create_time	;
	private  String create_user;
	private  Date update_time	;
	private  String update_user;
	private  String Column_11;
	private  String Column_12;
	private  String Column_13;


}
