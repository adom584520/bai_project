package com.pbtd.manager.launcher.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * launcher分组实体类
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class LauncherGroup  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String groupName;// 分组名称
	private String contacts;// 联系人
	private String contactsNumber;// 联系号码
	private String address;// 联系地址
	private String loginInfoName;// 创建账号名称
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
}
