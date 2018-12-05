package com.pbtd.playlive.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class LiveChannelSource {
    private Integer id;

    private Integer cpepgid;

    private Integer packageid;

    private String title;

    private Byte issave;

    private Long starttime;

    private Long endtime;

    private String tag;

    private String chncode;

    private String chnname;

    private Byte ismodified;

    private Integer playorder;

    private String playurl;

    private String backplayurl;

    private String epgposter;

    private String backposter;

    private Byte epgstatus;

    private Long createtime;

    private Long updatetime;

    private String packagename;

    private String packagecode;

    private String packagecover;

    private String chnimage;


}