package com.pbtd.playclick.yinhe.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class VodcpxxWithBLOBs extends Vodcpxx implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String currShowPlayOrder;

    private String pic;

    private String superScripts;

}