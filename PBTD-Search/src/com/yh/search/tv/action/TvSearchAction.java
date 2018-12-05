package com.yh.search.tv.action;

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
import com.yh.search.tv.bean.TvSearchResult;
import com.yh.search.tv.service.TvSearchService;

import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TV端根据关键词获取搜索列表
 * 
 * @author 程先生
 *
 */
@SuppressWarnings("all")
@ParentPackage("struts-default")
@Namespace("")
@Controller("tvSearchAction")
@Scope("prototype")
@Action("tvSearchAction")
public class TvSearchAction extends ActionSupport {

	@Resource
	private TvSearchService searchService;
	@Setter
	private String queryString;
	@Setter
	private Integer page = 1;
	@Setter
	private String categoryName;

	// TV搜索列表
	public String getSearchListByKD() throws Exception {
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
		final int ITEM_ROWS = 20;
		// 判断查询全部还是分类部分
		TvSearchResult result = null;
		try {
			if (categoryName != null && !"".equals(categoryName)) {
				result = searchService.supSearch(queryString, page, ITEM_ROWS, categoryName);
				if (result.getItemList().size() != 0) {
					json.accumulate("code", 1);
					json.accumulate("message", "成功");
					json.accumulate("alb_num", result.getRecordCount());
					json.accumulate("alb_page", result.getPageCount());
					json.accumulate("queryString", queryString);
					json.accumulate("page", result.getPage());
					json.accumulate("data", JSONArray.fromObject(result.getItemList()));
				} else {
					json.accumulate("code", 0);
					json.accumulate("message", "无数据");
				}
			} else {
				// 不带过滤条件查全部
				result = searchService.search(queryString, page, ITEM_ROWS);
				if (result.getItemList().size() != 0) {
					json.accumulate("code", 1);
					json.accumulate("message", "成功");
					json.accumulate("alb_num", result.getRecordCount());
					json.accumulate("alb_page", result.getPageCount());
					json.accumulate("queryString", queryString);
					json.accumulate("page", result.getPage());
					json.accumulate("data", JSONArray.fromObject(result.getItemList()));
				} else {
					json.accumulate("code", 0);
					json.accumulate("message", "无数据");
				}
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
