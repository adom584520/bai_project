package com.pbtd.manager.live.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class LivePackage  implements Serializable{
	private static final long serialVersionUID = 1L;
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

    private Long createtime;

    private Long updatetime;


}