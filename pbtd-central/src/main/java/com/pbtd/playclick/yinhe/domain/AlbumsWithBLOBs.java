package com.pbtd.playclick.yinhe.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class AlbumsWithBLOBs extends Albums implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String actors;

    private String albumDesc;

    private String cpList;

    private String currShowPlayOrder;

    private String leafTags;

    private String picUrl;

    private String superScripts;

}