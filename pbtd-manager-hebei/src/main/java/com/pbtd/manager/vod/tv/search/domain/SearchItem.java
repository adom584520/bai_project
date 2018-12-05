package com.pbtd.manager.vod.tv.search.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 添加到solr的实体
 * @author 程先生
 *
 */
@SuppressWarnings("all")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class SearchItem implements Serializable{
			private static final long serialVersionUID = 1L;
	private Long epis;    //是否多专辑
	private String series_id;    //专辑Id
	private String series_name;    //专辑标题
	private String pinName;    //专辑标题拼音
	private String actor;    //专辑导演
	private String writer;    //演员    //导演和演员名字取错了
	private String year;    //专辑年份
	private String sup_id;    //分类ID
	private String categoryName;    //分类名称
	private String image_v;    //T专辑图片竖
	private String image_h;    //T专辑图片橫
	private String image_vs;    //S专辑图片竖
	private String image_hs;    //S专辑图片橫
	private Long update_number;    //更新集数
	private String country;    //国家
	private String reco;    //简介
	private String sup_url;     //角标
	private Long zt;    //专辑状态
	private String vip;  //是否付费

	
}
