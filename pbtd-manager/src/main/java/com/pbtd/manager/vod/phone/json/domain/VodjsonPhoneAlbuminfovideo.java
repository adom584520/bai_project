package com.pbtd.manager.vod.phone.json.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VodjsonPhoneAlbuminfovideo  implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long id;
    
    private Long dramacode;

    private String seriescode;

    private Integer drama;

    private String dramaname;

    private String dramaviewpoint;

    private String resolution;

    private Integer definition;

    private String sourcetype;

    private String type;

    private String duration;

    private Integer priority;

    private Date injectiontime;

    private String pic;

    private String title;

    private String description;

    private String zxversionlist;

    private String fileurl;

    private String movieurl;

    private String zxversion;
    
    private String centralcode;
   
    private String version;

    private String hwversionlist;

    private String hwversion;

    private String hwmovieurl;

    private String hwfileurl;

    private Integer isshow;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    
    private Integer dramasequence;

    private Integer isCollectfees; 
    
    private String cpvideoId;
    private Integer   isPositive;
    private Integer   offset;

}