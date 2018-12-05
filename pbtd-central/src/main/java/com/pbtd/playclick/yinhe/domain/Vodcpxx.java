package com.pbtd.playclick.yinhe.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Vodcpxx implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String albumId;

    private String albumName;

    private String chnId;

    private String cpId;

    private String is3d;

    private String score;

    private String sets;

    private String bz;

    private String status;

}