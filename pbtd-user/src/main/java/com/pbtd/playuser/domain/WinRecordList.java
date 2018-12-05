package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class WinRecordList {
    private Integer id;

    private String userid;//用户id

    private String usermobile;//用户手机号

    private Date createtime;

    private Date updatetime;

    private Integer prizecode;//奖品id

    private String activecode;//活动名称

    private Integer activetype;//中奖类型 0实物，1电子产品

    private String useraddress;//地址

    private String prizename;//奖品名称

 }