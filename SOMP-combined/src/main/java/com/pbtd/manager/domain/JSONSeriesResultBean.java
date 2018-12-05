package com.pbtd.manager.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JSONSeriesResultBean {
	private Integer code;
	private String message;
	private VodTvAlbuminfoReturn data;
}
