package com.pbtd.manager.vod.phone.slideshow.query;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SlideshowQueryObject extends QueryObject {
	private Integer status;
	private String targetCode;
	private Integer id;

	public String getTargetCode() {
		return targetCode != null && targetCode.length() > 0 ? targetCode : null;
	}
}
