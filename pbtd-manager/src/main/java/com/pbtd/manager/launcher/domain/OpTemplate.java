package com.pbtd.manager.launcher.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 运营位模板实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class OpTemplate  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String tempName;// 运营位模板的名称
	private Long navId;// 导航ID
	private Long groupId;// 分组ID
	private Integer status;// 是否上下线，0下线，1上线
	private String loginInfoName;// 创建账号名称
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
}
