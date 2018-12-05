package com.pbtd.playclick.integrate.domain;

import java.io.Serializable;
import java.util.Date;

import com.pbtd.playclick.page.QueryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Vodactors extends QueryObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String name;
	private String bz;
	private String status;
	private String actor;
	private String director;
	private String imgportrait ;
	private String backgroundimg;
	private Date updatetime;
	private String pinyin ;
	private String pinyinsuoxie;
	
}
