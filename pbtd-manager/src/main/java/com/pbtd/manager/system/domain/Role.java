package com.pbtd.manager.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色实体类
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
public class Role  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;// 角色名称
	private String descriptor;// 角色描述信息
	private Date createDate;// 创建时间
	private List<Menu> menus = new ArrayList<>();// 角色拥有的集合
}
