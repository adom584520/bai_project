package com.pbtd.playclick.yinhe.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class GitvvideoWithBLOBs extends Gitvvideo implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String indexM3u8;

    private String m3u8;

    private String superScripts;

    private String videoUrl;

}