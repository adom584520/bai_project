package com.pbtd.playclick.yinhe.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class YhLabel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tagId;
	
	private String tagName;
	
	private String typeId;
	
	private String typeName;
	
	private String chnId;
	
	private String bz;
	
	private String status;

}
