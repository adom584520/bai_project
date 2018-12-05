package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ActivitiesBaseInfo {
    private Integer id;
    private String activecode;//活动code
    private String activename;//活动名称
    private String activedescribe;//活动描述
    private Date starttime;	//活动开始时间
    private Date endtime;//活动结束时间
    private String activepicture;//活动展示图片
    private Integer activefloat;//活动是否开启浮标 0 不开启；1 开启
    private Integer activetype;//活动类型   0：主活动；1，小活动；
    private Date updatetime;
    private Date createtime;
}