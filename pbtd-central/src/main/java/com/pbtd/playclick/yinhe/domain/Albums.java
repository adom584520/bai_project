package com.pbtd.playclick.yinhe.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Albums implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String albumId;

    private String albumAttributes;

    private String albumName;

    private String chnId;

    private String chnName;

    private String cpId;

    private String duration;

    private String focus;

    private String is3d;

    private String isPurchase;

    private String isPurchaseOwn;

    private String isSeries;

    private String maxSet;

    private String phase;

    private String pid;

    private String playCnt;

    private String score;

    private String scoreLabel;

    private String season;

    private String sets;

    private String showDate;

    private String timestamp;

    private String status;

    private String bz;
    
    private String tags;
    private String tagsids;
    private String currentCount;
    private String actorname;
    private String directorname;
    private String actorids;
    private String directorids;
    private String originalCountry;
    private String originaids;
    private int isStorage;
    private String storagetime;
    private String  updatetime;

}