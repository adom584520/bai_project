package com.pbtd.launcher.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionType {
     private int id;
     private String name;
     public PositionType(int id){
    	 this.id=id;
     }
}
