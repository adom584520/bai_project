package com.pbtd.manager.launcher.page;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LauncherGroupDetailQueryObject extends QueryObject {
	private String groupName;

	public String getGroupName() {
		return groupName == null || "".equals(groupName) ? null : groupName;
	}
}
