package com.pbtd.launcher.domain;

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
public class Mac {
	private Long id;
	private String macName; // mac名称
	private Long groupId; // 分组id
	private String loginInfoName; // 创建的账号
	private Date createTime; // 创建时间 
}
