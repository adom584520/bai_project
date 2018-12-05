package com.pbtd.manager.util;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.domain.Menu;

/**
 * 作用域对象的存储工具类
 * 
 * @author JOJO
 *
 */
public class LoginInfoContext {
	private LoginInfoContext() {
	}

	/**
	 * 表示当前用户
	 */
	public static final String LOGININFO_IN_SESSION = "loginInfo";

	/**
	 * 当前账号的所有菜单
	 */
	public static final String LOGININFO_MENU_SESSION = "loginInfoAllMenu";

	public static HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	/**
	 * 将当前用户放入ssion
	 * 
	 * @param current
	 */
	public static void setCurrent(LoginInfo current) {
		getSession().setAttribute("username", current.getUsername());
		getSession().setAttribute("userid", current.getId());
		getSession().setAttribute(LOGININFO_IN_SESSION, current);
	}

	/**
	 * 获取ssion中的当前用户
	 * 
	 * @return
	 */
	public static LoginInfo getCurrent() {
		if (getSession().getAttribute(LOGININFO_IN_SESSION) != null) {
			return (LoginInfo) getSession().getAttribute(LOGININFO_IN_SESSION);
		}
		return null;
	}
	
	/**
	 * 将账号菜单放入ssion中
	 * 
	 * @param menus
	 */
	public static void setSelfMenu(List<Menu> menus) {
		getSession().setAttribute(LOGININFO_MENU_SESSION, menus);
	}


	/**
	 * 获取菜单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Menu> getSelfMenu() {
		return (List<Menu>) getSession().getAttribute(LOGININFO_MENU_SESSION);
	}
}
