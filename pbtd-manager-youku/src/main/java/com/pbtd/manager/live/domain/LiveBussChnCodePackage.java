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
public class LiveBussChnCodePackage {
    private Integer id;

    private Integer bussid;
    
    private String bussname;

    private String chncode;
    
    private Integer channelid;

    private Integer chncodenum;

    private Date createtime;

    private Date updatetime;

    private Integer chncodestatus;

}