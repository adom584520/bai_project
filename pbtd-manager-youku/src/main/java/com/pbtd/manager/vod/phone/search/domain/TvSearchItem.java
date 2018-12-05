package com.pbtd.manager.vod.phone.search.domain;

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
public class TvSearchItem implements Serializable{

	private Long epis;    //是否多专辑
	private String series_id;    //专辑Id
	private String series_name;    //专辑标题
	private String actor;    //演员 
	private String sup_id;    //分类ID
	private String categoryName;    //分类名称
	private String image_v;    //T专辑图片竖
	private String image_h;    //T专辑图片橫
	private Long update_number;    //更新集数
	private String sup_url;    //角标
	private Long zt;    //专辑状态
	private String vip;  //是否付费
	
}
