package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesSignIn {
	private Integer id;
	private String userId;//用户ID
	private Date signInTime;//签到时间
	private Date createTime;//记录创建时间
	private Integer fluxNumber;//当天签到流量
}
