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
public class VodjsonPhoneAlbuminfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String centralcode;
	
	private String seriescode;

	private Integer id;
	
	private String seriesname;

	private Integer code;

	private Integer volumncount;

	private Integer currentnum;

	private String pinyin;

	private String pinyinsuoxie;

	private String cpcode;

	private String cpname;

	private String ordercode;

	private Integer iscollectfees;

	private String collectfeesbag;

	private String originalname;

	private String actor;

	private String actorname;

	private String writer;

	private String writername;

	private String orgairdate;

	private String price;

	private String description;

	private String contentprovider;

	private String keyword;

	private String tag;

	private String viewpoint;

	private String starlevel;

	private String rating;

	private String awards;

	private String originalcountry;

	private String language;

	private String releaseyear;

	private String duration;

	private String channel;

	private String channelname;

	private String channel2;

	private String channelname2;

	private String label;

	private String labelname;

	private String label2;

	private String labelname2;

	private String enname;

	private String vname;

	private String definition;

	private String copyrightproperty;

	private String crbegindate;

	private String crenddate;

	private String copyright;

	private String cpcontentid;

	private Float score;

	private Integer sequence;

	private String pictureurl1;

	private String pictureurl2;

	private String pictureurl3;

	private String pictureurl4;

	private Integer isvip;

	private String corner;

	private Integer status;

	private String cpseriescode;

	private String pictureurl5;

	private Date createTime;

	private String createUser;

	private Date updateTime;

	private String updateUser;

	private String cpcodelist;
	
	private String pay_type;
	private  int exclusive;
	
	private Integer offset;
}