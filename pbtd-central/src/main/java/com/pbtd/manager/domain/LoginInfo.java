package com.pbtd.manager.domain;

import java.io.Serializable;
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
public class LoginInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;//账号名
	private String password;//账号密码
	private Integer status;//账号处于可以使用状态
	private String realName;//使用账号的员工名字
	private Date createDate;//账号创建时间
}
