package com.pbtd.manager.vod.tv.mould.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VodTvModule  implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;
    private Long moduleid;
    private Integer masterplateid;
    private Integer sequence;
    private Long channel;
    private String describes;
    private String name;
    private Long linkchannel;
    private Long linklabel;
    private int linkstatus;
    private String modulepic;
    private int picstatus;
    private int flag;
    
   // channel,name,masterplateId,describes,sequence
    
	public VodTvModule(String channel,String name,String masterplateId,String describes,String sequence,String moduleid) {
		if(moduleid != null && !"".equals(moduleid)){
			this.moduleid =	Long.valueOf(moduleid);
		}
		this.channel =	Long.valueOf(channel);
		this.name = name;
		this.masterplateid =  Integer.valueOf(masterplateId);
		this.sequence =	Integer.valueOf(sequence);
		this.describes = describes;
	}

}