package com.pbtd.manager.query;

import com.pbtd.manager.util.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleQueryObject extends QueryObject {
	private Long groupId;
	private Integer type;
}
