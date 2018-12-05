package com.pbtd.manager.query;

import java.util.List;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupQueryObject extends QueryObject {
	private String groupName;
	private String macName;
	private List<Long> groupIds;

	public String getGroupName() {
		return groupName == null || "".equals(groupName) ? null : groupName;
	}

	public String getMacName() {
		return macName == null || "".equals(macName) ? null : macName;
	}
}
