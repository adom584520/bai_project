package com.pbtd.manager.vod.common.mould.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class vodMasterplate implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    private String masterplatename;

    private Integer masterplatenum;

    private String masterplatephoto;
    
    private  String describes;
}