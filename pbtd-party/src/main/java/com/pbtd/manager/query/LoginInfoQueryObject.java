package com.pbtd.manager.query;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginInfoQueryObject extends QueryObject {
	private String username;
	private Integer status;
	private Integer level;

	public String getUsername() {
		return username != null && username.length() > 0 ? username : null;
	}
}
