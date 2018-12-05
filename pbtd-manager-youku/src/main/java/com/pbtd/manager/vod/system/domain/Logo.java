package com.pbtd.manager.vod.system.domain;

import java.util.Date;

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
public class Logo {
    private Integer id;
    private String name;
    private String logo;
    private String instruction;
    private String create_user;
    private Date create_time;
    private String modify_user;
    private Date modify_time;
    public Logo(int id){
    	this.id=id;
    }
}
