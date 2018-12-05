package com.pbtd.manager.query;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MacQueryObject extends QueryObject {
	private String macName;
	private Long groupId;

	public String getMacName() {
		return macName == null || "".equals(macName) ? null : macName;
	}
}
