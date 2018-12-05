package com.pbtd.manager.live.domain;

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
public class LiveGroup {
    private Integer groupid;

    private String groupname;

    private Integer grouporder;

    private Date createtime;

    private Date updatetime;

    private Integer isdelete;


}