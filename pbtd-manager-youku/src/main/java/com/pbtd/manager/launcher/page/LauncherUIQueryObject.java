package com.pbtd.manager.launcher.page;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LauncherUIQueryObject extends QueryObject {
	private Long groupId;
	private Boolean dataStatus;

	public Boolean getDataStatus() {
		return dataStatus == null ? true : dataStatus;
	}
}
