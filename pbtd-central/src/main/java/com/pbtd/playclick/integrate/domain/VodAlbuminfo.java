package com.pbtd.playclick.integrate.domain;

import java.io.Serializable;
import java.util.Date;

import com.pbtd.playclick.page.QueryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VodAlbuminfo  extends QueryObject implements Serializable  {
    private static final long serialVersionUID = 1L;
    //唯一标识
    private  String  centralcode;
	 //节目集名称
    private String seriesname;
    //节目集code
    private String seriescode;
    //总集数
    private Integer volumncount;
    //更新至集数
    private Integer currentnum;
    //拼音
    private String pinyin;
    //拼音缩写
    private String pinyinsuoxie;
    //CP编码
    private String cpcode;
    //CP名称
    private String cpname;
    //订购编号
    private String ordercode;
    //是否收费
    private Integer iscollectfees;
    //原名
    private String originalname;
    //导演
    private String actor;
    //主演 
    private String writer;
    //导演名称
    private String actorname;
    //主演名称
    private String writername;
    //首映日期
    private String orgairdate;
    //含税定价
    private String price;
    //描述信息
    private String description;
    //节目提供商
    private String contentprovider;
    //关键字
    private String keyword;
    //关联标签
    private String tag;
    //看点
    private String viewpoint;
    // 
    private String starlevel;
    // 
    private String rating;
    // 
    private String awards;
    // 
    private String originalcountry;
    // 
    private String language;
    // 
    private String releaseyear;
    // 
    private String duration;
    //频道
    private String channel;
    //频道名称
    private String channelname;
    //标签
    private String label;
    //标签名称
    private String labelname;
    // 
    private String enname;
    // 
    private String vname;
    //清析度
    private String definition;
    // 
    private Integer copyrightproperty;
    // 
    private String crbegindate;
    // 
    private String crenddate;
    // 
    private String copyright;
    // 
    private String cpcontentid;
    // 
    private Double score;
    // 
    private String pictureurl1;
    // 
    private String pictureurl2;
    // 
    private String pictureurl3;
    // 
    private String pictureurl4;
    // 
    private String pictureurl5;
    // 
    private Date updatetime;
    private Date issuetime;
    private String issue;
    private String cpCodelist;
    private String pay_type;
    private int exclusive;
    private int offset;
    //用于查询字段  日期
    
    private Date updatetime1;
    private Date  updatetime2;
    private int channelstatus;
    
}
