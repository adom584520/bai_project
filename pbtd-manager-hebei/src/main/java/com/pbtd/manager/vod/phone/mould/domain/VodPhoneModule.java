package com.pbtd.manager.vod.phone.mould.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VodPhoneModule implements Serializable {
	private static final long serialVersionUID = 1L;
	 private Long moduleid;
	    private Integer masterplateid;
	    private Integer sequence;
	    private Long channel;
	    private String describes;
	    private String name;
	    private Long linkchannel;
	    private Integer linktype;
	    private Long linklabel;
	    private String linkstatus;
	    private String modulepic;
	    private Integer picstatus;
	    private String textrecommendpic;
	    private Integer textpicstatus;
	    private String picture;
	    private Integer picturestatus;
	    private String moduleviewpoint;
	    private Integer viewpointstatus;
	    private Integer modulestatus;
	    private Integer isshowleft;
	    private Integer isshowright;
	    private Integer namestatus;
	    private Integer modulelinktype;
	    private Long modulelinkchannel;
	    private String modulelinkurl;
	    private Long modulelinkspecial;
	    private Integer flag;
    
   // channel,name,masterplateId,describes,sequence
    
	public VodPhoneModule(String channel,String name,String masterplateId,String describes,String moduleid) {
		if(moduleid != null && !"".equals(moduleid)){
			this.moduleid =	Long.valueOf(moduleid);
		}
		this.channel =	Long.valueOf(channel.substring(channel.indexOf("_")+1,channel.length()));
		this.name = name;
		this.masterplateid =  Integer.valueOf(masterplateId);
		//this.sequence =	Integer.valueOf(sequence);
		this.describes = describes;
	}

}