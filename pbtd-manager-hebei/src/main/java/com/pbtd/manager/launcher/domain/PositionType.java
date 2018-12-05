package com.pbtd.manager.launcher.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionType  implements Serializable{
	private static final long serialVersionUID = 1L;
     private int id;
     private String name;
     public PositionType(int id){
    	 this.id=id;
     }
}
