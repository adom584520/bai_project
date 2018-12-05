package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class UserBaseInfo {
    private String  userid;

    private String usermobile;

    private String username;

    private String usernickname;

    private String usericon;

    private Integer vipstat;

    private String viptype;

    private Date vipcreatetime;

    private Date vipendtime;

    private Integer sharenum;

    private Integer commendnun;

    private String code;

    private Date createtime;

    private Date updatetime;

}