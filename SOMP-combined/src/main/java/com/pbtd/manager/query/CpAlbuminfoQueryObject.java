package com.pbtd.manager.query;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CpAlbuminfoQueryObject extends QueryObject {
	private String cpCode;
	private Integer status;
	private String seriesName;
	private String seriesCode;
	private Integer joinStatus;
}
