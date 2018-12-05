package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ActivitiesBaseInfo {
    private Integer id;

    private String activename;
    
    private String activedescribe;

    private Date starttime;	

    private Date endtime;

    private String activepicture;

    private Date updatetime;

    private Date createtime;
}