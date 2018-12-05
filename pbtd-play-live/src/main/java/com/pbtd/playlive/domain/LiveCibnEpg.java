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
public class LiveCibnEpg {
    private Integer epgid;

    private String chncode;

    private String playurl;

    private Long starttime;

    private Long endtime;

    private Long duration;

    private String epgname;

    private String showname;

    private String weekday;

    private String startdate;
    
	public LiveCibnEpg(String chncode,Long start,Long endtime) {
		this.chncode = chncode;
		this.starttime = start;
		this.endtime = endtime;
	}

}