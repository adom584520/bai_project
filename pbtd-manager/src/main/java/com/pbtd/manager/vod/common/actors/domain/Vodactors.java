package com.pbtd.manager.vod.common.actors.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Vodactors   implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String name;
	private String bz;
	private String status;
	private String actor;
	private String director;
	private String pinyin;
	private String pinyinsuoxie;
	private String imgportrait ;
	private String backgroundimg;
	private Date updatetime;
	private String birthDay	;
	private String birthPlace;
	private String description;

	
}
