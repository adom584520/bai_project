package com.pbtd.manager.launcher.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Position implements Serializable{
	private static final long serialVersionUID = 1L;
    private int id;
    private int position;
    private int position_type;
    private String position_name;
    private String name;
    public Position(int id){
    	this.id=id;
    }
}
