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
public class LiveCibnListEpg {
	
	
	private Integer proepgId;
	private String chnCode;
	private String playUrl;
	private long startTime;
	private long endTime;
	private long duration;
	private String epgName;
	private String showName;
	private String weekDay;
	private String startDate;
  
}
