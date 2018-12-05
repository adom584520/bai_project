package com.pbtd.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class VodTvAlbuminfoReturn {
	private String seriesCode;// 自己的专辑code
	private String cpCode;// cp的类型
	private String cpSeriesCode;// cp的专辑code
	private String seriesName;//自己的专辑名称
}
