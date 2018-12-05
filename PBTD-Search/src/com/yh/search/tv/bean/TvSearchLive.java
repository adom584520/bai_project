package com.yh.search.tv.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("all")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class TvSearchLive {
 
	private String packageid;    //专辑Id
	private String packagename;    //专辑标题
	private String chncode;    //频道名称
	private String pinyinsuoxie;    //专辑标题简拼
	private String packageposter;    //T专辑图片竖
	private String pictureurl2;    //T专辑图片橫
	private String ChannelName;    //分类名称
	private Long status;    //专辑状态
	
}
