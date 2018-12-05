package com.yh.search.phone.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.yh.search.phone.bean.PhoneSubSearchItem;
import com.yh.search.phone.service.PhoneUrlJsonService;

import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 手机端根据id获取搜索列表
 * 
 * @author 程先生
 *
 */
@SuppressWarnings("all")
@ParentPackage("struts-default")
@Namespace("")
@Controller("urlJsonAction")
@Scope("prototype")
@Action("urlJsonAction")
public class UrlJsonAction extends ActionSupport {

	@Autowired
	private PhoneUrlJsonService phoneUrlJsonService;
	@Setter
	private String seriesId;

	/**
	 * 影片相关推荐 获取某个影片的相关推荐影片 接口名：getRelativeSeries
	 */
	public String getRelativeSeries() throws IOException {
		JSONObject json = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		String cht_id = request.getParameter("cht_id") == null ? "" : request.getParameter("cht_id").toString();// 鉴权成功返回值（base64加密）
		String proj_id = request.getParameter("proj_id") == null ? "" : request.getParameter("proj_id").toString();// 运营平台生成的项目ID
		String group_id = request.getParameter("group_id") == null ? "" : request.getParameter("group_id").toString();// mac
		try {
			if (seriesId != null && !"".equals(seriesId)) {
				List<PhoneSubSearchItem> result = phoneUrlJsonService.getSubSearch(seriesId);
				if (result.size() != 0) {
					json.accumulate("code", 1);
					json.accumulate("message", "成功");
					json.accumulate("data", JSONArray.fromObject(result));
				} else {
					json.accumulate("code", 0);
					json.accumulate("message", "无数据");
				}
			} else {
				json.accumulate("code", 0);
				json.accumulate("message", "无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("code", 0);
			json.accumulate("message", "访问超时");
		}
		response.getWriter().write(json.toString());
		return null;
	}

}
