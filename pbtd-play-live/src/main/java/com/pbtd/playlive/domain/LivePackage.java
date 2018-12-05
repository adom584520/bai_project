package com.pbtd.playlive.domain;

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
public class LivePackage {
    private Integer packageid;

    private String packagename;

    private Long starttime;

    private Long endtime;

    private String chncode;

    private Integer tagid;

    private Integer packageorder;

    private String packageposter;

    private Integer packagestats;

    private String packagecode;

    private String packagecover;

    private Date createtime;

    private Date updatetime;


}