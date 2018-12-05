package com.pbtd.manager.vod.tv.album.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 移固融合响应实体类
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VodTvAlbuminfoReturn {
	private String seriesCode;// 自己的媒资code
	private String cpCode;// cp的类型
	private String cpSeriesCode;// cp的媒资code
	private String seriesName;//自己的专辑名称
}
