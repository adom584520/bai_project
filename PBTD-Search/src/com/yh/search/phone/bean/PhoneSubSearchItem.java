package com.yh.search.phone.bean;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * TV端搜索实体
 * @author 程先生
 *
 */
@SuppressWarnings("all")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class PhoneSubSearchItem implements Serializable{

	private Long epis;    //是否多专辑
	private String seriesCode;    //专辑Id
	private String seriesName;    //专辑标题
	private String actorName;    //演员 
	private String writerName;    //导演  
	private String orgairDate;    //专辑年份
	private String Channel;    //分类ID
	private String ChannelName;    //分类名称
	private String pictureurl1;    //T专辑图片竖
	private String pictureurl2;    //T专辑图片橫
	private Long currentnum;    //更新集数
	private String originalCountry;    //国家
	private String description;    //简介
	private String cornerimg;     //角标
	private Long status;    //专辑状态
	private String isvip;  //是否付费
	private String score;  //评分
}
