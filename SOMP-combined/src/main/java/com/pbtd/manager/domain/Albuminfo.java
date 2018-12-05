package com.pbtd.manager.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 己方媒资专辑信息实体
 * @author JOJO
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Albuminfo {
	private Long seriesCode;// 专辑ID
	private String seriesName;// 专辑名称
	private Integer volumnCount;// 总集数
	private Integer currentNum;// 更新集数
	private String originalName;//原来的名称
	private String actorName;//导演名称
	private String writerName;//主演名称
	private String orgairDate;//首映时间
	private String description;//描述信息
	private String tag;//关联标签
	private String viewPoint;//内容看点
	private String originalCountry;//国家地区
	private String releaseYear;//上映年份
	private String duration;//总时长
	private Integer deviceType;//手机还是TV，1.表示手机，2.表示TV，3.表示两者都有
	private Date updateTime;// 更新时间
	private Date createTime;//创建时间
}
