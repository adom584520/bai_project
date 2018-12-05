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
public class LiveProgram {
    private Integer programid;
    
    private Integer source;

    private Long starttime;

    private Long endtime;

    private String chncode;

    private String programname;

    private String realtime;

    private Date createtime;
    
    
	public LiveProgram(String chncode,Long starttime) {
		this.chncode = chncode;
		this.starttime = starttime;
	}
	public LiveProgram(String chncode,Long start,Long endtime) {
		this.chncode = chncode;
		this.starttime = start;
		this.endtime = endtime;
	}


}