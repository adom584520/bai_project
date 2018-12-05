package com.pbtd.manager.vod.system.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BottomNavigation  implements Serializable{
	private static final long serialVersionUID = 1L;
     private Integer id;
     private Integer type;
     private String name;
     private String imgNor;
     private String imgSelect;
     public BottomNavigation(int id){
    	this.id=id; 
     }
}
