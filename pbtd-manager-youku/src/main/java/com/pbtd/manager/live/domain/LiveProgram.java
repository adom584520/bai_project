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
public class LiveProgram {
    private Integer programid;
    
    private Integer source;

    private Long starttime;

    private Long endtime;

    private String chncode;

    private String programname;

    private String realtime;

    private Date createtime;


}