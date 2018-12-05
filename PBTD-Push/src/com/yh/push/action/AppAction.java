package com.yh.push.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;
import com.yh.push.bean.PushUser;
import com.yh.push.service.AppService;
import com.yh.push.utils.pushUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
@ParentPackage("struts-default")
@Namespace("")
@Controller("appAction")
@Scope("prototype")
@Action("appAction")
public class AppAction extends ActionSupport {
	public static Logger log = Logger.getLogger(AppAction.class);
	@Autowired
	private AppService appService;
	private pushUtils push = new pushUtils();

	public String receivePost() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		String type = "2";
		boolean bl = false;
		String status = "0";
		try {
			reqBody = push.getrequest(request);
			log.info("推屏参数: " + reqBody);
			Obj = JSONObject.fromObject(reqBody);// 将json字符串转换为json对象
			PushUser pushUser = new PushUser();
			if ("1".equals(Obj.get("des_type")) || "2".equals(Obj.get("des_type"))) {
				pushUser = appService.getselect(Obj.get("des_token").toString(), Obj.get("src_token").toString());
			} else {
				pushUser = appService.getselect(Obj.get("src_token").toString(), Obj.get("des_token").toString());
			}
			if (pushUser != null) {
				status = pushUser.getStatus();
			}
			if ("1".equals(status)) {
				if ("1".equals(Obj.get("des_type"))) {
					bl = push.sendAndroidUnicast(Obj.get("des_token").toString(), "播放推屏影片: " + Obj.get("title").toString(),
							reqBody, type);
					log.info("安卓单播");
				} else if ("2".equals(Obj.get("des_type"))) {
					bl = push.sendIOSUnicast(Obj.get("des_token").toString(), "播放推屏影片: " + Obj.get("title").toString(),
							reqBody, type);
					log.info("IOS单播");
				} else if ("3".equals(Obj.get("des_type"))) {
					bl = push.sendTvUnicast(Obj.get("des_token").toString(), "播放推屏影片: " + Obj.get("title").toString(),
							reqBody, type);
					log.info("TV单播");
				}
			} else {
				json.accumulate("code", 0);
				json.accumulate("message", "未绑定");
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			}
		} catch (Exception e) {
			log.error("推屏参数有误" + e);
			json.accumulate("code", 0);
			json.accumulate("message", "推屏参数有误");
			try {
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			} catch (IOException e1) {
				log.error("推屏参数有误" + e);
			}
		}
		try {
			if (bl) {
				json.accumulate("code", 1);
				json.accumulate("message", "成功");
				log.info("=====" + reqBody + "...");
			} else {
				json.accumulate("code", 0);
				json.accumulate("message", "访问超时");
			}
			response.getWriter().write(json.toString());// 传递给页面
		} catch (IOException e) {
			log.error("返回参数有误" + e);
		}
		return null;
	}

	// 手机绑定TV
	public String getTv() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		boolean bl = false;
		String type = "1";
		try {
			reqBody = push.getrequest(request);
			log.info("绑定TV参数: " + reqBody);
			Obj = JSONObject.fromObject(reqBody);// 将json字符串转换为json对象
			PushUser pushUserDel = appService.getselectDel(Obj.get("des_token").toString());
			if (pushUserDel != null) {
				if ("1".equals(pushUserDel.getSrc_type())) {
					bl = push.sendAndroidUnicast(pushUserDel.getSrc_token(), "TV端与你解绑", "TV端与你解绑", "3");
					log.info("安卓单播TV端与你解绑");
				} else if ("2".equals(pushUserDel.getSrc_type())) {
					bl = push.sendIOSUnicast(pushUserDel.getSrc_token(), "TV端与你解绑", "TV端与你解绑", "3");
					log.info("IOS单播TV端与你解绑");
				}
			}
			bl = push.sendTvUnicast(Obj.get("des_token").toString(), "绑定", reqBody, type);
			log.info("TV单播");
		} catch (Exception e) {
			log.error("手机绑定TV参数有误" + e);
			json.accumulate("code", 0);
			json.accumulate("message", "手机绑定TV失败");
			try {
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			} catch (IOException e1) {
				log.error("手机绑定TV有误" + e);
			}
		}
		try {
			if (bl) {
				PushUser pushUser = appService.getselect(Obj.get("src_token").toString(),
						Obj.get("des_token").toString());
				if (pushUser == null) {
					// 绑定参数
					PushUser jb = (PushUser) JSONObject.toBean(Obj, PushUser.class);// 将建json对象转换为PushUser对象
					appService.getAddTv(jb);
				} else {
					log.info(pushUser.getPhoneName() + "111111111");
				}
				log.info("发送到TV端的推送成功");
				json.accumulate("code", 1);
				json.accumulate("message", "正在等待电视端确认");
			} else {
				json.accumulate("code", 0);
				json.accumulate("message", "绑定失败");
			}
			response.getWriter().write(json.toString());// 传递给页面
		} catch (IOException e) {
			log.error("返回参数有误" + e);
		}
		return null;
	}

	// TV绑定手机
	public String getPhone() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		boolean bl = false;
		JSONObject Obj = null;
		String type = "1";// 状态1:绑定,2:推送,3:解绑
		try {
			reqBody = push.getrequest(request);
			log.info("绑定手机参数: " + reqBody);
			Obj = JSONObject.fromObject(reqBody);// 将json字符串转换为json对象
			if ("1".equals(Obj.get("src_type"))) {
				bl = push.sendAndroidUnicast(Obj.get("src_token").toString(), "绑定成功", "绑定成功", type);
				log.info("安卓单播绑定成功");
			} else if ("2".equals(Obj.get("src_type"))) {
				bl = push.sendIOSUnicast(Obj.get("src_token").toString(), "绑定成功", "绑定成功", type);
				log.info("IOS单播绑定成功");
			}
			if (bl) {
				// 绑定参数
				PushUser jb = (PushUser) JSONObject.toBean(Obj, PushUser.class);// 将建json对象转换为PushUser对象
				jb.setStatus("1");
				appService.getAddPhone(jb);
				log.info("发送到手机端的推送成功");
				json.accumulate("code", 1);
				json.accumulate("message", "成功");
			} else {
				json.accumulate("code", 0);
				json.accumulate("message", "绑定失败");
			}
			response.getWriter().write(json.toString());// 传递给页面
		} catch (Exception e) {
			log.error("TV绑定手机参数有误" + e);
			json.accumulate("code", 0);
			json.accumulate("message", "TV绑定手机失败");
			try {
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			} catch (IOException e1) {
				log.error("请求参数有误" + e);
			}
		}
		return null;
	}

	// 手机和TV解绑
	public String getDeleteToken() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		String type = "3";
		boolean bl = false;
		try {
			reqBody = push.getrequest(request);
			log.info("解绑参数: " + reqBody);
			Obj = JSONObject.fromObject(reqBody);// 将json字符串转换为json对象
			String des_type = Obj.get("des_type") == null ? "" : Obj.get("des_type").toString();
			String src_userID = Obj.get("src_userID") == null ? "" : Obj.get("src_userID").toString();
			String des_token = Obj.get("des_token") == null ? "" : Obj.get("des_token").toString();
			// 解绑
			if ("1".equals(des_type)) {
				int getdelete = appService.getDelTv(src_userID);
				if (getdelete != 0) {
					bl = push.sendAndroidUnicast(des_token, "推送解绑", "TV解绑手机", type);
					log.info("TV解绑手机A");
				}
			} else if ("2".equals(des_type)) {
				int getdelete = appService.getDelTv(src_userID);
				if (getdelete != 0) {
					bl = push.sendIOSUnicast(des_token, "推送解绑", "TV解绑手机", type);
					log.info("TV解绑手机I");
				}
			} else if ("3".equals(des_type)) {
				int getdelete = appService.getDelPhone(src_userID);
				if (getdelete != 0) {
					bl = push.sendTvUnicast(des_token, "推送解绑", "手机解绑TV", type);
					log.info("手机解绑TV");
				}
			}
		} catch (Exception e) {
			log.error("解绑参数有误" + e);
			json.accumulate("code", 0);
			json.accumulate("message", "解绑参数有误");
			try {
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			} catch (IOException e1) {
				log.error("解绑参数有误" + e);
			}
		}
		try {
			if (bl) {
				json.accumulate("code", 1);
				json.accumulate("message", "解绑成功");
				log.info("===== 推送解绑 ...");
			} else {
				json.accumulate("code", 1);
				json.accumulate("message", "解绑成功");
				log.info("===== 解绑成功 ...");
			}
			response.getWriter().write(json.toString());// 传递给页面
		} catch (IOException e) {
			log.error("返回参数有误" + e);
		}
		return null;
	}

	// 更新token
	public String updateDevieToken() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		try {
			reqBody = push.getrequest(request);
			log.info("更新Token参数: " + reqBody);
			Obj = JSONObject.fromObject(reqBody);// 将json字符串转换为json对象
		} catch (Exception e) {
			log.error("更新token参数有误" + e);
			json.accumulate("code", 0);
			json.accumulate("message", "更新token参数有误");
			try {
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			} catch (IOException e1) {
				log.error("更新token参数有误" + e);
			}
		}
		String src_type = Obj.get("src_type") == null ? "" : Obj.get("src_type").toString();
		String src_userID = Obj.get("src_userID") == null ? "" : Obj.get("src_userID").toString();
		String src_token = Obj.get("src_token") == null ? "" : Obj.get("src_token").toString();
		int getdelete = 0;
		if ("1".equals(src_type)) {
			getdelete = appService.getTokenPhone(src_userID, src_token, src_type);
			log.info(getdelete + "更新");
		} else if ("2".equals(src_type)) {
			getdelete = appService.getTokenPhone(src_userID, src_token, src_type);
			log.info(getdelete + "更新");
		} else if ("3".equals(src_type)) {
			getdelete = appService.getTokenTv(src_userID, src_token);
			log.info(getdelete + "更新");
		}
		try {
			if (getdelete != 0) {
				json.accumulate("code", 1);
				json.accumulate("message", "更新token成功");
				log.info("===== 更新token ...");
			} else {
				json.accumulate("code", 0);
				json.accumulate("message", "无token更新");
			}
			response.getWriter().write(json.toString());// 传递给页面
		} catch (IOException e) {
			log.error("返回参数有误" + e);
		}
		return null;
	}

	// 查看绑定关系
	public String getSelectToken() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		try {
			reqBody = push.getrequest(request);
			log.info("查看绑定参数: " + reqBody);
			Obj = JSONObject.fromObject(reqBody);// 将json字符串转换为json对象
		} catch (Exception e) {
			log.error("查看绑定参数有误" + e);
			json.accumulate("code", 0);
			json.accumulate("message", "查看绑定参数有误");
			try {
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			} catch (IOException e1) {
				log.error("请求参数有误" + e);
			}
		}
		String src_type = Obj.get("src_type") == null ? "" : Obj.get("src_type").toString();
		String src_userID = Obj.get("src_userID") == null ? "" : Obj.get("src_userID").toString();
		List<PushUser> list = new ArrayList<PushUser>();
		if ("1".equals(Obj.get("src_type")) || "2".equals(Obj.get("src_type"))) {
			list = appService.getselectPhone(src_userID);
		} else {
			list = appService.getselectTv(src_userID);
		}
		try {
			if (list.size() > 0) {
				json.accumulate("code", 1);
				json.accumulate("message", "成功");
				json.accumulate("body", JSONArray.fromObject(list));
			} else {
				json.accumulate("code", 1);
				json.accumulate("message", "无数据");
				json.accumulate("body", JSONArray.fromObject(list));
			}
			response.getWriter().write(json.toString());// 传递给页面
		} catch (Exception e) {
			log.error("查看绑定参数有误" + e);
		}
		return null;
	}
}