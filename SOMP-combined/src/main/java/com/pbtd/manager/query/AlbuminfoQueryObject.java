package com.pbtd.manager.query;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlbuminfoQueryObject extends QueryObject {
	private String cpCode;
	private String seriesName;
	private String seriesCode;
	private Integer status;//判断已方的专辑是否已经关联过

	public String getCpCode() {
		return cpCode == null || "".equals(cpCode) ? "1" : cpCode;
	}
}
