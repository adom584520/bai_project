package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserLiveCollectInfo {
    private Integer id;

    private String userid;

    private String chncode;

    private String chnname;
    
    private String playurl;

    private Date createtime;

    private Date updatetime;

    private Integer groupid;

    private String packagecover;

    private Integer videoid;

}