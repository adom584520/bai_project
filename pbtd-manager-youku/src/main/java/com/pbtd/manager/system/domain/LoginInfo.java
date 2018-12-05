package com.pbtd.manager.system.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 账号（登录信息）实体类
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
public class LoginInfo {
	private Long id;
	private String username;//账号名
	private String password;//账号密码
	private Integer status;//账号处于可以使用状态
	private String realName;//使用账号的员工名字
	private String contactInformation;//联系方式
	private Integer level;//账号的等级，超级管理员或者普通管理员
	private String createLoginInfo;//创建人
	private Date createDate;//账号创建时间
}
