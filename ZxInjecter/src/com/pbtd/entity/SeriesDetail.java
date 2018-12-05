package com.pbtd.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SeriesDetail {
	
	private int id;  //自增长
	private int subWebId;//分运营平台传递过来的Id  关联使用
	private String cpSeriesId; //原始cp的专辑Id
	private String seriesId; //专辑Id
	private String programName; 
	private String movieCode;
	private Integer version;
	private Integer terminalType;
	private String sourceUrl;  //原始地址
	private String movieUrl;   //注入后获取地址
	private Integer isInsert;
	private Integer injectTimes; //重试次数  null，1,2   失败才更新值
	private String bunchcode;
	private String programId;
	private String xmlLocation;
	private Date createTime;  //数据插入数据库时间
	private Date receiveTime; 
	private Date sendTime;  //数据发送到C2时间
	private Date callBackTime;  //回调时间
	private String injectTime;  //时间字符串
	private Integer priority;
	
	
	/*
	 private Integer id;
	 private String id_jmmd;
	 private String programCode;
	 private String programName;
	 private String programSequence;
	 private String action;
	 private String isPlay;
	 private String movieCode;
	 private String moviesSreenFormat;
	 private int movieDefinition;
	 private int movieSourceType;
	 private String movieType;
	 private String movieUrl;
	 private String movieSequence;
	 private int bj;
	 private int zt;
	 private String injection;
	 private String bz;
	 private int isinsert;
	 private String bunchcode;
	 private String injecttime;
	 private int priority;
	 */
	
}
