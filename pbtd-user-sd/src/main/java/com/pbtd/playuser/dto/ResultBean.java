package com.pbtd.playuser.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台接口统一返回类型
 * 
 * @author JOJO
 *
 * @param <T>
 */
@Setter
@Getter
public class ResultBean {
	private Integer code;
	private String message;
	private SeriesDTO data;
}
