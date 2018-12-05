package com.pbtd.manager.query;

import java.util.List;

import com.pbtd.manager.domain.Menu;
import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;
@Setter@Getter
public class MenuQueryObject extends QueryObject {
	private List<Menu> ids;
}
