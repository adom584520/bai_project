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
public class LiveChannelTvEpg {
    private Long endTime;
    private Long startTime;
    private Long duration;
    private String chnCode;
    private String chnName;
    private String chnImage;
    private String tag;
    private String title;
    private int playOrder;
    private String playUrl;
    private String playUrl2;

}