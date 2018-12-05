package com.pbtd.playuser.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class LiveCibnNextEpg {
	
	private int  videoId;
	private String  chnCode;
    private String  epgName;
    private long startTime;
    private long endTime;
    private String typeName;
    private String  nextEpgName;
    private long nextStartTime;
    private long nextEndTime;

  
}