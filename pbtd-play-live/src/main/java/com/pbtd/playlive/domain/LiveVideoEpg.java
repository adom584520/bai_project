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
public class LiveVideoEpg {
	
    private Integer videoid;

    private String title;
    private String chncode;
    public long startTime;  //开始时间
    public long endTime;   //结束时间
    private String packagecode;

    
    public String chnName;   //频道名称
    public String playUrl;   //播放地址(暂时不用)
    public String playUrl2;   //播放地址(暂时不用)

}