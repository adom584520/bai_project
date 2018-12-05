package com.pbtd.manager.system.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.domain.Menu;
import com.pbtd.manager.system.domain.Role;
import com.pbtd.manager.system.service.IRealmNameService;

/**
 * 作用域对象的存储工具类
 * 
 * @author JOJO
 *
 */
@Controller
public class LoginInfoContext {
	private LoginInfoContext() {
	}
	@Autowired
	public   IRealmNameService realmnameService;
	/**
	 * 表示当前用户
	 */
	public static final String LOGININFO_IN_SESSION = "loginInfo";
	/**
	 * 账号的所有角色
	 */
	public static final String LOGININFO_ROLE_SESSION = "loginInfoAllRole";

	/**
	 * 当前账号的所有菜单
	 */
	public static final String LOGININFO_MENU_SESSION = "loginInfoAllMenu";
	/**
	 * 菜单中的权限表达式，只在验证请求访问是否有权限
	 */
	public static final String LOGININFO_PERMISSION_SESSION = "loginInfoAllPermission";

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
	 * 将数据放进session中
	 * @param current
	 */
	public     void setsession() {
		 Map<String,Object> maprealm=realmnameService.findtitle(null);
		getSession().setAttribute("maprealm",maprealm);
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
	 * 将账号的角色放入sseion
	 * @param Roles
	 */
	public static void setSelfRole(List<Role> Roles) {
		getSession().setAttribute(LOGININFO_ROLE_SESSION, Roles);
	}
	
	/**
	 * 获取账号的角色
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Role> getSelfRole() {
		return (List<Role>) getSession().getAttribute(LOGININFO_ROLE_SESSION);
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

	/**
	 * 将账号权限放入ssion中
	 * 
	 * @param menus
	 */
	public static void setSelfPermission(Set<String> menus) {
		getSession().setAttribute(LOGININFO_PERMISSION_SESSION, menus);
	}

	/**
	 * 获取权限
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getSelfPermission() {
		return (Set<String>) getSession().getAttribute(LOGININFO_PERMISSION_SESSION);
	}

	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) requestLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}
}
