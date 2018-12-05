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
public class LiveSysParam {
    private Integer paramid;

    private String paramname;

    private String paramcnname;

    private String paramvalue;

    private String paramvalueadd;

    private Integer paramstatus;

    private Date createtime;

    private Date updatetime;

}