package com.pbtd.playclick.guoguang.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class GgAlbums implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private int status;
	private int isdelete;
	private int updatetime;

}