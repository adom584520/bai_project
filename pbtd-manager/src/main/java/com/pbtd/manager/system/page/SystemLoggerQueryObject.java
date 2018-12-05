package com.pbtd.manager.system.page;

import com.pbtd.manager.system.util.MyDateUtil;
import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemLoggerQueryObject extends QueryObject {
	private String loginInfoName;
	private String operationInfo;
	private String startTime;
	private String endTime;

	public String getLoginInfoName() {
		return loginInfoName != null && loginInfoName.length() > 0 ? loginInfoName : null;
	}

	public String getStartTime() {
		if (this.startTime != null && this.startTime.length() > 0) {
			return MyDateUtil.stringDateFormater(this.startTime, "yyyy-MM-dd", "yyyy-MM-dd KK:mm:ss");
		}
		return null;
	}

	public String getEndTime() {
		if (this.endTime != null && this.endTime.length() > 0) {
			return MyDateUtil.endOfDayStringDateFormater(this.endTime, "yyyy-MM-dd", "yyyy-MM-dd hh:mm:ss");
		}
		return null;
	}
}
