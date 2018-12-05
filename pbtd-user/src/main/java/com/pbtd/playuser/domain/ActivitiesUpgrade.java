package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivitiesUpgrade {
	private Integer id;
	private String userId;
	private String activeCode;
	private Integer flux;
	private String version;
	private Date createTime;
}
