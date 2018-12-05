package com.pbtd.manager.system.page;

import java.util.List;

import com.pbtd.manager.system.domain.Menu;
import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;
@Setter@Getter
public class MenuQueryObject extends QueryObject {
	private List<Menu> ids;
}
