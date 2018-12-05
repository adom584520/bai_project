package com.pbtd.vodinterface.web.domain;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class VodTvModule {
    private Integer id;
    private Long moduleid;
    private Integer masterplateid;
    private Integer sequence;
    private Long channel;
    private String describes;
    private String name;
    private Long linkchannel;
    private Long linklabel;
    private int linkstatus;
    private String modulepic;
    private int picstatus;
   
}