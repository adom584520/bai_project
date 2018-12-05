package com.pbtd.manager.inject.page.domain;


import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Vodalbuminfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String seriesCode	;
	private String seriesName	;
	private Integer id	;
	private Integer code	;
	private Integer volumncount	;
	private Integer currentnum	;
	private String pinyin	;
	private String pinyinsuoxie	;
	private String cpCode	;
	private String cpName	;
	private String cpCodelist	;
	private String orderCode	;
	private Integer isCollectfees	;
	private String Collectfeesbag	;
	private String originalName	;
	private String actor	;
	private String actorName	;
	private String writer	;
	private String writerName	;
	private String orgairDate	;
	private String price	;
	private String description	;
	private String contentProvider	;
	private String keyWord	;
	private String tag	;
	private String viewPoint	;
	private String starLevel	;
	private String rating	;
	private String awards	;
	private String originalCountry	;
	private String language	;
	private String releaseYear	;
	private String duration	;
	private String Channel	;
	private String ChannelName	;
	private String Channel2	;
	private String ChannelName2	;
	private String Label	;
	private String LabelName	;
	private String Label2	;
	private String LabelName2	;
	private String enName	;
	private String vName	;
	private String definition	;
	private String copyRightProperty	;
	private String crBeginDate;	;
	private String crEndDate;	;
	private String copyright	;
	private String cpContentID	;
	private float score	;
	private Integer Sequence	;
	private String pictureurl1	;
	private String pictureurl2	;
	private String pictureurl3	;
	private String pictureurl4	;
	private Integer isvip	;
	private String corner	;
	private Integer status	;
	private String cpseriescode	;
	private String pictureurl5	;
	private Date create_time	;
	private String create_user	;
	private Date update_time	;
	private String update_user	;
	public Vodalbuminfo(int id) {
		super();
		this.id = id;
	}


}
