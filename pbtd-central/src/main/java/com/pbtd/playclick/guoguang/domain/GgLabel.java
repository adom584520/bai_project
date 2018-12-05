package com.pbtd.playclick.guoguang.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class GgLabel implements Serializable{
	private static final long serialVersionUID = 1L;
	private int tagId;
	private String tagName;
	private int typeId;
	private int chnId;
	private String bz;
	private String status;
	private String typeName;

}
