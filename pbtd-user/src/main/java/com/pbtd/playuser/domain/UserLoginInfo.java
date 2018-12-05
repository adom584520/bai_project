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
public class UserLoginInfo {
    private Integer id;

    private String userid;

    private String userdevice;

    private String userdevicename;

    private String tokencode;

    private Integer loginstat;

    private Date createtime;

    private Date updatetime;

}