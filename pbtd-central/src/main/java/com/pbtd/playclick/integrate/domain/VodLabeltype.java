package com.pbtd.playclick.integrate.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class VodLabeltype implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id	;
	private String name	;
	private  int sequence	;
	private String update_time	 ;
	private int num;

}
