package com.pbtd.manager.live.domain;

import java.io.Serializable;
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
public class LiveVideo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer videoid;

    private String title;

    private String chncode;

    private Long starttime;

    private Long endtime;

    private Integer tagid;

    private Integer packageid;

    private String packagecode;

    private Date createtime;

    private Date updatetime;

}