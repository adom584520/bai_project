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
public class LiveChannel {
	
    private Integer channelid;

    private Integer sournece;

    private String oldchncode;

    private String chncode;

    private String chnname;

    private String playurl;

    private String packagecover;

    private Integer groupid;
    private Integer tagid;
    private Integer defaultnum;

    private Date createtime;

    private Date updatetime;

    private Integer epgstatus;
    
    private Integer videoid;

  
}