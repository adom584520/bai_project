package com.pbtd.manager.system.domain;

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
public class Menu {
	private Long id;
	private String text;// 节点名称
	private String url;// 页面路径
	private Long parentId;// 父菜单Id
	private String state;// 'open' 或 'closed'，选择open则节点自动展开
	private boolean checked = false;// 字段用来在树菜单中节点复选框为默认不选中状态，没有其他用处，选中状态由前端负责，数据库不需要存储
	private String iconCls;// 节点的小图片
	private Integer sort;// 节点的顺序
	private String permission;// 菜单的权限表达式
	private List<Menu> children = new ArrayList<>();// 该节点的子节点集合
}
