package com.pbtd.manager.user.domain;

import java.beans.ConstructorProperties;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysCountUser {
	private Integer id;

	private Date createtime;
	private Integer usercount;
	private Integer playcount;
	private Integer playusercount;
	private Integer activecount;
	private Integer activeusercount;
	private Integer addusercount;
	private Integer addplaycount;
	private Integer addplayusercount;
	private Integer addactivecount;
	private Integer addactiveusercount	;
	private Float playtime;
	private Float activedegree;
	private Float playactivedegree;
	private Float playtimetoone;

	private Integer fourgplayusercount;
	private Integer wifiplayusercount;
	private Float fourgplaytime;
	private Float wifiplaytime;



	@ConstructorProperties({"id", "createtime", "usercount", "playcount", "playusercount", "activecount", "activeusercount", "addusercount", "addplaycount", "addplayusercount", "addactivecount", "addactiveusercount", "playtime", "activedegree", "playactivedegree", "playtimetoone"})
	public SysCountUser(Integer id, Date createtime, Integer usercount, Integer playcount, Integer playusercount, Integer activecount, Integer activeusercount, Integer addusercount, Integer addplaycount, Integer addplayusercount, Integer addactivecount, Integer addactiveusercount, Float playtime, Float activedegree, Float playactivedegree, Float playtimetoone) 
	{ this.id = id; 
	this.createtime = createtime; 
	this.usercount = usercount; 
	this.playcount = playcount; 
	this.playusercount = playusercount; 
	this.activecount = activecount; 
	this.activeusercount = activeusercount; 
	this.addusercount = addusercount; 
	this.addplaycount = addplaycount; 
	this.addplayusercount = addplayusercount; 
	this.addactivecount = addactivecount; 
	this.addactiveusercount = addactiveusercount; 
	this.playtime = playtime; 
	this.activedegree = activedegree; 
	this.playactivedegree = playactivedegree; 
	this.playtimetoone = playtimetoone; 
	}

}