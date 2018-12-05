package com.pbtd.manager.vod.phone.album.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Vodalbuminfovideo  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id	;
	private String seriesCode	;
	private Integer drama	;
	private String dramaname	;
	private String dramaviewPoint	;
	private String Resolution	;
	private Integer Definition	;
	private String SourceType	;
	private String Type	;
	private Date 	updatetime;
	private String duration	;
	private Integer priority	;
	private Date  Injectiontime	;
	private String pic	;
	private String title	;
	private String description	;
	private String zxversionlist;
	private String zxversion	;
	private String zxmovieUrl	;
	private String zxfileurl;
	private String hwversionlist;
	private String hwversion	;
	private String hwmovieUrl	;
	private String hwfileurl	;
	private Integer isShow	;
	private Date create_time	;
	private String create_user	;
	private Date  update_time	;
	private String update_user	;
	private String hwdispatchurl;
	private String zxdispatchurl;
	private int dramasequence;

}
