package com.pbtd.playclick.guoguang.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class GgAlbumsinfovideo implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String volumn;
	private String name;
	private String playURL;
	private String fileURL;
	private String dramacode;
}