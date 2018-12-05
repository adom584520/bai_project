package com.pbtd.manager.launcher.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * mac实体类
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mac implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String macName; // mac名称
	private String loginInfoName; // 创建的账号
	private Long groupId; // 分组id
	private String groupName;//分组名称
	private Date createTime; // 创建时间 
}
