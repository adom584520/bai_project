package com.pbtd.manager.query;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CpPlayHistoryQueryObject extends QueryObject {
	private String deviceId;
}
