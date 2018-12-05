package com.pbtd.manager.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 点播收藏和播放记录获取专辑信息返回对象实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class SeriesResultBean {
	private Integer code;
	private String message;
	private SeriesDTO data;
}
