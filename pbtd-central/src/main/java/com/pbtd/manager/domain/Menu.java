package com.pbtd.manager.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 节点(菜单)实体类，
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
public class Menu implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String text;// 节点名称
	private String url;// 页面路径
	private Long prentId;//父菜单Id
	private String state;// 'open' 或 'closed'，选择open则节点自动展开
	private String iconCls;// 节点的小图片
	private Integer sort;// 节点的顺序
	private List<Menu> children = new ArrayList<>();// 该节点的子节点集合
}
