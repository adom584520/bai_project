package com.pbtd.manager.user.page;

import com.pbtd.manager.user.util.UpgradeConstant;
import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpgradeQueryObject extends QueryObject {
	private Integer type;
	private Integer status = UpgradeConstant.UPGRADE_STATUS_NEW;
	private Long groupId;
}
