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
public class UserSearchInfo {
    private Integer id;

    private String mobilenum;

    private String sousuowenzi;
    private Integer count;

    private Date createtime;
}