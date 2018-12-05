package com.pbtd.manager.system.page;

import java.util.List;

import com.pbtd.manager.system.domain.Role;
import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;
@Setter@Getter
public class RoleQueryObject extends QueryObject {
	private List<Role> ids;
	
}
