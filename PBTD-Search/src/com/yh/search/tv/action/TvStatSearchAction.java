package com.yh.search.tv.action;

import java.util.HashMap;
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
import com.yh.search.tv.bean.TvStatSearchItem;
import com.yh.search.tv.service.TvStatSearchService;

import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TV端根据关键词获取搜索分类列表
 * 
 * @author 程先生
 *
 */
@SuppressWarnings("all")
@ParentPackage("struts-default")
@Namespace("")
@Controller("tvStatSearchAction")
@Scope("prototype")
@Action("tvStatSearchAction")
public class TvStatSearchAction extends ActionSupport {

	@Resource
	private TvStatSearchService tvstatSearchService;
	@Setter
	private String queryString;

	// 分类列表
	public String getSearchParam() throws Exception {
		JSONObject json = new JSONObject();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/html;charset=utf-8");
		String cht_id = request.getParameter("cht_id") == null ? "" : request.getParameter("cht_id").toString();// 鉴权成功返回值（base64加密）
		String pro_id = request.getParameter("pro_id") == null ? "" : request.getParameter("pro_id").toString();// 运营平台生成的项目ID
		String mac = request.getParameter("mac") == null ? "" : request.getParameter("mac").toString();// mac
		String use_id = request.getParameter("user_id") == null ? "" : request.getParameter("user_id").toString();// 用户id
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(queryString);
		queryString = m.replaceAll("").trim().toLowerCase();
		// 输入参数
		if (queryString.equals(null) || queryString.equals("") || queryString.equals("*")
				|| queryString.equals("*:*")) {
			json.accumulate("code", 0);
			json.accumulate("message", "访问参数有误");
			response.getWriter().write(json.toString());
			return null;
		}
		try {
			HashMap map = new HashMap();
			map.put("cht_id", cht_id);
			map.put("pro_id", pro_id);
			map.put("mac", mac);
			map.put("userid", use_id);
			List<TvStatSearchItem> statresult = tvstatSearchService.GroupFieldQuery(queryString);
			if (statresult.size() <= 0) {
				json.accumulate("code", 0);
				json.accumulate("message", "无数据");
			} else {
				json.accumulate("code", 1);
				json.accumulate("message", "成功");
				json.accumulate("data", JSONArray.fromObject(statresult));
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
			json.accumulate("message", "访问超时");
		}
		// 传递给页面
		response.getWriter().write(json.toString());

		return null;

	}

}
