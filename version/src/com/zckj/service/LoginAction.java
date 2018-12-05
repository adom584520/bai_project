/**
 * @author liuxiaomeng
 * @datetime 2015-8-1_下午6:05:48
 */
package com.zckj.service;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.zckj.tool.utils.DataBasUtils;
import com.zckj.tool.utils.EncrypAES;

/**
 * 角色数据，登陆验证等
 * 
 * @author liuxiaomeng
 * @datetime 2015-8-1_下午6:05:48
 */
public class LoginAction {

	/** 操作员ID */
	private String operatorId;

	/** 登录帐号 */
	private String username;

	/** 登录密码 */
	private String password;

	private String jsondate = "";

	public String getUsername() {
		return this.username;
	}

	public String getJsondate() {
		return this.jsondate;
	}

	public void setJsondate(final String jsondate) {
		this.jsondate = jsondate;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-7_下午2:45:58
	 * @return the operatorId
	 */
	public String getOperatorId() {
		return this.operatorId;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-7_下午2:45:58
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(final String operatorId) {
		this.operatorId = operatorId;
	}

	public String execute() throws Exception {
		final JSONObject jobj = new JSONObject();
		if (null == this.username || this.username.trim().length() == 0) {
			jobj.put("success", false);
			jobj.put("info", "用户名不能为空！");
		} else if (null == this.password || this.password.trim().length() == 0) {
			jobj.put("success", false);
			jobj.put("info", "密码不能为空！");
		} else {
			final String sqlquery = "select * from operator where operatorName='" + this.username + "'";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
			if (ret.next()) {
				this.operatorId = ret.getString(1);
				final String pwd = ret.getString(3);
				String pwdEn =  this.password.trim();
				if ((null != pwd) && pwdEn.equals(pwd)) {
					jobj.put("success", true);
					jobj.put("info", "登录成功！");
				} else {
					jobj.put("success", false);
					jobj.put("info", "密码有误，请重新输入！");
				}
			} else {
				jobj.put("success", false);
				jobj.put("info", "用户名不存在！");
			}
		}
		final HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jobj.toString());
		response.getWriter().flush();
		return Action.SUCCESS;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-1_下午6:05:48
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO 需要完善相关逻辑
	}
}
