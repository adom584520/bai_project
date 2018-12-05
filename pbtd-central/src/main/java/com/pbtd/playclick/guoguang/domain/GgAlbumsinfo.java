package com.pbtd.playclick.guoguang.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class GgAlbumsinfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private int status;
	private int isdelete;
	private String pictureURL1;
	private String pictureURL2;
	private String pictureURL3;
	private String pictureURL4;
	private String writerDisplay;
	private String actorDisplay;
	private String programType;
	private String programType2;
	private String releaseYear;
	private int volumnCount;
	private int currentNum;
	private String description;
	private String originalCountry;
	private int duration;
	private String viewPoint;
	private String cpName;
    private String playURL;
	private String programTypeids;
	private String programType2ids;
	private int isStorage;
	private String storagetime;
	private String  updatetime;

}