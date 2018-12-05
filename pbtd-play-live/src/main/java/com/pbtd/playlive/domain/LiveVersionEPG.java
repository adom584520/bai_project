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
public class LiveVersionEPG {


    private Integer isPlayContinue;

    private Integer showType;

    private Integer isNumChange;

    private Integer isShowLiveList;

    private Integer liveNavStyle;
    
    private String timestamp;
	
}