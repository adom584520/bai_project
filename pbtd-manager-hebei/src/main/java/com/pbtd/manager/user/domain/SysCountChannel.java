package com.pbtd.manager.user.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class SysCountChannel {
    private Integer id;

    private Date createtime;

    private String channel;

    private String channelname;

    private Float playtime;

    private Integer playcount;

    private Integer playusercount;

}