package com.pbtd.manager.util;

import java.util.Iterator;
import java.util.List;

import com.pbtd.manager.domain.Menu;

public class SystemUtil {
	private SystemUtil() {
	}

	/**
	 * 将集合中的菜单对象按照父子对象映射的关系进行绑定
	 * 
	 * @param menus
	 */
	public static void findMenuPrent(List<Menu> menus) {
		// 将子节点放入父节点
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			Long prentId = menu.getParentId();
			if (prentId != null) {
				for (int j = 0; j < menus.size(); j++) {
					if (prentId == menus.get(j).getId()) {
						menus.get(j).getChildren().add(menu);
					}
				}
			}
		}
		// 将集合中不为根节点的节点删除
		Iterator<Menu> iterator = menus.iterator();
		while (iterator.hasNext()) {
			Menu menu = iterator.next();
			if (menu.getParentId() != null) {
				iterator.remove();
			}
		}
	}

	/**
	 * 将父子节点的菜单结构拆分为平级的菜单结构
	 * 
	 * @param menus
	 */
	public static void goodOrdinary(List<Menu> menus, List<Menu> newMenus) {
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			goodOrdinary(menu.getChildren(), newMenus);
			newMenus.add(menu);
		}
	}
}
