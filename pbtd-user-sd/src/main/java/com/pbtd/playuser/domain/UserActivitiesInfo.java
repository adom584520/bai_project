package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserActivitiesInfo {
    private Integer id;

    private String userid;

    private Integer activenum;	//每日参与活动次数

    private Integer isnotwin;	//是否中奖	

    private String activename;	//活动name

    private Date createtime;

    private Date updatetime;

}