package com.pbtd.manager.util;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.playclick.integrate.service.face.IRealmNameService;

/**
 * ssion的存储工具类
 * 
 * @author JOJO
 *
 */
@Controller
public class LoginInfoContext {
	/**
	 * 表示当前用户
	 */
	public static final String LOGININFO_IN_SESSION = "loginInfo";
	
	@Autowired
	public   IRealmNameService realmnameService;

	public static HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	/**
	 * 将当前用户放入ssion
	 * 
	 * @param current
	 */
	public static void setCurrent(LoginInfo current) {
		getSession().setAttribute(LOGININFO_IN_SESSION, current);
	}
	/**
	 * 将数据放进session中
	 * @param current
	 */
	public   void setsession() {
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
}
