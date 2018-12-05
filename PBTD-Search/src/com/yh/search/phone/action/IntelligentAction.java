package com.yh.search.phone.action;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.yh.search.phone.service.PhoneIntelligentService;

import lombok.Setter;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
@ParentPackage("struts-default")
@Namespace("")
@Controller("intelligentAction")
@Scope("prototype")
@Action("intelligentAction")
public class IntelligentAction extends ActionSupport{

	@Resource
	private PhoneIntelligentService phoneIntelligentService;
	@Setter
	private String queryString;

	// 智能提示
	public String getIntelligen() throws Exception {
		JSONObject json = new JSONObject();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/html;charset=utf-8");
		String cht_id = request.getParameter("cht_id") == null ? "" : request.getParameter("cht_id").toString();// 鉴权成功返回值（base64加密）
		String pro_id = request.getParameter("pro_id") == null ? "" : request.getParameter("pro_id").toString();// 运营平台生成的项目ID
		String mac = request.getParameter("mac") == null ? "" : request.getParameter("mac").toString();// mac
		String use_id = request.getParameter("user_id") == null ? "" : request.getParameter("user_id").toString();// 用户id
		// 输入参数
		if (queryString == null || "".equals(queryString)) {
			json.accumulate("code", 0);
			json.accumulate("message", "输入参数有误");
			response.getWriter().write(json.toString());
			return null;
		}
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(queryString);
		queryString = m.replaceAll("").trim().toLowerCase();
		//查全部
		List<String> body = phoneIntelligentService.search(queryString);
		if (body.size() != 0) {
			json.accumulate("code", 1);
			json.accumulate("message", "成功");
			json.accumulate("queryString", queryString);
			json.accumulate("data", body);
		} else {
			json.accumulate("code", 0);
			json.accumulate("message", "无数据");
		}
		// 传递给页面
		response.getWriter().write(json.toString());

		return null;

	}
}
