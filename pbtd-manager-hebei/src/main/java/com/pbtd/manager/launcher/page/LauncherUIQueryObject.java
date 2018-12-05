package com.pbtd.manager.launcher.page;

import com.pbtd.manager.launcher.util.LauncherConstant;
import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LauncherUIQueryObject extends QueryObject {
	private Long groupId;
	private Integer dataStatus;

	public Integer getDataStatus() {
		return dataStatus == null ? LauncherConstant.LAUNCHER_DATA_STATUS_VALID : dataStatus;
	}
}
