package com.pbtd.launcher.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private int id;
    private int position;
    private int position_type;
    private String position_name;
    private String name;
    public Position(int id){
    	this.id=id;
    }
}
